package com.stocktalkhub.stocktalkhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NewsfeedModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsfeedModuleApplication.class, args);
	}

}
