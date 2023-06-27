package com.aiosp.demo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@SpringBootApplication
@ComponentScan("com.aiosp")
public class DemoApplication {

//    @Value("${dynamicLoad.interface1impl:UappInterface1ImplOut1}")
//    private static String ImplName;

    static private Map<String, MyClassLoader> myClassLoaderCenter = new ConcurrentHashMap<>();

    static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(DemoApplication.class, args);

        System.out.println("Demo is ready!");

//        String path = "/Users/haio/Dev/java/uappdemo/uapp1/target/";
//        String fileName = "uapp1-0.0.1-SNAPSHOT.jar";

//        String path = "/Users/haio/Dev/java/uappdemo/uinterface1/target/";
//        String fileName = "uinterface1-0.0.1-SNAPSHOT.jar";

        String path = "/Users/haio/Dev/java/uappdemo/uapp2/target/";
        String fileName = "uapp2-0.0.1-SNAPSHOT.jar";
        try {

            MyContextHelper.loadJar(context, path, fileName);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        //        context.getBeanDefinitionNames();
        String[] names = context.getBeanDefinitionNames();

        System.out.println("------<");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("------>");

//        String classString = "com.aiosp.demo.DemoController";
//        String classString = "com.aiosp.uinterface1.uappInterface1";
//        String classString = "com.aiosp.uapp1.Impl4Interface1";

//        String classString = "com.aiosp.demo.UappInterface1ImplOut1"; // its OK
//        String classString = DemoConfig.ImplJarPath; // ImplName;
//        String classString = "UappInterface1ImplOut1";  //is NOT OK

//        testload(classString);
    }

    static void testload(String classString) {
        try {
            Class<?> cls = context.getClassLoader().loadClass(classString);

            Method[] methods = cls.getDeclaredMethods();

            for (Method invokeMethod : methods) {
                Object obj = 1;

//                try {
//
////                    invokeMethod.invoke(null, obj);
//                    invokeMethod.invoke(context.getBean(classString), obj);
//
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//                invokeMethod.invoke( );
                Annotation[] annotations = invokeMethod.getAnnotations();

                int annotationCount = 0;
                for (Annotation annotation : annotations) {
                    annotationCount++;
                }

                System.out.println("Annotation Count: " + annotationCount);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        BeanFactory bf = context.getBeanFactory();

//        DemoController controller = new DemoController();
//        controller.showVersion();
    }


}
