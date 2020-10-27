package co.edu.usbcali.demo.jpql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.usbcali.demo.domain.Customer;

 
 


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TestCustomerJQPL {
	
	private final static Logger log=LoggerFactory.getLogger(TestCustomerJQPL.class);
	
	
	@Autowired
	public EntityManager entityManager;
	
	@BeforeEach
	public void beforeEach() {
		log.info("before all ..");
		assertNotNull(entityManager," El entityManager es nulo");
	}
	
	public void selectWhereParam() {
		log.info("select WhereParam ..");
		String jpql = " SELECT cus FROM Customer cus WHERE cus.enable = 'Y' AND cus.email=:email";
		
		 List<Customer> customers =  entityManager.createQuery(jpql,Customer.class)
				 .setParameter("enable", "Y")
				 .setParameter("email", "jfilintsevrg@wikipedia.org")
				 .getResultList();
		
		 customers.forEach(customer->{
				log.info(customer.getEmail());
				log.info(customer.getName());
				log.info(customer.getEnable());
		 });
		 
	}
	
	@Test
	public void selectWhereEnable() {
		log.info("select WhereEnable ..");
		String jpql = " SELECT cus FROM Customer cus WHERE cus.enable = 'Y' ORDER BY cus.email";
		
		 List<Customer> customers =  entityManager.createQuery(jpql,Customer.class).getResultList();
		
		 customers.forEach(customer->{
				log.info(customer.getEmail());
				log.info(customer.getName());
				log.info(customer.getEnable());
		 });
		 
	}
	
	
	@Test
	public void selectLike() {
		log.info("select all ..");
		String jpql = " SELECT cus FROM Customer cus WHERE cus.name LIKE 'Mar%'";
		
		 List<Customer> customers =  entityManager.createQuery(jpql,Customer.class).getResultList();
		
		 customers.forEach(customer->{
				log.info(customer.getEmail());
				log.info(customer.getName());
		 });
		 
	}
	
	@Test
	public void selectAll() {
		log.info("select all ..");
		String jpql = " SELECT cus FROM Customer cus";
		
		// PRIMERA FORMA
		
		
		//  List<Customer> customers =  entityManager.createQuery(jpql).getResultList();
		// Para quitar el warning
		 List<Customer> customers =  entityManager.createQuery(jpql,Customer.class).getResultList();
		
		 customers.forEach(customer->{
				log.info(customer.getEmail());
				log.info(customer.getName());
		 });
		 
		//for(Customer customer : customers) {
		//	log.info(customer.getEmail());
		//	log.info(customer.getName());
	    //	}
	}
}
