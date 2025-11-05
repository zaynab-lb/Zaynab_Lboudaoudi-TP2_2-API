package com.example.filieresservice;

import com.example.filieresservice.configuration.RsaKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class)
public class FilieresServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilieresServiceApplication.class, args);
    }

}
