package com.hanghae.justpotluck;

import com.hanghae.justpotluck.global.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class JustPotluckApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustPotluckApplication.class, args);
    }

}
