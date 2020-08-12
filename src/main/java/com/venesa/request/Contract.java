package com.venesa.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Contract implements  Serializable{
    private String profileId;
    private String contractCode;
    private int status;
    private String productCode;
    private String contractDate;
    private String branchCode;
}
