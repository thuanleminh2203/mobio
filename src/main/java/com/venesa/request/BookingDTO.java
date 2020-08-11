package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO extends BookingBase implements Serializable {
    private String productCode;
    private String contractCode;
    private String branchCode;
    private int sessionNumber;
    private int sessionTotal;
}
