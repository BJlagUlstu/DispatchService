package com.example.messenger.controllers;

import com.example.messenger.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.messenger.utils.Constants.IMAGES_MAIN_PATH;

@RestController
@RequestMapping(IMAGES_MAIN_PATH)
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @GetMapping("/{image-id}")
    public ResponseEntity<byte[]> loadImage(@PathVariable("image-id") String imageId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.IMAGE_PNG)
                .body(imageService.loadImage(imageId));
    }
}
