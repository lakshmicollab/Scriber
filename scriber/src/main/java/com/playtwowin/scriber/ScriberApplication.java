package com.playtwowin.scriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.playtwowin")
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EntityScan(basePackages = "com.playtwowin")
public class ScriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScriberApplication.class, args);
	}

}
