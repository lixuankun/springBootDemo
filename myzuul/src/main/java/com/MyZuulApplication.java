package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
@EnableFeignClients

public class MyZuulApplication {

    public static void main(String[] args) {
//		SpringApplication.run(MyZuulApplication.class, args);
        new SpringApplicationBuilder(MyZuulApplication.class).web(true).run(args);
    }
}
