package com.spring.jpa.springbootjpasample;

import com.spring.jpa.springbootjpasample.controller.ProductController;
import com.spring.jpa.springbootjpasample.controller.SampleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// bootstraps entire spring container
@SpringBootTest
class SpringBootJpaSampleApplicationTests {

	@Autowired
	ProductController productController;

	@Autowired
	SampleController sampleController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(productController);
		Assertions.assertNotNull(sampleController);
	}

}
