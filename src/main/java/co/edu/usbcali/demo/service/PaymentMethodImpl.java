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

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.repository.PaymentMethodRepository;



@Service
@Scope("singleton")
public class PaymentMethodImpl implements PaymentMethodService{
	
	@Autowired
	PaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<PaymentMethod> findAll() {
		return paymentMethodRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<PaymentMethod> findById(Integer id) throws Exception {
 		return paymentMethodRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return paymentMethodRepository.count();
	}
	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public PaymentMethod save(PaymentMethod entity) throws Exception {
		validate(entity);
		
	
		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public PaymentMethod update(PaymentMethod entity) throws Exception {
		validate(entity);
		
		if(paymentMethodRepository.existsById(entity.getPayId())==false) {
			throw new Exception("El paymentMethod  con id:"+entity.getPayId()+" no existe");
		}
		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(PaymentMethod entity) throws Exception {
	 
		if(entity==null) {
			throw new Exception("El paymentMethod es nulo");
		}
		
		if(entity.getPayId()==null || entity.getPayId()<0) {
			throw new Exception("El payId es obligatorio");
		}
		
		if(paymentMethodRepository.existsById(entity.getPayId())==false) {
			throw new Exception("El paymentMethod con id:"+entity.getPayId()+" no existe. No se puede borrar");
		}
		
		paymentMethodRepository.findById(entity.getPayId()).ifPresent(paymentMethod->{
			if(paymentMethod.getShoppingCarts()!=null && paymentMethod.getShoppingCarts().isEmpty()==false) {
				throw new RuntimeException("El paymentMethod con id:"+entity.getPayId()+" tiene ShoppingCart que no se puede borrar");
			}
		});
		
		paymentMethodRepository.deleteById(entity.getPayId());
	}

 	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		if(id == null){
			throw new Exception("El payId es obligatorio");
		}
		
		if(paymentMethodRepository.existsById(id)) {
			delete(paymentMethodRepository.findById(id).get());
		}else {
			throw new Exception("El payId con id :" + id + " no existe");

		}

	}

	@Override
	public void validate(PaymentMethod entity) throws Exception {
		if(entity==null) {
			throw new Exception("El PaymentMethod es nulo");
		}
		
		Set<ConstraintViolation<PaymentMethod>> constraintValidator=validator.validate(entity);
		
		
		if(constraintValidator.isEmpty()==false) {
			throw new ConstraintViolationException(constraintValidator);
		}
	}


	

 
}
