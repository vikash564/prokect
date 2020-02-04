package com.observer.pattern;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableCaching
@EnableAutoConfiguration()
@EnableJpaRepositories(basePackages = "com.observer.pattern")
@ComponentScan(basePackageClasses = { MainClass.class })
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class MainClass {


    public static void main(String[] args)
    {
        //logger.info("********** starting observer pattern **********");
        SpringApplication.run(MainClass.class,args);
    }
}
