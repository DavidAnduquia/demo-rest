package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.dto.CustomerDTO;

@Mapper
public interface CustomerMapper {

	 CustomerDTO toCustomerDTO(Customer customer);

	 Customer toCustomer(CustomerDTO customerDTO);

	 List<CustomerDTO> toCustomersDTO(List<Customer> customers);

	 List<Customer> toCustomers(List<CustomerDTO> customersDTO);

}