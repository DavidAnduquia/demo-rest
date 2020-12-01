package co.edu.usbcali.demo.rest;

import javax.validation.Valid;

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

import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.ShoppingProductDTO;
import co.edu.usbcali.demo.mapper.ShoppingProductMapper;
import co.edu.usbcali.demo.service.CartService;
import co.edu.usbcali.demo.service.ShoppingProductService;

@RestController
@RequestMapping("/api/shoppingProduct")
@CrossOrigin(origins = "*")
public class ShoppingProductController {

  @Autowired
  private ShoppingProductService shoppingProductService;
  @Autowired
	private CartService cartService; // agregar producto , eliminar de cart
	@Autowired
	private ShoppingProductMapper shoppingProductMapper;

	@GetMapping(value = "/findById/{shprId}")
	public ResponseEntity<?> findById(@PathVariable("shprId") Integer shprId) throws Exception {

		ShoppingProduct shoppingProduct = (shoppingProductService.findById(shprId).isPresent() == true)
				? shoppingProductService.findById(shprId).get()
				: null;

		return ResponseEntity.ok().body(shoppingProductMapper.shoppingProductToShoppingProductDTO(shoppingProduct));
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() throws Exception {

		return ResponseEntity.ok().body(
				shoppingProductMapper.listShoppingProductToListShoppingProductDTO(shoppingProductService.findAll()));
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody ShoppingProductDTO shoppingProductDTO) throws Exception {

		ShoppingProduct shoppingProduct = shoppingProductMapper.shoppingProductDTOToShoppingProduct(shoppingProductDTO);
		shoppingProduct = shoppingProductService.save(shoppingProduct);

		return ResponseEntity.ok().body(shoppingProductMapper.shoppingProductToShoppingProductDTO(shoppingProduct));
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody ShoppingProductDTO shoppingProductDTO) throws Exception {

		ShoppingProduct shoppingProduct = shoppingProductMapper.shoppingProductDTOToShoppingProduct(shoppingProductDTO);
		shoppingProduct = shoppingProductService.update(shoppingProduct);

		return ResponseEntity.ok().body(shoppingProductMapper.shoppingProductToShoppingProductDTO(shoppingProduct));
	}

	@DeleteMapping(value = "/deleteById/{shprId}")
	public ResponseEntity<?> delete(@PathVariable("shprId") Integer shprId) throws Exception {

		shoppingProductService.deleteById(shprId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(shoppingProductService.count());
	}


  // Mis Url
  @PostMapping("/addProduct/{carId}/{proId}/{quantity}")
	public ResponseEntity<?> addShopinggProduct(@PathVariable("carId") Integer carId,@PathVariable("proId") String proId,@PathVariable("quantity") Integer quantity) throws Exception {

		cartService.addProduct(carId, proId,quantity);
    return ResponseEntity.ok().build();

  }

  @PostMapping("/sumProduct/{carId}/{proId}")
	public ResponseEntity<?> sumShopinggProduct(@PathVariable("carId") Integer carId,@PathVariable("proId") String proId) throws Exception {

		cartService.sumProduct(carId, proId);
    return ResponseEntity.ok().build();

  }

  @DeleteMapping("/deleteProduct/{proId}/{carId}")
	public ResponseEntity<?> deleteShoppingProduct(@PathVariable("proId") String proId,@PathVariable("carId") Integer carId) throws Exception {

		cartService.removeProduct(carId, proId);
    return ResponseEntity.ok().build();

  }

  @DeleteMapping("/restProduct/{proId}/{carId}")
	public ResponseEntity<?> restShoppingProduct(@PathVariable("proId") String proId,@PathVariable("carId") Integer carId) throws Exception {

		cartService.restProduct(carId, proId);
    return ResponseEntity.ok().build();

	}
}
