package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShoppingProductService extends GenericService<ShoppingProduct, Integer> {

	public Long totalShoppingProductByShoppingCart(Integer carId);
	public Integer quantityShoppingProductByShoppingCart(Integer carId);
	public ShoppingProduct findByShoppingCartAndProduct(Integer carId,String proId);
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId);
	public void deleteProductsByShoppingCart(Integer carId) throws Exception;

}
