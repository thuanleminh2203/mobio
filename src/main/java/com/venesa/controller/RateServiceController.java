package com.venesa.controller;

import com.venesa.common.DTO.ResponseData;
import com.venesa.common.DTO.crm.request.CRMBookingBase;
import com.venesa.common.DTO.crm.request.CRMSurveyDTO;
import com.venesa.common.DTO.crm.response.CRMBookingUpdateRes;
import com.venesa.common.DTO.mobio.request.BookingBase;
import com.venesa.common.Utils.ConstantsUtil;
import com.venesa.common.config.EnvironmentConfig;
import com.venesa.component.WebClientComponent;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.Customer;
import com.venesa.dto.MobioRating;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(ConstantsUtil.URL_GATEWAY)
public class RateServiceController {
    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClientComponent;
    private final EnvironmentConfig environmentConfig;

    @PostMapping("createSurvey")
    public ResponseEntity<?> update(@RequestBody CRMSurveyDTO rq, BindingResult result) {
        String url = environmentConfig.getSourceCRMCreateSurvey();
        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            CRMSurveyDTO response = webClientComponent.callInternalService(new ParameterizedTypeReference<BookingBase>() {
            }, rq, HttpMethod.POST, url, CRMSurveyDTO.class);
            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
