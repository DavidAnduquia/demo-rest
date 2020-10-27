package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;


 
@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepositoryTest {

	private final static String pro_id="IphonUltraX";
	
	private final static Logger log=LoggerFactory.getLogger(ProductRepository.class);
	
	@Autowired
	ProductRepository productRespository;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		log.info(" modo save test ");
		Optional<Product> productOptional = productRespository.findById(pro_id);
		
		 assertFalse(productOptional.isPresent(),"El product ya existe");

		// Siga si es falso , quiere decir que no existe
		
		Product producto = new Product();
		producto.setProId(pro_id);
		producto.setPrice(4509680);
		producto.setName("Iphone ultra mega x");
		producto.setImage("www.iphon4ever");
		producto.setDetail("Made in chine");
		producto.setEnable("Y");
		
		productRespository.save(producto);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		log.info("modo findById test ");
		Optional<Product> productOptional = productRespository.findById(pro_id);
		assertTrue(productOptional.isPresent(),"El producto no existe");
	
	}
	
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		log.info("modo update test" );
		 Optional<Product> productOptional= productRespository.findById(pro_id);
		 
		 assertTrue(productOptional.isPresent(),"El customer no existe");
		 
		 Product product = productOptional.get();
		 product.setEnable("N");
		 
		 
		 productRespository.save(product);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete(){
		
		log.info(" mode delete test ");
		
		Optional<Product> productOptional = productRespository.findById(pro_id);
		
		assertTrue(productOptional.isPresent()," EL producto no existe");
		
		
		Product product = productOptional.get();
		
		product.setEnable("N");
		
		productRespository.delete(product);
	}
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		productRespository.findAll().forEach(product->{
			log.info("Name:"+ product.getProId());
			log.info("Email:"+product.getName());
		});
	}
	
	
	
	@Test
	@Transactional
	@Order(6)
	void count() {
		log.info("Count:"+productRespository.count()); 
	}
	
	// Personalizados
	@Test
	@Transactional
	@Order(7)
	void findProductMoreExpensive() {
		log.info(" modo save test ");
		List<Product> products = productRespository.findProductMoreExpensive();
		
		assertFalse(products.isEmpty());
		 
		products.forEach(customer->{
			log.info("Price :"+ customer.getPrice());
			log.info("Name product :"+customer.getName());
		});
	}

	@Test
	@Transactional
	@Order(8)
	void findAllByOrderByPriceDesc () {
		log.info("modo findById test ");
		List<Product> productOptional = productRespository.findAllByOrderByPriceDesc();
		assertFalse(productOptional.isEmpty());
		
	}
	
	@Test
	@Transactional
	@Order(9)
	void findByEnable () {
		log.info("modo findById test ");
		List<Product> products = productRespository.findByEnable("Y");
		
		assertFalse(products.isEmpty());
		
		products.forEach(product->{
			log.info("Price :"+ product.getPrice());
			log.info("Name product :"+product.getName());
		});
		
	}
	
	
	@Test
	@Transactional
	@Order(10)
	void findByNameIgnoreCase() {
		log.info("modo findById test ");
		List<Product> products = productRespository.findByNameIgnoreCase("ipad pro");
		
		assertFalse(products.isEmpty());
		
		products.forEach(product->{
			log.info("Price :"+ product.getPrice());
			log.info("Name product :"+product.getName());
		});
		
	}
	
}
