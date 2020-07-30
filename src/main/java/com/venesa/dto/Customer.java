package com.venesa.dto;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.venesa.utils.ConstantsUtil;
import com.venesa.utils.FieldDTOConstant;
import com.venesa.utils.ValidatorUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
		ValidatorUtils.checkLength(customer.getMobileId(), errors, FieldDTOConstant.MAX_MOBILE_ID, "mobileId");

		ValidatorUtils.checkNullOrEmpty(customer.getFullName(), errors, "fullName");
		ValidatorUtils.checkLength(customer.getFullName(), errors, FieldDTOConstant.MAX_FULLNAME, "fullName");

		ValidatorUtils.checkNullOrEmpty(customer.getMobile(), errors, "mobile");
		ValidatorUtils.checkLength(customer.getMobile(), errors, FieldDTOConstant.MAX_MOBILE, "mobile");
		ValidatorUtils.checkRegex(customer.getMobile(), errors, "mobile", ConstantsUtil.REGEX_NUMBER_PHONE);

		ValidatorUtils.checkNullOrEmpty(customer.getIdCardNo(), errors, "idCardNo");
		ValidatorUtils.checkLength(customer.getIdCardNo(), errors, FieldDTOConstant.MAX_MOBILE, "idCardNo");

		ValidatorUtils.checkNullOrEmpty(customer.getGender().toString(), errors, "gender");
		ValidatorUtils.checkLength(customer.getGender().toString(), errors, FieldDTOConstant.MAX_GENDER, "gender");
		ValidatorUtils.checkGender(customer.getGender(), errors, "gender");

	}

}
