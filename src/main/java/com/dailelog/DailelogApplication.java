package com.dailelog;

import com.dailelog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class DailelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailelogApplication.class, args);
    }

}
