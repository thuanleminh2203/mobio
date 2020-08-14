package com.venesa.controller;

import com.venesa.common.DTO.ContractUpdateRes;
import com.venesa.common.DTO.ListContractCreateRes;
import com.venesa.common.config.EnvironmentConfig;
import com.venesa.component.WebClientComponent;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.ResponseData;
import com.venesa.request.Contract;
import com.venesa.request.ContractBase;
import com.venesa.request.ContractDTO;
import com.venesa.request.ListContractRq;
import com.venesa.utils.ConstantsUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/mobio/contract1/")
public class ContractController {
    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClientComponent;
    private final EnvironmentConfig environmentConfig;

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Contract rq, BindingResult result) {
        String url = environmentConfig.getSourceContract(HttpMethod.PUT);
        rq.validate(rq, result);
        ModelMapper modelMapper = new ModelMapper();
        ContractDTO contractDTO = modelMapper.map(rq, ContractDTO.class);
        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            ContractUpdateRes response = webClientComponent.callOuterService(new ParameterizedTypeReference<ContractDTO>() {
            }, contractDTO, HttpMethod.PUT, url, ContractUpdateRes.class);

            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ListContractRq rq, BindingResult result) {
        ModelMapper modelMapper = new ModelMapper();
        String url = environmentConfig.getSourceContract(HttpMethod.POST);
//        List<ContractDTO> dtos = rq.getContract().stream()
//                .map(user -> modelMapper.map(user, ContractDTO.class))
//                .collect(Collectors.toList());
        //List<ContractDTO> userDtoList = mapList(rq, ContractDTO.class);

        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            ListContractCreateRes response = webClientComponent.callOuterService(new ParameterizedTypeReference<ListContractRq>() {
            }, rq, HttpMethod.POST, url, ListContractCreateRes.class);


            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getCause().getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
