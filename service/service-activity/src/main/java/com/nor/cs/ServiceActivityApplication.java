package com.nor.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @author: Nor Way
 * @description:
 * @date 2023/7/16 14:55
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceActivityApplication.class, args);
    }
}
