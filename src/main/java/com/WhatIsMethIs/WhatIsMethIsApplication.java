package com.WhatIsMethIs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class WhatIsMethIsApplication {

	public static void main(String[] args) {

		SpringApplication.run(WhatIsMethIsApplication.class, args);
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");

	}

}
