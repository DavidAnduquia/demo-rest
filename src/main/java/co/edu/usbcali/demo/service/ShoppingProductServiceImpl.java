package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.repository.ShoppingProductRepository;

@Service
@Scope("singleton")
public class ShoppingProductServiceImpl implements ShoppingProductService{

	private final static Logger log = LoggerFactory.getLogger(ShoppingProductServiceImpl.class);

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ShoppingProductRepository shoppingProductRepository;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingProduct> findAll() {
		return shoppingProductRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return shoppingProductRepository.count();
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingProduct save(ShoppingProduct entity) throws Exception {
		validate(entity);
		return shoppingProductRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(ShoppingProduct entity) throws Exception {
		if(entity==null) {
			throw new Exception("El shoppingProduct es nulo");
		}

		if(entity.getShprId()==null) {
			throw new Exception("El ShprId es obligatoria");
		}

		if(shoppingProductRepository.existsById(entity.getShprId())==false) {
			throw new Exception("El shoppingProduct con id:"+entity.getShprId()+" no existe. No se puede borrar");
		}

		shoppingProductRepository.deleteById(entity.getShprId());
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
        log.debug("deleting ShoppingProduct instance");

		if(id==null || id<0) {
			throw new Exception("El shoppingCart es obligatoria");
		}
		if(shoppingProductRepository.existsById(id)) {
			delete(shoppingProductRepository.findById(id).get());
		}
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingProduct update(ShoppingProduct entity) throws Exception {
		validate(entity);
		if(shoppingProductRepository.existsById(entity.getShprId())==false) {
			throw new Exception("El ShoppingProduct con id:"+entity.getShprId()+" no existe");
		}
		return shoppingProductRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ShoppingProduct> findById(Integer id) throws Exception {
        log.debug("getting ShoppingProduct instance");
		return shoppingProductRepository.findById(id);
	}

	@Override
	public void validate(ShoppingProduct entity) throws Exception {

		Set<ConstraintViolation<ShoppingProduct>> constraintValidator=validator.validate(entity);

		if(constraintValidator.isEmpty()==false) {
			throw new ConstraintViolationException(constraintValidator);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long totalShoppingProductByShoppingCart(Integer carId) {	
		return shoppingProductRepository.totalShoppingProductByShoppingCart(carId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer quantityShoppingProductByShoppingCart(Integer carId) {
		return shoppingProductRepository.quantityShoppingProductByShoppingCart(carId);
	}

	@Override
	@Transactional(readOnly = true)
	public ShoppingProduct findByShoppingCartAndProduct(Integer carId, String proId) {
		return shoppingProductRepository.findByShoppingCartAndProduct(carId, proId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId){
		return shoppingProductRepository.findShoppingProductByShoppingCart(carId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteProductsByShoppingCart(Integer carId) throws Exception{
		 if (carId == null) {
	        throw new Exception("El ShoppingCart id es nulo");
	     }
		 if(shoppingCartService.findById(carId).isPresent()==false) {
			 throw new Exception("El ShoppingCart con id:"+carId+" No existe");
		 }
		 
		 ShoppingCart shoppingCart=shoppingCartService.findById(carId).get();
		 
		 if(shoppingCart.getPaymentMethod()!=null) {
			 throw new Exception("El ShoppingCart con id:"+carId+" ya se encuentra pagado no se pueden eliminar");
		 }
		 
		 shoppingProductRepository.deleteProductsByShoppingCart(carId);		
	}

}
