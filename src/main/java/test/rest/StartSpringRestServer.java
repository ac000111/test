package test.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "test", "test.rest" })
public class StartSpringRestServer {
    public static void main(String[] argv) throws Exception {
        SpringApplication.run(StartSpringRestServer.class);
    }

    @Bean
    public ExecutorService getExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
