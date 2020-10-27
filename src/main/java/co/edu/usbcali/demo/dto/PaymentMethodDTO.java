package co.edu.usbcali.demo.dto;

 
public class PaymentMethodDTO {
	private Integer payId;
	private String enable;
	private String name;


	public PaymentMethodDTO() {
	}
	
	public PaymentMethodDTO(Integer payId, String enable, String name) {
 		this.payId = payId;
		this.enable = enable;
		this.name = name;
	}

	public Integer getPayId() {
		return payId;
	}


	public void setPayId(Integer payId) {
		this.payId = payId;
	}


	public String getEnable() {
		return enable;
	}


	public void setEnable(String enable) {
		this.enable = enable;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


}
