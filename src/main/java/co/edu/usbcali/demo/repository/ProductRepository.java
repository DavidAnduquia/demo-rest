package co.edu.usbcali.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.Product;


public interface ProductRepository extends JpaRepository<Product, String>{
	@Query("SELECT p FROM Product p WHERE p.price = (select max(price) FROM Product)")
    List<Product>  findProductMoreExpensive();
	
	List<Product>  findByEnable(String Enable);
	
	List<Product>  findAllByOrderByPriceDesc();
	
	List<Product> findByNameIgnoreCase(String name);

 }
