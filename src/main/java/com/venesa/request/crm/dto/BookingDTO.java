package com.venesa.request.crm.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.venesa.request.crm.base.BookingBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookingDTO extends BookingBase implements Serializable {
    private String productCode;
    private String contractCode;
    private String branchCode;
    private int sessionNumber;
    private int sessionTotal;
}
