package com.venesa.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.)
public class ContractBase implements Serializable, Validator {
    @JsonProperty("profile_id")
    private String profileId;
    @JsonProperty("contract_code")
    private String contractCode;
    private int status;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ContractBase.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
