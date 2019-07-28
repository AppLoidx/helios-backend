package com.apploidxxx.heliosbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaRepositories("com.apploidxxx.heliosbackend.data.repository")
public class HeliosBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeliosBackendApplication.class, args);
    }

}
