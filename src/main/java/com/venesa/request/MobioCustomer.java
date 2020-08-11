package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MobioCustomer implements Serializable {
    private String phoneNumber;
    private String email;
    private String fullName;
    private String profileId;
    private String address;
    private String cardId;
    private String code;
    private Integer point;
    private String birthday;
    private Integer gender;
}
