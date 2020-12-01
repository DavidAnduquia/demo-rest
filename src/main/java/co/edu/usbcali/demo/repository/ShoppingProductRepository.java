package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShoppingProductRepository extends JpaRepository<ShoppingProduct,Integer> {

	// Suma total de la compra
	@Query("SELECT SUM(shpr.total) FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public Long totalShoppingProductByShoppingCart(Integer carId);

	// suma de todas las cantidades de los productos del carrito de compra
	@Query("SELECT SUM(shpr.quantity) FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public Integer quantityShoppingProductByShoppingCart(Integer carId);

	// buscar product y carId en el carrito de compras
	@Query("SELECT shpr FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId and shpr.product.proId=:proId")
	public ShoppingProduct findByShoppingCartAndProduct(Integer carId,String proId);

	// Lista de todos los productos del carrito de compras
	@Query("SELECT shpr FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId);

	// clear carrito de compras
	@Modifying
	@Query("DELETE FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public void deleteProductsByShoppingCart(Integer carId);

	// cantidad de items , productos distintos , no la suma de cantidad de todos los productos
	@Query("SELECT COUNT(shpr) FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public Integer quantityItemsByCart(Integer carId);


}
