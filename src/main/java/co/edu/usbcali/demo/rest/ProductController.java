package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.dto.ProductDTO;
import co.edu.usbcali.demo.mapper.ProductMapper;
import co.edu.usbcali.demo.repository.ProductRepository;
import co.edu.usbcali.demo.service.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {

	//private final static Logger  log = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	ProductRepository productRepository;
	@Autowired 
	ProductMapper productMapper;
	@Autowired
	ProductService productService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws Exception{

			Product product=productMapper.toProduct(productDTO);
			product=productService.save(product);
			productDTO=productMapper.toProductDTO(product);
			return ResponseEntity.ok().body(productDTO);

	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody ProductDTO productDTO) throws Exception {

			Product product=productMapper.toProduct(productDTO);
			product=productService.update(product);
			productDTO=productMapper.toProductDTO(product);
			return ResponseEntity.ok().body(productDTO);

	}

	@DeleteMapping("/delete/{proId}")
	public ResponseEntity<?> delete(@PathVariable("proId") String proId) throws Exception {

			productService.deleteById(proId);
			return ResponseEntity.ok().build();

	}

	@GetMapping("/findById/{proId}")
	public ResponseEntity<?> findById(@PathVariable("proId") String proId) throws Exception {

			Optional<Product> productOptional = productService.findById(proId);
			if(productOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Product Not Found");
			}

			Product product  = productOptional.get();
			ProductDTO productDTO = productMapper.toProductDTO(product);
			return ResponseEntity.ok().body(productDTO);

		}


		@GetMapping("/findAll")
		public ResponseEntity<?> findAll() throws Exception{

				List<Product> product = productRepository.findAll();
				List<ProductDTO> productsDTO=productMapper.toProductsDTO(product);
				return ResponseEntity.ok().body(productsDTO);

		}

}
