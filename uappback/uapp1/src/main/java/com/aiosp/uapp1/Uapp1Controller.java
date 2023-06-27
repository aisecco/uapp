package com.aiosp.uapp1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Uapp1Controller {

    private static final String template = "Hello, %s!";
    private static AtomicLong counter = new AtomicLong();

    @GetMapping("/uappgreeting")
    public Greeting uappgreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
