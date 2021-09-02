package com.eventoRS.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients("com.eventoRS.clients")
//@EnableEurekaClient
@ComponentScan("com.eventoRS")
public class EventoRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoRsApplication.class, args);
	}

}
