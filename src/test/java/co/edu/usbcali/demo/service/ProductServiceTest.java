package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {

	private final static Logger log=LoggerFactory.getLogger(CustomerServiceTest.class);
	
	private final static String pro_id="IphoneUltraG";
	 
	
	@Autowired
	ProductService productService;
	
	@Test
	@Order(1)
	void save()throws Exception {
		log.info("save");
		
		Product producto = new Product();
		producto.setProId(pro_id);
		producto.setPrice(4509680);
		producto.setName("Iphone ultraG");
		producto.setImage("www.iphon4ever");
		producto.setDetail("Made in chine");
		producto.setEnable("Y");
	
		productService.save(producto);
		
	}
	
	@Test
	@Order(2)
	void findById() throws Exception{
		log.info("findById");
	
		Optional<Product> productOptional=productService.findById(pro_id);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(productOptional.isPresent(),"El customer no existe");
	}
	
	@Test
	@Order(3)
	void update()throws Exception {
		log.info("update");
	
		Optional<Product> productOptional=productService.findById(pro_id);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(productOptional.isPresent(),"El customer no existe");
		
		Product product=productOptional.get();
		
		product.setEnable("N");
		
		productService.update(product);		
	}
	
	@Test
	@Order(4)
	void delete()throws Exception {		
		log.info("delete");
	
		Optional<Product> productOptional=productService.findById(pro_id);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(productOptional.isPresent(),"El customer no existe");
		
		Product product=productOptional.get();
		
		productService.delete(product);		
	}
}
