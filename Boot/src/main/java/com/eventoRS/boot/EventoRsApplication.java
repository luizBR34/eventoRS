package com.eventoRS.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.eventoRS")
public class EventoRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoRsApplication.class, args);
	}

}
