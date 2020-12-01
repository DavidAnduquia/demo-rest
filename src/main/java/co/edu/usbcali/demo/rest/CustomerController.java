package co.edu.usbcali.demo.rest;

 
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.mapper.CustomerMapper;
import co.edu.usbcali.demo.repository.CustomerRepository;
import co.edu.usbcali.demo.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {

	private final static Logger  log = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerMapper customerMapper;

	@Autowired
	CustomerService customerService;

	@RequestMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {

			Customer customer=customerMapper.toCustomer(customerDTO);
			customer=customerService.save(customer);
			customerDTO=customerMapper.toCustomerDTO(customer);
			return ResponseEntity.ok().body(customerDTO);

	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {

			Customer customer=customerMapper.toCustomer(customerDTO);
			customer=customerService.update(customer);
			customerDTO=customerMapper.toCustomerDTO(customer);
			return ResponseEntity.ok().body(customerDTO);

	}

	@GetMapping("/findById/{email}")
	public ResponseEntity<?> findById(@PathVariable("email") String email) throws Exception {

			Optional<Customer> customerOptional = customerService.findById(email);
			if(customerOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Customer Not Found");
			}

			Customer customer  = customerOptional.get();

			CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);

			return ResponseEntity.ok().body(customerDTO);

	}

	@DeleteMapping("/delete/{email}")
	public ResponseEntity<?> delete(@PathVariable("email") String email) throws Exception {

			customerService.deleteById(email);
		/*
			Optional<Customer> customerOptional = customerRepository.findById(email);
			if(customerOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Customer Not Found");
			}

			Customer customer  = customerOptional.get();

			CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);
			*/

			return ResponseEntity.ok().build();

	}


	//Lo mismo para product

	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() throws Exception {

			List<Customer> customers = customerService.findAll();
			List<CustomerDTO> customersDTOs=customerMapper.toCustomersDTO(customers);

			return ResponseEntity.ok().body(customersDTOs);

	}


	@GetMapping("/findById-viejo/{email}")
	public CustomerDTO findById_viejo(@PathVariable("email") String email) {
		try {
			Optional<Customer> customerOptional = customerRepository.findById(email);
			if(customerOptional.isPresent()==false) {
				return null;
			}

			Customer customer  = customerOptional.get();

			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setAddress(customer.getAddress());
			customerDTO.setEmail(customer.getEmail());
			customerDTO.setEnable(customer.getEnable());
			customerDTO.setName(customer.getName());
			customerDTO.setPhone(customer.getPhone());
			customerDTO.setToken(customer.getToken());

			return customerDTO;


		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}

}
