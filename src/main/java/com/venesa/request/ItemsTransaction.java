package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemsTransaction implements Serializable {
    private String id;
    private String name;
    private List<String> tags;
    private Integer quantity;
    private Float price;
    private Float totalAmount;
    private String code;
    private Supplier supplier;

}
