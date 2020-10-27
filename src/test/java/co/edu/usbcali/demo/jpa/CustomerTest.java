package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import co.edu.usbcali.demo.domain.Customer;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class) // No permite dar el orden  de ejecución de las pruebas 
class CustomerTest {
	
	private final static String email="daledro-1998@hotmail.com";
	
	// sfl4fj
	private final static Logger log=LoggerFactory.getLogger(CustomerTest.class);
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		// verifica que el Entity no sea nulo , Siga si no es nulo
		assertNotNull(entityManager,"El entityManager es nulo");
		
		// Si no es nulo, el procedimiento continua
		
		// se carga la siguiente accion a realizar
		Customer customer = entityManager.find(Customer.class, email);
		
		// Si la condicion Es nula continuar el procedimiento
		assertNull(customer,"El cliente con email"+email+" No esta registrado");
		
		// Si es nulo ( no existe) , el procedimiento continua
		
		customer = new Customer();
		customer.setAddress("avenica siempre viva 123");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("David Anduquia");
		customer.setPhone("312 124 5321");
		customer.setToken("NKJH43232KJ423KJ4234");
		
		
		entityManager.persist(customer);
	}
	
	
	@Test
	@Transactional
	@Order(2)
	void findId() {
		
		// verifica que el Entity no sea nulo -> Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
			
		// 
		Customer customer = entityManager.find(Customer.class, email);
		//Siga si NO es nulo
		
		assertNotNull(customer , "el cliente con email "+email+ "  ya esta registrado ");
		log.info(customer.getName());		
	}
	
	@Test
	@Transactional
	@Order(3)
	void upDate() {
		// verifica que el Entity no sea nulo -> siga si no es nulo 
		assertNotNull(entityManager,"El entityManager es nulo");
		// 
		Customer customer = entityManager.find(Customer.class,email);
		
		assertNotNull(customer,"el cliente con email"+ email + " no existe");
		customer.setEnable("N");
		
		entityManager.merge(customer);
	}
	
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
	 // verifica que el Entity no sea nulo -> siga si no es nulo
		assertNotNull(entityManager,"El entityManager es nulo");
		
		Customer customer= entityManager.find(Customer.class,email);
		
		assertNotNull(customer,"El cliente con email"+email+"ya esta registrado");
		
		entityManager.remove(customer);
	}
}