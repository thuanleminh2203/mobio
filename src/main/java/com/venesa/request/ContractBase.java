package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ContractBase implements Serializable {

    private String profileId;
    private String contractCode;
    private int status;
}
