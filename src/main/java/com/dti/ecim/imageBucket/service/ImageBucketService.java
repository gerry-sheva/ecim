package com.dti.ecim.imageBucket.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageBucketService {
    String uploadImage(MultipartFile file, String folderName) throws IOException;
}
