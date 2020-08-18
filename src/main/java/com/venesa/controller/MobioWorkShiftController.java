package com.venesa.controller;

import com.venesa.common.DTO.ResponseData;
import com.venesa.common.DTO.crm.request.CRMBookingBase;
import com.venesa.common.DTO.crm.request.CRMWorkShiftDTO;
import com.venesa.common.DTO.crm.response.CRMBookingUpdateRes;
import com.venesa.common.DTO.crm.response.CRMWorkShiftRes;
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

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(ConstantsUtil.URL_GATEWAY)
public class MobioWorkShiftController {
    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClientComponent;
    private final EnvironmentConfig environmentConfig;

    @GetMapping("getListWorkShiftId")
    public ResponseEntity<?> getListWorkShiftId(@RequestBody CRMWorkShiftDTO rq, BindingResult result) {
        String url = environmentConfig.getSourceCRMGetWorkShiftId();
        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            List<Integer> response = webClientComponent.callInternalService(new ParameterizedTypeReference<CRMWorkShiftDTO>() {
            }, rq, HttpMethod.GET, url, List.class);
            CRMWorkShiftRes workShiftRes = new CRMWorkShiftRes(response);
            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, workShiftRes));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
