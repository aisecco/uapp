package com.aiosp.demo;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyContextHelper {

    static private Map<String, MyClassLoader> myClassLoaderCenter = new ConcurrentHashMap<>();

    public static void loadJar(ConfigurableApplicationContext context, String path, String fileName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        File file = new File(path + "/" + fileName);
        // 获取beanFactory
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();

        try {
            URL url = new URL("jar:file:" + file.getAbsolutePath() + "!/");
            URLConnection urlConnection = url.openConnection();
            JarURLConnection jarURLConnection = (JarURLConnection) urlConnection;
            // 创建自定义类加载器，并加到map中方便管理
            MyClassLoader myClassloader = new MyClassLoader(new URL[]{url}, ClassLoader.getSystemClassLoader());
            myClassLoaderCenter.put(fileName, myClassloader);
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            // 遍历文件
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    // 1. 将类加载到jvm
                    String className = jarEntry.getName().replace('/', '.').substring(0, jarEntry.getName().length() - 6);
                    // 1.1 反射获取
                    Class clazz = myClassloader.loadClass(className);
                    // 1.2 实例化
                    Object instance = clazz.newInstance();

                    // 2. 将@spring注解的类交给 Spring管理
                    // 2.1 判断是否注入spring
                    Boolean flag = SpringAnnotationUtils.hasSpringAnnotation(clazz);
                    if (flag) {
                        // 2.2交给spring管理
                        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                        // 此处beanName使用全路径名是为了防止beanName重复
                        String packageName = className.substring(0, className.lastIndexOf(".") + 1);
                        String beanName = className.substring(className.lastIndexOf(".") + 1);
                        beanName = packageName + beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                        // 2.3注册到spring的beanFactory中
                        beanFactory.registerBeanDefinition(beanName, beanDefinition);
                        // 2.4允许注入和反向注入
                        beanFactory.autowireBean(clazz);
                        beanFactory.initializeBean(clazz, beanName);
                    }
                }
            }
        } catch (IOException e) {
//            logger.error("读取{} 文件异常", fileName);
            e.printStackTrace();
            throw new RuntimeException("读取jar文件异常: " + fileName);
        }
    }
}
