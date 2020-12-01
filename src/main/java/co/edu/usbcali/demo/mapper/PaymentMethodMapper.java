package co.edu.usbcali.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.dto.PaymentMethodDTO;

@Mapper
public interface PaymentMethodMapper {

	 PaymentMethodDTO toPaymentMethodDTO(PaymentMethod paymentMethod);

	 PaymentMethod toPaymentMethod(PaymentMethodDTO paymentMethodDTO);

	 List<PaymentMethodDTO> toPaymentMethodsDTO(List<PaymentMethod> paymentMethods);

	 List<PaymentMethod> toPaymentMethods(List<PaymentMethodDTO> paymentMethodsDTO);


}
