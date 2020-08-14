package com.venesa.request;

import com.venesa.request.crm.dto.ContractDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListContractRq implements Serializable {
    private List<ContractDTO> contract;
}
