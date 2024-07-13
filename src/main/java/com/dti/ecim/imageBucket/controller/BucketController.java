package com.dti.ecim.imageBucket.controller;

import com.dti.ecim.imageBucket.service.ImageBucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Log
@RequestMapping("/api/v1/bucket")
public class BucketController {
    private final ImageBucketService imageBucketService;

    @GetMapping
    public void upload() throws IOException {
        imageBucketService.uploadImage();
    }
}
