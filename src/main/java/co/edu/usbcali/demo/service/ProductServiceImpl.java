package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.repository.ProductRepository;

@Service
@Scope("singleton")
public class ProductServiceImpl  implements ProductService{

	@Autowired
	ProductRepository productRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(String id) throws Exception {
		return productRepository.findById(id);
	}


	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return productRepository.count();
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Product save(Product entity) throws Exception {
		validate(entity);

		//Si existe lanza el error
		if(productRepository.existsById(entity.getProId())){
			throw new Exception("El product con id:"+entity.getProId()+" ya existe");
		}

		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Product update(Product entity) throws Exception {
		validate(entity);

		//Si no existe lanza el error
		if(productRepository.existsById(entity.getProId())==false){
			throw new Exception("El product con id:"+entity.getProId()+" no existe");
		}
		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Product entity) throws Exception {
		if(entity==null) {
			throw new Exception("El product es nulo");
		}

		if(entity.getProId()==null || entity.getProId().isBlank()==true) {
			throw new Exception("El Email es obligatoria");
		}

		//Si no existe lanza el error
		if(productRepository.existsById(entity.getProId())==false){
			throw new Exception("El product con id:"+entity.getProId()+" no existe. No se puede borrar");
		}

		//Valido las referecia con la tabla de Shoppincart
		productRepository.findById(entity.getProId()).ifPresent(product->{
			if(product.getShoppingProducts()!=null && product.getShoppingProducts().isEmpty()==false) {
				throw new RuntimeException("El product con id:"+entity.getProId()+" tiene ShoppingProduct no se puede borrar");
			}
		});

		productRepository.deleteById(entity.getProId());
	}



	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if(id==null || id.isBlank()==true) {
			throw new Exception("El proID es obligatoria");
		}

		if(productRepository.existsById(id)) {
			delete(productRepository.findById(id).get());
		}else {
			throw new Exception("El product con id :" + id + " no existe");

		}
	}


	@Override
	public void validate(Product entity) throws Exception {
		if(entity==null) {
			throw new Exception("El product es nulo");
		}

		Set<ConstraintViolation<Product>> constraintValidator=validator.validate(entity);


		if(constraintValidator.isEmpty()==false) {
			throw new ConstraintViolationException(constraintValidator);
		}
		/*
		if(entity.getName()==null || entity.getName().isBlank()==true) {
			throw new Exception("El Name es obligatoria");
		}

		if(entity.getPrice()==null || entity.getPrice()<0) {
			throw new Exception("El Price es obligatoria");
		}

		if(entity.getDetail()==null || entity.getDetail().isBlank()==true) {
			throw new Exception("El Detail es obligatoria");
		}


		if(entity.getImage()==null || entity.getImage().isBlank()==true) {
			throw new Exception("El Image es obligatoria");
		}

		if(entity.getEnable()==null || entity.getEnable().isBlank()==true) {
			throw new Exception("El Enable es obligatoria");
		}

		*/
	}



}
