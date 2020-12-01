package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class ShoppingProductRepositoryTest {
	
	
	private final static Logger log=LoggerFactory.getLogger( ShoppingProduct.class);
	
	private static Integer sphrId=null;
	
	private static final String proId="APPL90";
			
	private static final Integer carId=1;
	
	
	@Autowired
	ShoppingProductRepository shoppingProductRepository;
	
	@Autowired
	ShoppingCartRepository shoppingCartRespository;
	
	@Autowired
	ProductRepository productRepository;
	
	
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		log.info("holi");
		ShoppingProduct shoppingProduct=new ShoppingProduct();
		shoppingProduct.setShprId(null);
		shoppingProduct.setQuantity(5);
		shoppingProduct.setTotal((long)50000);
 			
		Optional<Product> ProductOptional =productRepository.findById(proId);
		assertTrue(ProductOptional.isPresent(),"El Producto Con Identificador"+proId+" No existe"  );
		
		Product product =ProductOptional.get();
		shoppingProduct.setProduct(product);
		
		Optional<ShoppingCart> shoppingCartOptional =shoppingCartRespository.findById(carId);
		assertTrue(shoppingCartOptional.isPresent(),"El customer Con email"+carId+" No existe"  );
		
		ShoppingCart shoppingCart=  shoppingCartOptional.get();
		shoppingProduct.setShoppingCart(shoppingCart);
		
		shoppingProduct = shoppingProductRepository.save(shoppingProduct);
		
		sphrId=shoppingProduct.getShprId();
		assertNotNull(sphrId,"El CardId Es nulo");
		 
		
	}
	
	
	@Test
	@Transactional
	@Order(2)
	void finById() {
		//sphrId=1;
		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductRepository.findById(sphrId);
		assertNotNull(shoppingProductOptional.isPresent(),"El ShoppingProduct con ID "+sphrId+" No existe ");
	 
		
	}
	@Test
	@Transactional
	@Order(3)
	void update() {

		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductRepository.findById(sphrId);
		
		assertTrue(shoppingProductOptional.isPresent(),"El ShoppingProduct con ID "+sphrId+" No existe ");
		
		ShoppingProduct shoppingProduct =  shoppingProductOptional.get();
		shoppingProduct.setQuantity(3);
		
		shoppingProductRepository.save(shoppingProduct);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {

				
		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductRepository.findById(sphrId);
		
		assertTrue(shoppingProductOptional.isPresent(),"El ShoppingProduct con ID "+sphrId+" No existe ");
		
		ShoppingProduct shoppingProduct =  shoppingProductOptional.get();
		shoppingProductRepository.delete(shoppingProduct);
		
	}

}
