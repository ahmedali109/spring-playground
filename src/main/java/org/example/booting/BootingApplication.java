package org.example.booting;

import org.example.booting.data.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootingApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EmployeeRepository repo) {
        return args -> {
            repo.findAll().forEach(System.out::println);
        };
    }
}
