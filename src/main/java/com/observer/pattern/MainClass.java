package com.observer.pattern;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableCaching
@ComponentScan(basePackages = "com.observer")
public class MainClass {


    public static void main(String[] args)
    {
        //logger.info("********** starting observer pattern **********");
        SpringApplication.run(MainClass.class,args);
    }
}
