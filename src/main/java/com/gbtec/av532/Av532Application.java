package com.gbtec.av532;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.gbtec.av532.repositories")
@EnableScheduling
public class Av532Application {

    public static void main(String[] args) {
        SpringApplication.run(Av532Application.class, args);
    }

}
