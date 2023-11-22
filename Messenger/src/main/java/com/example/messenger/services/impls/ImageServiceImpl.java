package com.example.messenger.services.impls;

import com.example.messenger.exceptions.ApiRequestException;
import com.example.messenger.repositories.ImageRepository;
import com.example.messenger.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    @Override
    public String saveImage(MultipartFile file) {
        try {
            return imageRepository.saveImage(file);
        } catch (IOException exception) {
            throw new ApiRequestException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] loadImage(String imageName) {
        try {
            return imageRepository.loadImage(imageName);
        } catch (IOException exception) {
            throw new ApiRequestException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
