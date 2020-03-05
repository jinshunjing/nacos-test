package org.jim.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MyNacosConsumer {
    public static void main(String[] args) {
        SpringApplication.run(MyNacosConsumer.class, args);
    }
}
