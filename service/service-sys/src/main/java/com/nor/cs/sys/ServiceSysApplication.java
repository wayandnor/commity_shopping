package com.nor.cs.sys;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nor.cs")
public class ServiceSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSysApplication.class,args);
    }
}
