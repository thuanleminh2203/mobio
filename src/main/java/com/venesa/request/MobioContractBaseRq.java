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
public class MobioContractBaseRq implements Serializable {
    private List<ContractBase> services;
}
