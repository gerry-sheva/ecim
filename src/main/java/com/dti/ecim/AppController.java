package com.dti.ecim;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class AppController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
