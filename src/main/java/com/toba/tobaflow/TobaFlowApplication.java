package com.toba.tobaflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class TobaFlowApplication implements CommandLineRunner {

    public static void main(String[] args) {

        System.setProperty("spring.devtools.livereload.enabled", "true");
        SpringApplication.run(TobaFlowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("## Started TobaFlow Application...");
    }
}
