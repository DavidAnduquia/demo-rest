package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.dto.ProductDTO;
 
@Mapper
public interface ProductMapper {
	

	 ProductDTO toProductDTO(Product Product);
		
	 Product toProduct(ProductDTO productDTO);
	
	 List<ProductDTO> toProductsDTO(List<Product> products);
	
	 List<Product> toProducts(List<ProductDTO> productsDTO);

}
