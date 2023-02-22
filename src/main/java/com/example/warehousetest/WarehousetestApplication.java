package com.example.warehousetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class WarehousetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousetestApplication.class, args);
	}

}
