package com.venesa.service;

import com.venesa.common.DTO.MessageDTO;
import com.venesa.common.DTO.ResponseData;
import com.venesa.common.DTO.mobio.request.BookingBase;
import com.venesa.common.DTO.mobio.request.BookingDTO;
import com.venesa.common.DTO.mobio.request.ContractBase;
import com.venesa.common.DTO.mobio.request.ContractDTO;
import com.venesa.common.DTO.mobio.response.ContractUpdateRes;
import com.venesa.common.DTO.mobio.response.ListContractCreateRes;
import com.venesa.common.Utils.ConstantsUtil;
import com.venesa.common.config.EnvironmentConfig;
import com.venesa.component.WebClientComponent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class RabbitMQListener {

    private final EnvironmentConfig env;

    private final WebClientComponent webClientComponent;

    //	@Override
    @RabbitListener(queues = "queue_common")
    public <T> void onMessage(MessageDTO<T> message) throws Exception {
        String messType = message.getType();
        HttpMethod medthod = message.getMethod();
        ParameterizedTypeReference<?> reference = null;
        Class<?> tclass = null;
        System.out.println("====receive Mess======" + (new Date().getTime())/1000);
//        try {
            switch (messType) {
                case ConstantsUtil.CONTRACT: {
                    String url = env.getSourceContract();
                    if (medthod.matches(String.valueOf(HttpMethod.POST))) {
                        reference = new ParameterizedTypeReference<ContractDTO>() {
                        };
                        tclass = ListContractCreateRes.class;
                    }
                    if (medthod.matches(String.valueOf(HttpMethod.PUT))) {
                        reference = new ParameterizedTypeReference<ContractBase>() {
                        };
                        tclass = ContractUpdateRes.class;
                    }
                    webClientComponent.callOuterService(reference, message.getMessage(), message.getMethod(), url, tclass);
                    break;
                }
                case ConstantsUtil.BOOKING: {
                    String url = env.getSourceBooking();
                    if (medthod.matches(String.valueOf(HttpMethod.POST))) {
                        reference = new ParameterizedTypeReference<BookingDTO>() {
                        };
                        tclass = BookingDTO.class;
                    }
                    if (medthod.matches(String.valueOf(HttpMethod.PUT))) {
                        reference = new ParameterizedTypeReference<BookingBase>() {
                        };
                        tclass = BookingBase.class;
                    }
                    webClientComponent.callOuterService(reference, message.getMessage(), message.getMethod(), url, ResponseData.class);
                    break;
                }
            }
//        } catch (Exception e) {
//            System.out.println("======errr=====" + e.getCause().getMessage());
//        }

    }
}
