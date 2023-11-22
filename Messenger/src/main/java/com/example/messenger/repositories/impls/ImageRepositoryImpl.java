package com.example.messenger.repositories.impls;

import com.example.messenger.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Component
public class ImageRepositoryImpl implements ImageRepository {
    @Value("${pictures.path}")
    private String storageDirectoryPath;
    @Override
    public String saveImage(MultipartFile file) throws IOException {
        String imageExpansion = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        String randomFileName = UUID.randomUUID() + "." + imageExpansion;
        byte[] bytes = file.getBytes();
        try (OutputStream os = new FileOutputStream(String.format("%s//%s", storageDirectoryPath, randomFileName))) {
            os.write(bytes);
        }
        return randomFileName;
    }

    @Override
    public byte[] loadImage(String imageName) throws IOException {
        String imagePath = String.format("%s//%s", storageDirectoryPath, imageName);
        var imageFile = new File(imagePath);
        byte[] imageBytes;
        try (FileInputStream inputStream = new FileInputStream(imageFile)) {
            imageBytes = inputStream.readAllBytes();
        }
        return imageBytes;
    }
}
