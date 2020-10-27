package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;

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

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class) // No permite dar el orden  de ejecución de las pruebas 
class ProductTest {
	
		private final static String pro_id="IphonUltraX";
	// sfl4fj
		private final static Logger log=LoggerFactory.getLogger(Product.class);
		
		@Autowired
		EntityManager entityManager;
		
		@Test
		@Transactional
		@Order(1)
		void save() {
			
			// verifica que el Entity no sea nulo
			assertNotNull(entityManager," El entity es nulo ");
			
			// se carga la siguiente accion
			Product producto = entityManager.find(Product.class, pro_id);
		
			// Si la condicion es Nula continuar el procedimiento
			assertNull(producto,"El producto con id "+ pro_id + " No esta registrado");
			
			producto = new Product();
			producto.setProId(pro_id);
			producto.setPrice(4509680);
			producto.setName("Iphone ultra mega x");
			producto.setImage("www.iphon4ever");
			producto.setDetail("Made in chine");
			producto.setEnable("Y");
			
			entityManager.persist(producto);
			
			log.info(producto.getProId());
			log.info(producto.getName());

		}
		
		@Test
		@Transactional
		@Order(2)
		void findId() {
			// verifica que el Entity no sea nulo -> Siga si no es nulo
			assertNotNull(entityManager,"El entityManager es nulo");
			
			Product producto = entityManager.find(Product.class, pro_id);
			
			assertNotNull(producto,"El producto con id (" + pro_id + ") ya  existe");
			log.info(producto.getName());
			
		}
		
		@Test
		@Transactional
		@Order(3)
		void upDate() {
			
			assertNotNull(entityManager,"El entityManager es nulo");
			
			Product producto = entityManager.find(Product.class, pro_id);
			
			assertNotNull(producto,"El producto con id ("+ pro_id+ ") ya existe");
			
			producto.setEnable("N");
			
			entityManager.merge(producto);
		}
		
		@Test
		@Transactional
		@Order(4)
		void delete(){
			
			assertNotNull(entityManager,"El entityManager es nulo");
			
			Product producto= entityManager.find(Product.class,pro_id);
			
			assertNotNull(producto,"El producto con id ("+ pro_id + ") ya existe");
			
			entityManager.remove(producto);
			
		}
}