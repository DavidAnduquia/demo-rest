package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.ShoppingProductDTO;

@Mapper
public interface ShoppingProductMapper {

  @Mapping(source = "product.proId", target = "productId")
  @Mapping(source = "shoppingCart.carId", target = "shoppingCartId")
  public ShoppingProductDTO shoppingProductToShoppingProductDTO(ShoppingProduct shoppingProduct);

  @Mapping(source = "productId", target = "product.proId")
  @Mapping(source = "shoppingCartId", target = "shoppingCart.carId")
  public ShoppingProduct shoppingProductDTOToShoppingProduct(ShoppingProductDTO shoppingProductDTO);

  public List<ShoppingProductDTO> listShoppingProductToListShoppingProductDTO(List<ShoppingProduct> shoppingProducts);

  public List<ShoppingProduct> listShoppingProductDTOToListShoppingProduct(List<ShoppingProductDTO> shoppingProductDTOs);

}
