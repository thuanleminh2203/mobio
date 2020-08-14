package com.venesa.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venesa.common.DTO.mobio.MobioResponse;
import com.venesa.common.Utils.ConstantsUtil;
import com.venesa.common.config.EnvironmentConfig;
import com.venesa.dto.ResponseData;
import com.venesa.entity.LogEntity;
import com.venesa.exception.ExceptionCustom;
import com.venesa.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
//@AllArgsConstructor
public class WebClientComponent {

    @Autowired
    private LogService logService;
    @Autowired
    private WebClient webClient;
    @Autowired
    private EnvironmentConfig environmentConfig;

    private String token = "";
    private String xMerchantId = "";

    @PostConstruct
    public void initToken() {
        this.token = environmentConfig.getMobioToken();
        this.xMerchantId = environmentConfig.getXMerchantId();
    }


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
        ResponseData responseData = webClient.method(method).uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .body(Mono.justOrEmpty(body), type)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(ResponseData.class).flatMap(response ->
                                Mono.error(new Exception(response.getErrorMessage()))
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(ResponseData.class).flatMap(response ->
                                Mono.error(new Exception(response.getErrorMessage()))
                        ))
                .bodyToMono(ResponseData.class).block();
        ObjectMapper objectMapper = new ObjectMapper();

        dto = objectMapper.convertValue(responseData, tClass);
        return dto;
    }


    /**
     * webclient for call outside service
     *
     * @param type   body pass
     * @param body   data pass
     * @param method
     * @param url
     * @param tClass return type
     * @param <T>
     * @param <V>
     * @return
     * @throws Exception
     */
    public <T, V> T callOuterService(ParameterizedTypeReference<?> type, V body, HttpMethod method, String url,
                                     Class<T> tClass) throws Exception {
        LogEntity logEntity = new LogEntity();
        logEntity.setRequestTime(new Date());
        logEntity.setMethod(method.name());
        logEntity.setUrl(url);
        logEntity.setBody(body.toString());
        logEntity.setType(ConstantsUtil.CALL_API_TO_MOBIO);
        T dto = null;
        try {
            MobioResponse<?> responseData = webClient.method(method).uri(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, this.token)
                    .header("X-Merchant-Id", this.xMerchantId)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.justOrEmpty(body), type)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                            clientResponse.bodyToMono(MobioResponse.class).flatMap(response ->{
                                        System.out.println("=====err 4xxx======");
                                        return   Mono.error(new Exception(response.getMessage()));
                                    }

                            )
                    )
                    .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                            clientResponse.bodyToMono(MobioResponse.class).flatMap(response ->
                                    Mono.error(new Exception(response.getMessage()))
                            ))
                    .bodyToMono(MobioResponse.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("=====response======" + responseData.getData());
            dto = objectMapper.convertValue(responseData.getData(), tClass);
            logEntity.setResponseBody(dto.toString());
            return dto;
        }catch (Exception e){
            System.out.println("minhon========="+e.getCause().getMessage());
            throw new Exception(e.getCause().getMessage());
        }
//        MobioResponse<?> responseData = webClient.method(method).uri(url)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .header(HttpHeaders.AUTHORIZATION, this.token)
//                .header("X-Merchant-Id", this.xMerchantId)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.justOrEmpty(body), type)
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
//                        clientResponse.bodyToMono(MobioResponse.class).flatMap(response ->{
//                            System.out.println("=====err 4xxx======");
//                                return   Mono.error(new Exception(response.getMessage()));
//                                }
//
//                        )
//                )
//                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
//                        clientResponse.bodyToMono(MobioResponse.class).flatMap(response ->
//                                Mono.error(new Exception(response.getMessage()))
//                        ))
//                .bodyToMono(MobioResponse.class).block();
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println("=====response======" + responseData.getData());
//        dto = objectMapper.convertValue(responseData.getData(), tClass);
//        logEntity.setResponseBody(dto.toString());
//        return dto;
    }

}