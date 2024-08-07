package com.dti.ecim.imageBucket.controller;

import com.dti.ecim.imageBucket.service.ImageBucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/bucket")
public class BucketController {
    private final ImageBucketService imageBucketService;

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageBucketService.uploadImage(file, "avatar"));
    }

    @PostMapping("/event")
    public ResponseEntity<?> uploadEvent(MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageBucketService.uploadImage(file, "event"));
    }
}
