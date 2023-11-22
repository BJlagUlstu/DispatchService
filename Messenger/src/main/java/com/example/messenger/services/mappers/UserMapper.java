package com.example.messenger.services.mappers;

import com.example.messenger.domain.User;
import com.example.messenger.dto.message.SenderResponseDto;
import com.example.messenger.dto.user.AuthenticationRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.example.messenger.utils.Constants.IMAGES_MAIN_PATH;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CommonMapper commonMapper;
    @Value("${messenger.hostname}")
    private String hostName;
    public Authentication mapAuthenticationUserDtoToAuthentication(AuthenticationRequestDto dto) {
        return new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
    }

    public UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = commonMapper.convertToDto(user, UserResponseDto.class);
        userResponseDto.setImagePath(convertImageName(user));
        return userResponseDto;
    }

    public SenderResponseDto userToSenderResponseDto(User user) {
        SenderResponseDto senderResponseDto = commonMapper.convertToDto(user, SenderResponseDto.class);
        senderResponseDto.setImagePath(convertImageName(user));
        return senderResponseDto;
    }

    public UserDetails userToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .toList()
        );
    }

    private String convertImageName(User user) {
        if (user.getImagePath() != null) {
            return String.format("%s%s/%s", hostName, IMAGES_MAIN_PATH, user.getImagePath());
        }
        return null;
    }
}
