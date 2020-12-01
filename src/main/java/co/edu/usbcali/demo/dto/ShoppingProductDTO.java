package co.edu.usbcali.demo.dto;

import javax.validation.constraints.NotNull;

public class ShoppingProductDTO {
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer shprId;
    @NotNull
    private Long total;
    @NotNull
    private String productId;
    @NotNull
    private Integer shoppingCartId;

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getShprId() {
		return shprId;
	}
	public void setShprId(Integer shprId) {
		this.shprId = shprId;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getShoppingCartId() {
		return shoppingCartId;
	}
	public void setShoppingCartId(Integer shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}
}
