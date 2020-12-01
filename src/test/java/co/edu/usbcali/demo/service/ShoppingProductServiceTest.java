package co.edu.usbcali.demo.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingProduct;


@SpringBootTest
@Rollback(false)
class ShoppingProductServiceTest {

	private final static Logger log=LoggerFactory.getLogger(ShoppingProductServiceTest.class);
	private static final String productId="APPL90";
	private static  Integer ShprId=null;

	
	@Autowired
	ShoppingProductService shoppingProductService;
	
	@Autowired
	ProductService productService;
	
	@Test
	void test() throws Exception{
		// Arrange
		Long total=0L;
		Integer carId=38;
		// Act
		total=shoppingProductService.totalShoppingProductByShoppingCart(carId);
		//Assert
		log.info("Eltotal es" + total);
		assertTrue(total>0);
	}
	
	@Test
 	@Order(1)
	void save() throws Exception {
		log.info("save");
		
		ShoppingProduct shoppingProduct = new ShoppingProduct();
		shoppingProduct.setShprId(1);
		shoppingProduct.setQuantity(2);
		shoppingProduct.setTotal(2000000L);
		
		Optional<Product> productOptional=productService.findById(productId);		
		assertTrue(productOptional.isPresent(),"El product con productId "+productId+" No existe");
		Product product=productOptional.get();
		shoppingProduct.setProduct(product);
		
		shoppingProduct=shoppingProductService.save(shoppingProduct);
		ShprId=shoppingProduct.getShprId();
		assertNotNull(ShprId, "El ShprId es nulo");
	}
	
	@Test
 	@Order(2)
	void findById() throws Exception  {
		log.info("findById");
		Optional<ShoppingProduct> shoppingProductServiceOptional=shoppingProductService.findById(ShprId);
		assertTrue(shoppingProductServiceOptional.isPresent(),"El shoppingProductServiceOptional con carId "+ShprId+" No existe");
	}
	
	@Test
 	@Order(3)
	void update() throws Exception  {
		log.info("update");
		
		Optional<ShoppingProduct> shoppingProductServiceOptional=shoppingProductService.findById(ShprId);
		assertTrue(shoppingProductServiceOptional.isPresent(),"El shoppingProductServiceOptional con carId "+ShprId+" No existe");
		
		ShoppingProduct shoppingProduct=shoppingProductServiceOptional.get();
		shoppingProduct.setQuantity(3);
		
		shoppingProductService.save(shoppingProduct);	
	}
	
	@Test
 	@Order(4)
	void delete() throws Exception {
		log.info("delete");
		Optional<ShoppingProduct> shoppingProductServiceOptional=shoppingProductService.findById(ShprId);
		assertTrue(shoppingProductServiceOptional.isPresent(),"El shoppingProductServiceOptional con carId "+ShprId+" No existe");
	
		ShoppingProduct shoppingProduct=shoppingProductServiceOptional.get();		
		shoppingProductService.delete(shoppingProduct);	
	}

}
