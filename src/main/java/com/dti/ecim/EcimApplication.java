package com.dti.ecim;

import com.dti.ecim.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
@ComponentScan(basePackages = {"com.dti.ecim", "com.dti.ecim.config"})
@Log
public class EcimApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcimApplication.class, args);
    }
}
