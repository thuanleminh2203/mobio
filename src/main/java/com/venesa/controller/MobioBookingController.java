package com.venesa.controller;
//

import com.venesa.common.DTO.ResponseData;
import com.venesa.common.DTO.mobio.request.BookingBase;
import com.venesa.common.DTO.mobio.request.BookingDTO;
import com.venesa.common.DTO.mobio.response.MobioResponse;
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
//@RequestMapping(ConstantsUtil.URL_GATEWAY+"booking")
@RequestMapping("/mobio")
public class MobioBookingController {
    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClientComponent;
    private final EnvironmentConfig environmentConfig;

    @PutMapping("/updateAppointment")
    public ResponseEntity<?> update(@RequestBody BookingBase rq, BindingResult result) {
        String url = environmentConfig.getSourceUpdateBooking();
        System.out.println("ID = " + rq.getBookingCode());
        System.out.println("APP_TIME = " + rq.getAppointmentTime());
        System.out.println("CA HEN = " + rq.getWorkShiftId());
        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            BookingBase response = webClientComponent.callOuterService(new ParameterizedTypeReference<BookingBase>() {
            }, rq, HttpMethod.PUT, url, BookingBase.class);

            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingDTO rq, BindingResult result) {
        String url = environmentConfig.getSourceBooking();

        if (result.hasErrors()) {
            return wrapperResponse.error(
                    new ResponseData<>(ConstantsUtil.ERROR, result.getFieldError().getDefaultMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            MobioResponse response = webClientComponent.callOuterService(new ParameterizedTypeReference<MobioResponse>() {
            }, rq, HttpMethod.POST, url, MobioResponse.class);

            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, response));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }


    }
}
