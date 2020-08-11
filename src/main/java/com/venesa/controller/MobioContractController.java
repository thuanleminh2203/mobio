package com.venesa.controller;

import com.venesa.common.DTO.MobioResponse;
import com.venesa.common.config.EnvironmentConfig;
import com.venesa.component.WebClientComponent;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.ResponseData;
import com.venesa.request.BookingBase;
import com.venesa.request.ContractBase;
import com.venesa.request.ContractDTO;
import com.venesa.utils.ConstantsUtil;
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
@RequestMapping("/mobio/contract/")
public class MobioContractController {

    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClientComponent;
    private final EnvironmentConfig environmentConfig;

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ContractBase rq, BindingResult result) {
        String url = environmentConfig.getSourceContract(HttpMethod.PUT);

        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
//            MobioResponse response = webClientComponent.callOutterService(new ParameterizedTypeReference<ContractBase>() {
//            }, rq, HttpMethod.POST, url, MobioResponse.class, null);

            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, rq));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContractDTO rq, BindingResult result) {
        String url = environmentConfig.getSourceContract(HttpMethod.POST);

        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            MobioResponse response = webClientComponent.callOutterService(new ParameterizedTypeReference<MobioResponse>() {
            }, rq, HttpMethod.POST, url, MobioResponse.class, null);

            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }
}
