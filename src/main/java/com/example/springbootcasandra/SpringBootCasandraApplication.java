package com.example.springbootcasandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
public class SpringBootCasandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCasandraApplication.class, args);
	}

}
