package com.example.messenger.services.impls;

import com.example.messenger.domain.User;
import com.example.messenger.dto.user.AuthenticationRequestDto;
import com.example.messenger.dto.user.RegistrationRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import com.example.messenger.exceptions.*;
import com.example.messenger.repositories.UserRepository;
import com.example.messenger.security.JwtProvider;
import com.example.messenger.services.AuthorizationService;
import com.example.messenger.services.mappers.CommonMapper;
import com.example.messenger.services.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

import static com.example.messenger.utils.Constants.REFRESH_TOKEN_COOKIE_NAME;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Value("${jwt.access.duration}")
    private Duration accessDuration;
    @Value("${jwt.refresh.duration}")
    private Duration refreshDuration;

    @Override
    public ResponseEntity<String> login(AuthenticationRequestDto requestDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(userMapper.mapAuthenticationUserDtoToAuthentication(requestDto));
            return generateAccessAndRefreshTokenIntoResponseEntity(authentication);
        } catch (GenerateJwtTokenException exception) {
            throw new ApiRequestException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException exception) {
            throw new ApiRequestException(exception.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<UserResponseDto> registration(RegistrationRequestDto requestDto) throws CloneNotSupportedException {
        String email = requestDto.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new LoginAlreadyExistException(String.format("Login: %s already exist", email),email);
        }
        if (requestDto.getPassword() == null) {
            throw new PasswordNotMatchException("Passwords don't match");
        }
        User savedUser = commonMapper.convertToEntity(requestDto.clone(passwordEncoder.encode(requestDto.getPassword())), User.class);
        LocalDateTime now = LocalDateTime.now();
        savedUser.setActive(false);
        savedUser.setCreatedAt(now);
        savedUser.setLastTimeOnline(now);
        User newUser = userRepository.saveAndFlush(savedUser);
        return ResponseEntity.ok(commonMapper.convertToDto(newUser, UserResponseDto.class));
    }

    @Override
    public ResponseEntity<String> refresh(String refreshToken) throws ReadJwtTokenException, GenerateJwtTokenException {
        Authentication authentication = jwtProvider.getAuthenticationByToken(refreshToken);
        return generateAccessAndRefreshTokenIntoResponseEntity(authentication);
    }

    private ResponseEntity<String> generateAccessAndRefreshTokenIntoResponseEntity(Authentication authentication) throws GenerateJwtTokenException {
        String accessToken = jwtProvider.generateToken(authentication, accessDuration);
        String refreshToken = jwtProvider.generateToken(authentication, refreshDuration);

        HttpCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(accessToken);
    }
}
