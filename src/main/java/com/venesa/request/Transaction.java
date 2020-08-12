package com.venesa.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Transaction implements Serializable {
    private MobioProfile profile;
    private String code;
    private String actionType;
    private Float actionTime;
    private Float amount;
    private String currencyCode;
    private Integer status;
    private List<ItemsTransaction> items;
    private List<MobioVouchers> vouchers;
    private MobioStore store;
    private MobioPayment payment;
    private String source;
}
