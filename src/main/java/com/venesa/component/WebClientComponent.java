package com.venesa.component;

import com.venesa.entity.LogEntity;
import com.venesa.service.LogService;
import com.venesa.utils.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venesa.dto.ResponseData;

import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class WebClientComponent {

    @Autowired
    private LogService logService;

    @Autowired
    private WebClient webClient;

    /**
     * @param <T>
     * @param <V>
     * @param type
     * @param body
     * @param method
     * @param url
     * @param tClass
     * @param token
     * @return
     * @throws Exception
     */
    public <T, V> T callInternalService(ParameterizedTypeReference<T> type, V body, HttpMethod method, String url,
                                        Class<T> tClass, String token) throws Exception {
        T dto = null;
        @SuppressWarnings("rawtypes")
        ResponseData responseData = null;
        try {
            responseData = webClient.method(method).uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(body), type).retrieve().onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        return Mono.error(new Exception());
                    }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                        return Mono.error(new Exception());
                    }).bodyToMono(ResponseData.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            dto = objectMapper.convertValue(responseData.getData(), tClass);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return dto;
    }


    /**
     * webclient for call outside service
     *
     * @param type
     * @param body
     * @param method
     * @param url
     * @param tClass
     * @param token
     * @param <T>
     * @param <V>
     * @return
     * @throws Exception
     */
    public <T, V> T callOutterService(ParameterizedTypeReference<T> type, V body, HttpMethod method, String url,
                                      Class<T> tClass, String token) throws Exception {
        LogEntity logEntity = new LogEntity();
        logEntity.setRequestTime(new Date());
        logEntity.setMethod(method.name());
        logEntity.setUrl(url);
        logEntity.setBody(body.toString());
        logEntity.setType(ConstantsUtil.CALL_API_TO_MOBIO);
        T dto = null;
        @SuppressWarnings("rawtypes")
        ResponseData responseData = null;
        try {
            responseData = webClient.method(method).uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(body), type).retrieve().onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                        return Mono.error(new Exception());
                    }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                        return Mono.error(new Exception());
                    }).bodyToMono(ResponseData.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            dto = objectMapper.convertValue(responseData.getData(), tClass);
            logEntity.setResponseBody(dto.toString());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                logService.save(logEntity);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        return dto;
    }
}