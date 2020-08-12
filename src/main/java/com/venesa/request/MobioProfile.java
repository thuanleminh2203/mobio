package com.venesa.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MobioProfile implements Serializable {
    private String id;
    private String numberPhone;
    private String email;
    private String taxCode;
    private ThirdPartyInfo thirdPartyInfo;
}
