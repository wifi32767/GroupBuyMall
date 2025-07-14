package com.wifi32767.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.wifi32767.infra.dao")
public class Application {

    public static void main(String[] args){
        System.out.println("Spring Boot: " + org.springframework.boot.SpringBootVersion.getVersion());
        System.out.println("Spring Framework: " + org.springframework.core.SpringVersion.getVersion());
        SpringApplication.run(Application.class);
    }

}