package com.venesa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venesa.component.WapperResponseData;
import com.venesa.dto.Customer;
import com.venesa.dto.ResponseData;
import com.venesa.service.RabbitMQSender;
import com.venesa.utils.ConstantsUtil;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private WapperResponseData wapperResponse;
	
	@Autowired
	private RabbitMQSender sender;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Customer customer, BindingResult result) {
		customer.validate(customer, result);
		sender.send(customer);
		System.out.println("Message sent to the RabbitMQ Venesa Successfully");
		if (result.hasErrors()) {
			return wapperResponse.error(
					new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
					HttpStatus.BAD_REQUEST);
		}

		return wapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, customer));
	}
	
	
	@GetMapping
	public ResponseEntity<?> get() {
//		customer.validate(customer, result);
//		if (result.hasErrors()) {
//			return wapperResponse.error(
//					new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
//					HttpStatus.BAD_REQUEST);
//		}
		Customer customer = new Customer();
		customer.setFullName("hihihi");
		customer.setGender(1);
		customer.setIdCardNo("aaaaaaa");
		customer.setMobile("098135273");
		return wapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS,customer));
	}

}
