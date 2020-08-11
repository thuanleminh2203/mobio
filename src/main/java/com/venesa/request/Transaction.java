package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
