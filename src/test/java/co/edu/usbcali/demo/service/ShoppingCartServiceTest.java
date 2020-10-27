package co.edu.usbcali.demo.service;

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
 
import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class ShoppingCartServiceTest {
	
	
	private final static Logger log=LoggerFactory.getLogger(ShoppingCartServiceTest.class);
	private static final String email="abaglowbn@furl.net";
	private static Integer carId=null;
	private static final Integer payId=1;
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	PaymentMethodService paymentMethodService;
	
	@Test
 	@Order(1)
	void save() throws Exception {
		log.info("save ShoppingCartService");
		ShoppingCart shoppingCart=new ShoppingCart();
		shoppingCart.setItems(2);
		shoppingCart.setTotal(15508700L);
		shoppingCart.setEnable("Y");
		
	 

		Optional<Customer> customerOptional=customerService.findById(email);		
		assertTrue(customerOptional.isPresent(),"El customer con email "+email+" No existe");
		
		Customer customer=customerOptional.get();
		shoppingCart.setCustomer(customer);
		
		Optional<PaymentMethod> paymentMethodOptional=paymentMethodService.findById(payId);		
		assertTrue(paymentMethodOptional.isPresent(),"El PaymentMethod con id "+payId+" No existe");
		
		PaymentMethod paymentMethod=paymentMethodOptional.get();
		shoppingCart.setPaymentMethod(paymentMethod);
		
		
		shoppingCart=shoppingCartService.save(shoppingCart);

		carId=shoppingCart.getCarId();
		
		assertNotNull(carId, "El carId es nulo");
	}
	
	@Test
	@Order(2)
	void findById() throws Exception {
		Optional<ShoppingCart> shoppingCartServiceOptional=shoppingCartService.findById(carId);
		assertTrue(shoppingCartServiceOptional.isPresent(),"El shoppingCartServiceOptional con carId "+carId+" No existe");
 
	}

	
	@Test 
	@Order(3)
	void update() throws Exception {
		Optional<ShoppingCart> shoppingCartServiceOptional=shoppingCartService.findById(carId);
		assertTrue(shoppingCartServiceOptional.isPresent(),"El shoppingCartOptional con carId "+carId+" No existe");
		
		ShoppingCart shoppingCart=shoppingCartServiceOptional.get();
		shoppingCart.setEnable("N");
		
		shoppingCartService.save(shoppingCart);	
	}

	@Test
	@Order(4)
	void delete() throws Exception {
	Optional<ShoppingCart> shoppingCartServiceOptional=shoppingCartService.findById(carId);
	assertTrue(shoppingCartServiceOptional.isPresent(),"El shoppingCartServiceOptional con carId "+carId+" No existe");
	
	ShoppingCart shoppingCart=shoppingCartServiceOptional.get();		
	shoppingCartService.delete(shoppingCart);	
	}
	
}
