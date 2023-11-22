package com.example.messenger.services;

import com.example.messenger.exceptions.ApiRequestException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    /**
     * @return saved picture path
     */
    public String saveImage(MultipartFile file) throws ApiRequestException;
    public byte[] loadImage(String imageName) throws ApiRequestException;
}
