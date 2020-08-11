package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractDTO extends ContractBase implements Serializable {
    private String productCode;
    private String contractDate;
    private String branchCode;
}
