package com.venesa.controller;

import com.venesa.component.WrapperResponseData;
import com.venesa.dto.ResponseData;
import com.venesa.repository.LogRepository;
import com.venesa.service.LogService;
import com.venesa.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private WrapperResponseData wrapperResponse;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, logService.getAll()));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, ConstantsUtil.ERR_BUSINESS, null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            logService.deleteById(id);
            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, null));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, ConstantsUtil.ERR_BUSINESS, null),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
