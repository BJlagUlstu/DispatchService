package com.example.messenger.repositories;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageRepository {
    /**
     * @return saved picture name
     */
    public String saveImage(MultipartFile file) throws IOException;
    public byte[] loadImage(String imageName) throws IOException;
}
