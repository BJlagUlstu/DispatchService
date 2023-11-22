package com.example.messenger.services.impls;

import com.example.messenger.domain.User;
import com.example.messenger.dto.user.UpdateUserRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import com.example.messenger.repositories.UserRepository;
import com.example.messenger.services.ImageService;
import com.example.messenger.services.mappers.CommonMapper;
import com.example.messenger.services.UserService;
import com.example.messenger.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Override
    public UserResponseDto loadUserByLoginIfExist(String login) {
        User user = userRepository.findByEmail(login).orElseThrow();
        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> loadUsersByQuery(String pattern) {
        String nameLikeQuery = String.format("%%%s%%", pattern.toLowerCase());
        return ResponseEntity.ok(
                userRepository.findByNameLikeIgnoreCaseOrderByName(nameLikeQuery)
                        .stream()
                        .map(userMapper::userToUserResponseDto)
                        .toList()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> updateImage(MultipartFile image, String userEmail) {
        String newImageName = imageService.saveImage(image);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        user.setImagePath(newImageName);
        userRepository.flush();

        return ResponseEntity.ok(userMapper.userToUserResponseDto(user));
    }

    @Override
    @Transactional
    public void updateActiveStatus(boolean active, String email) {
        LocalDateTime nowTime = LocalDateTime.now();
        userRepository.updateActive(email, active, nowTime);
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUserName(UpdateUserRequestDto requestDto, String userEmail) {
        User user = userRepository.updateUser(requestDto.getName(), userEmail);
        return ResponseEntity.ok(userMapper.userToUserResponseDto(user));
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(UpdateUserRequestDto requestDto, MultipartFile image, String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (image != null) {
            String newImageName = imageService.saveImage(image);
            user.setImagePath(newImageName);
        }
        if (requestDto != null) {
            user.setName(requestDto.getName());
        }

        userRepository.flush();

        return ResponseEntity.ok(userMapper.userToUserResponseDto(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with username %s not found", username));
        }
        return userMapper.userToUserDetails(user.get());
    }
}
