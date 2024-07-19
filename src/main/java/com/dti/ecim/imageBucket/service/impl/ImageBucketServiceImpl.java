package com.dti.ecim.imageBucket.service.impl;

import com.dti.ecim.imageBucket.service.ImageBucketService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.cloudinary.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageBucketServiceImpl implements ImageBucketService {

    @Resource
    private Cloudinary cloudinary;

    private final List<String> ALLOWED_FORMAT = List.of("jpg", "jpeg", "png", "webp");

    @Override
    public String uploadImage(MultipartFile file, String folderName) throws IOException {
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", folderName);
        options.put("allowed_format", ALLOWED_FORMAT);
        Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
        String publicId = (String) uploadedFile.get("public_id");
        return cloudinary.url().secure(true).generate(publicId);
    }
}
