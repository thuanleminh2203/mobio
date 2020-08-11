package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MobioProfile implements Serializable {
    private String id;
    private String numberPhone;
    private String email;
    private String taxCode;
    private ThirdPartyInfo thirdPartyInfo;
}
