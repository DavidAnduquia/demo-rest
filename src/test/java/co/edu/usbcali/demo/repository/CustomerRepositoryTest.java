package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

 

import java.util.List;
import java.util.Optional;
 

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import co.edu.usbcali.demo.domain.Customer;

 
@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class CustomerRepositoryTest {
	private final static String email="daledro-1998@hotmail.com";
	
	private final static Logger log=LoggerFactory.getLogger(Customer.class);
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		
		log.info("modo save test" );
		Optional<Customer> customerOptional= customerRepository.findById(email);
		 
		 // Siga si es falso quiere decir que no existe
		 assertFalse(customerOptional.isPresent(),"El customer ya existe");
		 
		 Customer customer = new Customer();
		
		customer.setAddress("avenica siempre viva 123");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("David Anduquia");
		customer.setPhone("312 124 5321");
		customer.setToken("NKJH43232KJ423KJ4234");
		
		customerRepository.save(customer);
		
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		
		log.info("modo findById test" );
		 Optional<Customer> customerOptional= customerRepository.findById(email);
		 
		 // Siga si es falso quiere decir que no existe
		 assertTrue(customerOptional.isPresent(),"El customer no existe");
		  
	}
	
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		
		log.info("modo update test" );
		 Optional<Customer> customerOptional= customerRepository.findById(email);
		 
		 assertTrue(customerOptional.isPresent(),"El customer no existe");
		 
		 Customer customer = customerOptional.get();
		 customer.setEnable("N");
		 
		 // .save()  ---> En repository sirve para guardar y actualizar
		 
		 customerRepository.save(customer);
		 
	}
	
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		
		log.info("modo delete test" );
		 Optional<Customer> customerOptional= customerRepository.findById(email);
		 
		 assertTrue(customerOptional.isPresent(),"El customer no existe");
		 
		 Customer customer = customerOptional.get();
		 customer.setEnable("N");
		 
		 // .save()  ---> En repository sirve para guardar y actualizar
		 
		 customerRepository.delete(customer);
		 
	}
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		// For funciona  l
		customerRepository.findAll().forEach(customer->{
			log.info("Name:"+ customer.getName());
			log.info("Email:"+customer.getEmail());
		});
		 
	}
	
	@Test
	@Transactional
	@Order(6)
	void count() {
		log.info("Count:"+customerRepository.count()); 
	}
	
	@Test
	@Transactional
	@Order(7)
	void findByEnableAndEmail() {
		List<Customer> customers=customerRepository.findByEnableAndEmail("Y", "jfilintsevrg@wikipedia.org");
		
		assertFalse(customers.isEmpty());
		 
		customers.forEach(customer->{
			log.info("Name:"+ customer.getName());
			log.info("Email:"+customer.getEmail());
		});
	}
	
	@Test
	@Transactional
	@Order(8)
	void findCustomerLikeMar() {
		List<Customer> customers=customerRepository.findCustomerLikeMar();
		
		assertFalse(customers.isEmpty());
		 
		customers.forEach(customer->{
			log.info("Name:"+ customer.getName());
			log.info("Email:"+customer.getEmail());
		});
	}
	
	// Personalizados
	
	@Test
	@Transactional
	@Order(9)
	void findFirstByOrderByNameAsc() {
		List<Customer> customers=customerRepository.findFirstByOrderByNameAsc();
		
		assertFalse(customers.isEmpty());
		 
		customers.forEach(customer->{
			log.info("Name:"+ customer.getName());
			log.info("Email:"+customer.getEmail());
		});
	}
	
	

}
