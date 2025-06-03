package com.swp08.dpss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
public class DrugPreventSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrugPreventSystemApplication.class, args);

    }
}


