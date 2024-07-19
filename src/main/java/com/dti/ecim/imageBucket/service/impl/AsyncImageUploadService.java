package com.dti.ecim.imageBucket.service.impl;

import com.dti.ecim.imageBucket.service.ImageBucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class AsyncImageUploadService {
    private ImageBucketService imageBucketService;

    @Async
    public CompletableFuture<String> uploadImageAsync(MultipartFile file, String folder) {
        try {
            String imageSrc = imageBucketService.uploadImage(file, folder);
            return CompletableFuture.completedFuture(imageSrc);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
