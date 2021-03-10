package com.dsw.studentvacancyallocater.configs;


import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dsw.studentvacancyallocater")
public class MainConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    ActorSystem actorSystem() {
        ActorSystem actorSystem = ActorSystem.create("actor-system", ConfigFactory.load());
        SpringExtension.getInstance().get(actorSystem).initialize(applicationContext);
        return actorSystem;
    }
}
