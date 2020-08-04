package com.venesa.controller;

import com.venesa.component.WapperResponseData;
import com.venesa.dto.Customer;
import com.venesa.dto.ResponseData;
import com.venesa.entity.Message;
import com.venesa.service.MessageService;
import com.venesa.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private WapperResponseData wapperResponse;

    @PostMapping
    @SuppressWarnings("unchecked")
    public ResponseEntity<?> getMessage(@RequestBody Message message, HttpServletRequest request) {

        ResponseEntity<?> responseEntity;
        try {
            responseEntity = wapperResponse.success(new ResponseData(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, messageService.getUserByUsername(message.getMessageId())));

        } catch (Exception e) {
            responseEntity = wapperResponse.error(new ResponseData(ConstantsUtil.ERROR, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
