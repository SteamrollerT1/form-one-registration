package com.dsw.studentvacancyallocater.configs;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dsw.studentvacancyallocater")
public class SecurityConfig {

    /*@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }*/
}
