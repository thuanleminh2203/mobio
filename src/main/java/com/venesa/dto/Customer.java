package com.venesa.dto;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.venesa.utils.ConstantsUtil;
import com.venesa.utils.ValidatorUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Customer implements Validator{

	private String mobileId;

	private String fullName;

	private String mobile;

	private String idCardNo;

	private Integer gender;

	private Date birthday;

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Customer customer = (Customer) target;

		ValidatorUtils.checkNullOrEmpty(customer.getMobileId(), errors, "mobileId");

		ValidatorUtils.checkNullOrEmpty(customer.getFullName(), errors, "fullName");

		ValidatorUtils.checkNullOrEmpty(customer.getMobile(), errors, "mobile");
		ValidatorUtils.checkRegex(customer.getMobile(), errors, "mobile", ConstantsUtil.REGEX_NUMBER_PHONE);

		ValidatorUtils.checkNullOrEmpty(customer.getIdCardNo(), errors, "idCardNo");

		ValidatorUtils.checkNullOrEmpty(customer.getGender().toString(), errors, "gender");
		ValidatorUtils.checkGender(customer.getGender(), errors, "gender");

	}

}
