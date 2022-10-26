package com.epam.esm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@SpringBootApplication
//@ComponentScan(basePackages = "com.epam.esm")
//@EnableJpaRepositories("com.epam.esm")
@SpringBootApplication(scanBasePackages = "com.epam.esm",exclude = HibernateJpaAutoConfiguration.class)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
//
//}
//public class Application implements WebMvcConfigurer {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
}
