package com.aiosp.uapp1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Uapp1Application {

//    @Autowired
//    UappInterface1 uappInterface1 ;

    public static void main(String[] args) {
        SpringApplication.run(Uapp1Application.class, args);

        System.out.println("Uapp1 is ready!");

//        System.out.printf("impl4Interfce1 : %s", impl4Interfce1.showVersion() );
    }

}
