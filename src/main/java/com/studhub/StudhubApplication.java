package com.studhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.class})
public class StudhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudhubApplication.class, args);
    }

}
