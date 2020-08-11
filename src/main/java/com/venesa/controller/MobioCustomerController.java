package com.venesa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.Customer;
import com.venesa.dto.ResponseData;
import com.venesa.dto.UserDTO;
import com.venesa.publisher.service.RabbitMQSender;
import com.venesa.request.CustomerRequest;
import com.venesa.utils.ConstantsUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/customer")
@AllArgsConstructor
public class MobioCustomerController {
	private final WrapperResponseData wrapperResponse;

	private final RabbitMQSender sender;

	private final ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Customer customer, BindingResult result) {
		customer.validate(customer, result);
		System.out.println("Message sent to the RabbitMQ Venesa Successfully");
		if (result.hasErrors()) {
			return wrapperResponse.error(
					new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
					HttpStatus.BAD_REQUEST);
		}

		return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, customer));
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody CustomerRequest rq, BindingResult result) {
		rq.validate(rq, result);
		Customer customer = objectMapper.convertValue(rq, Customer.class);
		System.out.println("Message sent to the RabbitMQ Venesa Successfully" + customer);
		if (result.hasErrors()) {
			return wrapperResponse.error(
					new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage() , null),
					HttpStatus.BAD_REQUEST);
		}

		return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, customer));
	}
	
	
	@GetMapping
	public ResponseEntity<?> get() {
		sender.sender(new UserDTO("thuanpro123","123456"), "exchange" , "key_common");
		Customer customer = new Customer();
		customer.setFullName("heidi");
		customer.setGender(1);
		customer.setIdCardNo("aaaaaaa");
		customer.setMobile("098135273");
		return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS,customer));
	}

}
