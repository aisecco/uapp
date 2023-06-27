package com.aiosp.demo;

import com.aiosp.uapp1.UappModelExt1;
import com.aiosp.uinterface1.UappInterface1;
import com.aiosp.uinterface1.UappModel;
import com.aiosp.uinterface1.UappResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



@RestController
public class DemoController {

//    @Autowired
//    Impl1 impl1;

//    @Value("${dynamicLoad.interface1impl:UappInterface1ImplOut1}")
//    private String info;

	@Autowired
	@Qualifier("Impl4Interface1")
    private UappInterface1 uappInterface1Impl;

    private String localImplJarPath = null;
//    @Autowired
//    @Qualifier("UappInterface1ImplOut2")
//    private UappInterface1 uappInterface1ImplOut2;
//
//    @Autowired
//	@Qualifier("Impl4Interface1")
//    private UappInterface1 impl4Interface1_2;


    public void showVersion() {
        System.out.printf("uappInterface1: %s", uappInterface1Impl.showVersion());
    }

    public String fetchVersion() {

        String version = uappInterface1Impl.showVersion();
        return version;
    }

    private static final String template = "Hello, %s! message: %s";
    private static AtomicLong counter = new AtomicLong();

    @GetMapping("/info")
    public Greeting info() {

        String info = "uApp";
        return new Greeting(counter.incrementAndGet(), String.format(template, info));
    }

    @GetMapping("/version")
    public Greeting version() {

        String version = fetchVersion();
        return new Greeting(counter.incrementAndGet(), String.format(template, version));
    }

    @GetMapping("/selectjarpath")
    public Greeting selectjarpath(@RequestParam(value = "path", defaultValue = "com.aiosp.uapp1.Impl4Interface1") String path) {

        ApplicationContext context = new AnnotationConfigApplicationContext(DemoApplication.class);
        uappInterface1Impl = context.getBean(path, UappInterface1.class);

        String version = "ERROR";
        List<String> resultValue = null;

        List<UappModel> resultModule = null;

        UappResponseModel<UappModel> responseModel = null;
        UappModel uappModel = (UappModel)new UappModelExt1();

        String message = "Kong";

        List<UappModel> list ;

        if(uappInterface1Impl !=null)
        {
            version = uappInterface1Impl.showVersion();

//            resultModule = uappInterface1Impl.getList( uappModel);

            responseModel = uappInterface1Impl.getResponse( uappModel);
            message = responseModel.getMessage();
            list = responseModel.getResult();

            if ( path.equals("impl4Interface1")) {
                //根据不同的消费端进行转换，这里
                List<UappModelExt1> uappModelExt1List = new ArrayList<UappModelExt1>();

                for (UappModel uappModel1 : list) {
                    uappModelExt1List.add((UappModelExt1) uappModel1);
                }
            }

            if ( path.equals("uappInterface1ImplOut1")) {
                //根据不同的消费端进行转换，这里
                List<UappModelOut1> uappModelOut1List = new ArrayList<UappModelOut1>();

                for (UappModel uappModel1 : list) {
                    uappModelOut1List.add((UappModelOut1) uappModel1);
                }
            }
        }

        return new Greeting(counter.incrementAndGet(), String.format(template, version, message));
    }

    @GetMapping("/loadjar")
    public Greeting loadJar(@RequestParam(value = "path", defaultValue = "com.aiosp.uapp1.Impl4Interface1") String path) {

        ApplicationContext context = new AnnotationConfigApplicationContext(DemoApplication.class);

        String jarpath = "/Users/haio/Dev/java/uappdemo/uapp2/target/";
        String jarfileName = "uapp2-0.0.1-SNAPSHOT.jar";
        try {

            MyContextHelper.loadJar((ConfigurableApplicationContext) context, jarpath, jarfileName);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        String[] names = context.getBeanDefinitionNames();

        System.out.println("------<");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("------>");

        uappInterface1Impl = context.getBean(path, UappInterface1.class);

        String version = "ERROR";
        if(uappInterface1Impl !=null)
        {
            version = uappInterface1Impl.showVersion();
        }

        return new Greeting(counter.incrementAndGet(), String.format(template, version));
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        showVersion();
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
