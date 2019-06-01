package com.backend.roxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Roxi酱
 */
@MapperScan("com.backend.roxi.mapper")
@SpringBootApplication
public class RoxiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoxiApplication.class, args);
    }

}
