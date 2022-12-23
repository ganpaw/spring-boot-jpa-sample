package com.spring.jpa.springbootjpasample;

import com.spring.jpa.springbootjpasample.controller.ProductController;
import com.spring.jpa.springbootjpasample.controller.SampleController;
import com.spring.jpa.springbootjpasample.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

//Integration test - bootstraps entire spring container
@SpringBootTest
class SpringBootJpaSampleApplicationTests {

	@Autowired
	ProductController productController;

	@Autowired
	SampleController sampleController;

	@Autowired
	EntityManager entityManager;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(productController);
		Assertions.assertNotNull(sampleController);
	}

	@Test
	@Transactional
	@Commit
	public void accessHibernateSessionTest(){
		Product p = new Product();
		p.setName("IPhone X");
		p.setQuantity(10);
		p.setPrice(999.99);
		entityManager.persist(p);
	}
}
