package com.aiosp.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.aiosp")
public class Demo2Application {


    static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(Demo2Application.class, args);

        System.out.println("Demo2 is ready!");
    }
}
