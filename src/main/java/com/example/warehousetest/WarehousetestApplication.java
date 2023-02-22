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
	/**
	 * This method available for resolving a SessionFactory by JPA persistence unit name
	 *
	 */
//	@Bean
//	public HibernateJpaSessionFactoryBean sessionFactory() {
//		return new HibernateJpaSessionFactoryBean();
//	}
//
//	@Bean
//	MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		factory.setMaxFileSize("5120MB");
//		factory.setMaxRequestSize("5120MB");
//		return factory.createMultipartConfig();
//	}

}
