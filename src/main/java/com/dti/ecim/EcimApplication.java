package com.dti.ecim;

import com.dti.ecim.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
@Log
public class EcimApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcimApplication.class, args);
    }

}
