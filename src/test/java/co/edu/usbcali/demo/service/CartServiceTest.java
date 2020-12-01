package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

@SpringBootTest
@Rollback(false)
public class CartServiceTest {


	@Autowired
	CartService cartService;

	@Test
	@Disabled
	void debeCrearShoppingCart() throws Exception {
		//Arrange - variables , datos 
		String email="afeaviour4@nba.com";
		ShoppingCart shoppingCart=null;
		// Act
		shoppingCart=cartService.createCart(email);
		// Assert
		assertNotNull(shoppingCart);
	}

	@Test
	void noDebeCrearUnShoppingCartPorCustomerDisable()throws Exception {
		//Arrange
		String email="abeamondqq@harvard.edu";

		//Act
		assertThrows(Exception.class, ()->cartService.createCart(email));

	}

	@Test
	void noDebeCrearUnShoppingCartPorCustomerNull()throws Exception {
		//Arrange
		String email=null;
		//Act
		assertThrows(Exception.class, ()->cartService.createCart(email));
	}

	@Test
	void noDebeCrearUnShoppingCartPorCustomerNoExiste()throws Exception {
		//Arrange
		String email="sadasd@vvv.com";

		//Act
		assertThrows(Exception.class, ()->cartService.createCart(email));

	}

	@Test
	void debeAgregarProductShoppingCart() throws Exception{
		Integer carId=38;
		String proId="APPL90";
		Integer quantity=3;
		ShoppingProduct shoppingProduct=null;
		//Act
		shoppingProduct=cartService.addProduct(carId, proId, quantity);
		//Assert
		assertNotNull(shoppingProduct,"El shoppingProduct es nulo");
	}

	@Test
	void debeSumarUnQuantityProduct() throws Exception{
		//Arrange
		Integer carId=1;
		String proId="APPL60";
		ShoppingProduct shoppingProduct=null;

		//Act
		shoppingProduct=cartService.sumProduct(carId, proId);
		assertNotNull(shoppingProduct,"El shoppingProduct es nulo");
	}
	//Personalizado
	@Test
	void debeRestarUnQuantityProduct() throws Exception{
		//Arrange
		Integer carId=38;
		String proId="APPL60";

		//Act
		cartService.restProduct(carId, proId);

	}


	@Test
	void deleteProduct() throws Exception{
		//Arrange
		Integer carId=38;
		String proId="APPL60";

		//Act
		cartService.removeProduct(carId, proId);

	}



	@Test
	void debeLimpiarElCart() throws Exception{
		//Arrange
		Integer carId=38;

		//Act
		cartService.clearCart(carId);
	}


}
