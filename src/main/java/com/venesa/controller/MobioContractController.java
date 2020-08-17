package com.venesa.controller;

import com.venesa.common.DTO.ResponseData;
import com.venesa.common.DTO.mobio.request.ContractBase;
import com.venesa.common.DTO.mobio.request.ListContractRq;
import com.venesa.common.DTO.mobio.response.ContractUpdateRes;
import com.venesa.common.DTO.mobio.response.ListContractCreateRes;
import com.venesa.common.Utils.ConstantsUtil;
import com.venesa.common.config.EnvironmentConfig;
import com.venesa.component.WebClientComponent;
import com.venesa.component.WrapperResponseData;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(ConstantsUtil.URL_GATEWAY+"contract")
public class MobioContractController {

    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClientComponent;
    private final EnvironmentConfig environmentConfig;

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ContractBase rq, BindingResult result) {
        String url = environmentConfig.getSourceContract();
        rq.validate(rq, result);
        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            ContractUpdateRes response = webClientComponent.callOuterService(new ParameterizedTypeReference<ContractBase>() {
            }, rq, HttpMethod.PUT, url, ContractUpdateRes.class);

            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ListContractRq rq, BindingResult result) {
        String url = environmentConfig.getSourceContract();
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
