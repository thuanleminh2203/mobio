package com.venesa.request;

import com.venesa.dto.Customer;
import com.venesa.utils.ConstantsUtil;
import com.venesa.utils.ValidatorUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest implements Validator {
    private String mobileId;
    private String mobile;
    private String email;

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerRequest customer = (CustomerRequest) target;

        ValidatorUtils.checkNullOrEmpty(customer.getMobileId(), errors, "mobileId");

        ValidatorUtils.checkNullOrEmpty(customer.getMobile(), errors, "mobile");
        ValidatorUtils.checkRegex(customer.getMobile(), errors, "mobile", ConstantsUtil.REGEX_NUMBER_PHONE);

        ValidatorUtils.checkNullOrEmpty(customer.getMobile(), errors, "email");
        ValidatorUtils.checkRegex(customer.getEmail(), errors, "email", ConstantsUtil.REGEX_EMAIL);
    }
}
