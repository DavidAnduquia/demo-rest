package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;


@Service
@Scope("singleton")
public class ShoppingCartImpl implements ShoppingCartService {

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<ShoppingCart> findAll() {
		return shoppingCartRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return shoppingCartRepository.count();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<ShoppingCart> findById(Integer id) throws Exception {
 		return shoppingCartRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingCart save(ShoppingCart entity) throws Exception {
		validate(entity);
	 
 		return shoppingCartRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingCart update(ShoppingCart entity) throws Exception {
		validate(entity);
		
		
		if(shoppingCartRepository.existsById(entity.getCarId())==false) {
			throw new Exception("El customer con id:"+entity.getCarId()+" no existe");
		}
		
		return shoppingCartRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(ShoppingCart entity) throws Exception {
		if(entity==null) {
			throw new Exception("El shoppingCart es nulo");
		}
	
		if(entity.getCarId()==null || entity.getCarId()<0) {
			throw new Exception("El Email es obligatoria");
		}
		
		if(shoppingCartRepository.existsById(entity.getCarId())==false) {
			throw new Exception("El shoppingCart con id:"+entity.getCarId()+" no existe. No se puede borrar");
		}
		
		shoppingCartRepository.findById(entity.getCarId()).ifPresent(shoppingCart->{
			if(shoppingCart.getShoppingProducts()!=null && shoppingCart.getShoppingProducts().isEmpty()==false) {
				throw new RuntimeException("El shoppingCart con id:"+entity.getCarId()+" tiene ShoppingCarts no se puede borrar");
			}
		});
		
		shoppingCartRepository.deleteById(entity.getCarId());
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		if(id==null || id<0) {
			throw new Exception("El shoppingCart es obligatoria");
		}
		
		if(shoppingCartRepository.existsById(id)) {
			delete(shoppingCartRepository.findById(id).get());
		}
	}

	

	@Override
	public void validate(ShoppingCart entity) throws Exception {
		if(entity==null) {
			throw new Exception("El customer es nulo");
		}
		
		if(entity.getItems()==null || entity.getItems()<0) {
			throw new Exception("El item es obligatoria");
		}
		
		if(entity.getTotal()==null || entity.getTotal()<0) {
			throw new Exception("El total es obligatoria");
		}
		
		if(entity.getEnable()==null || entity.getEnable().isBlank()==true) {
			throw new Exception("El Enable es obligatoria");
		}
		
	}



}
