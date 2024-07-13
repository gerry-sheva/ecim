package com.dti.ecim.imageBucket.service.impl;

import com.dti.ecim.imageBucket.service.ImageBucketService;
import org.springframework.stereotype.Service;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageBucketServiceImpl implements ImageBucketService {

    // Set your Cloudinary credentials



    @Override
    public void uploadImage() throws IOException {
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        System.out.println(cloudinary.config.cloudName);

        Map params1 = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );

        System.out.println(
                cloudinary.uploader().upload("https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", params1));

    }
}
