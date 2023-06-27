package com.aiosp.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author
 * @date
 */
//@Configuration
public class DemoConfig {

    public static String ImplJarPath = "Impl4Interface1";
//
//    @Value("${dynamicLoad.interface1impl}")
//    private String implJarPath;
//
//
//    @PostConstruct
//    public void getEnvironment() {
//
//        if (ImplJarPath == null) {
//            ImplJarPath = this.implJarPath;
//        }
//    }
}
