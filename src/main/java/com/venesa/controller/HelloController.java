package com.venesa.controller;

import com.venesa.common.Utils.ConstantsUtil;
import com.venesa.component.WebClientComponent;
import com.venesa.component.WrapperResponseData;
import com.venesa.dto.RateServiceDTO;
import com.venesa.dto.ResponseData;
import com.venesa.publisher.service.RabbitMQSender;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/hello")
@AllArgsConstructor
public class HelloController {
    private final WrapperResponseData wrapperResponse;
    private final WebClientComponent webClient;

    RabbitMQSender rabbitMQSender ;
//    @Autowired
//    public HelloController(WrapperResponseData wrapperResponse, WebClientComponent webClient) {
//        this.wrapperResponse = wrapperResponse;
//        this.webClient = webClient;
//    }

    @PostMapping
    public ResponseEntity<?> hello(Authentication authentication, @RequestBody RateServiceDTO rq,
                                   HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        try {
            String token1 = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXYtaXRhZCIsImp0aSI6IjkwIiwiaWF0IjoxNTk2NjI1Nj" +
                    "M1LCJleHAiOjE1OTc0ODk2MzUsInJvbGVzIjpbIlJPTEVfQURNSU5fU1lTVEVNIl0sInBlcm1pc3Npb25zIjpbIlBfQk9PS19BUFBfU0VBU" +
                    "kNIIiwiUF9CT09LX0FQUF9CT09LSU5HIiwiUF9BUFBfTElTVF9TRUFSQ0giLCJQX0FQUF9MSVNUX1BSSU5UIiwiUF9BUFBfTElTVF9FWFBPU" +
                    "lRfRVhDRUwiLCJQX0FQUF9DSEVDS09VVCIsIlBfQVBQX1NFQVJDSCIsIlBfQVBQX0xJU1RfU0VBUkNIX0NIRUNLSU5FRCIsIlBfQVBQX0NVU1R" +
                    "PTUVSX0lORk9fRURJVCIsIlBfQVBQX0NIRUNLSU5fQ0hFQ0tPVVQiLCJQX0NTX0NPTkZJR19TRUFSQ0giLCJQX0NTX0NPTkZJR19BRERfTkVXI" +
                    "iwiUF9QSUNLX0NVU1RPTUVSIiwiUF9TRUFSQ0giLCJQX1ZJRVdfUElDS19DVVNUT01FUl9ERVRBSUwiLCJQX0NIRUNLT1VUIiwiUF9ET0NUT1JfU" +
                    "ElDS19DVVNUT01FUiIsIlBfTEVBRF9TRUFSQ0giLCJQX0xFQURfQUREX05FVyIsIlBfTEVBRF9ISVNUT1JZX1RBS0VfQ0FSRV9WSUVXIiwiUF9M" +
                    "RUFEX0hJU1RPUllfVEFLRV9DQVJFX0FERF9ORVciLCJQX0xFQURfQk9PS19BUFAiLCJQX0RDX1NFQVJDSCIsIlBfU0FMRV9OT19BUFBfU0VBUkNI" +
                    "IiwiUF9TQUxFX05PX0FQUF9DUkVBVEVfQklMTCIsIlBfU0FMRV9DVVNfTElTVF9DSEVDS09VVCIsIlBfQklMTF9TRUFSQ0giLCJQX0JJTExfUFJJT" +
                    "lQiLCJQX0JJTExfTElTVF9BUFBST1ZFX0RFSU5FRCIsIlBfQklMTF9MSVNUX0FQUFJPVkVfT0siLCJQX0NVU1RPTUVSX1BST0ZJTEVfU0VBUkNIIi" +
                    "wiUF9DVVNUT01FUl9BRERfTkVXIiwiUF9DVVNUT01FUl9TRUFSQ0giLCJQX0NVU1RPTUVSX1VQREFURSIsIlBfQ09OVFJBQ1RfU0VBUkNIIiwiUF9" +
                    "DT05UUkFDVF9WSUVXX0RFVEFJTCIsIlBfQ09OVFJBQ1RfVVBEQVRFX1NUQVRVUyIsIlBfTVlfQ09OVFJBQ1RfU0VBUkNIIiwiUF9TT1VSQ0VfU0VBU" +
                    "kNIIiwiUF9TT1VSQ0VfQUREX05FVyIsIlBfU09VUkNFX0VESVQiLCJQX1NPVVJDRV9VUERBVEVfU1RBVFVTIiwiUF9NWV9DT05UUkFDVF9CT09LX0" +
                    "FQUCIsIlBfU1NfRklOSVNIRUQiLCJQX1NTX0NIRUNLT1VUIiwiUF9EQ19CT09LX0FQUCIsIlBfU0FMRV9DVVNfTElTVF9TRUFSQ0giLCJQX1NBTEV" +
                    "fQ1VTX0xJU1RfQ1JFQVRFX0JJTEwiLCJQX0JDX1dTX0NPTkZJR19BRERfTkVXIiwiUF9CQ19XU19DT05GSUdfU0VBUkNIIiwiUF9ET0NUT1JfQ1VTV" +
                    "E9NRVJfREVUQUlMIiwiUF9SRUZfQ1VTX1NFQVJDSCIsIlBfUkVGX0NVU19BRERfTkVXIiwiUF9TQUxFX0NVU19MSVNUX0FERF9ORVciLCJQX1NBTE" +
                    "VfQ1VTX0xJU1RfVVBEQVRFIiwiUF9TQUxFX05PX0FQUE9JTlRNRU5UX1NFQVJDSCIsIlBfQ09OVFJBQ1RfVVBEQVRFX0NPQUNIIiwiUF9SRVBPUlRf" +
                    "U0FMRV9TRUFSQ0giLCJQX1JFUE9SVF9TQUxFU19TRUFSQ0giXSwic3RhZmYiOiJ7XCJzdGFmZklkXCI6OTAsXCJhZEFjY291bnRcIjpcImRldi1pdG" +
                    "FkXCIsXCJzdGFmZkNvZGVcIjpcIjAwMDI2XCIsXCJicmF2b1VzZXJHdWlkXCI6bnVsbCxcImZ1bGxOYW1lXCI6XCJJVCBhZG1pblwiLFwiZGlzcGxhe" +
                    "U5hbWVcIjpcIklUIGFkbWluIC0gSVRfQURNSU5cIixcImpvYklkXCI6MixcImpvYlRpdGxlXCI6XCJBRE1JTklTVFJBVE9SXCIsXCJwb3NpdGlvblwiO" +
                    "m51bGwsXCJzdGFmZkxldmVsXCI6OCxcImJyYW5jaElkXCI6MTQsXCJicmFuY2hDb2RlXCI6XCJBMDFcIixcImRlcGFydG1lbnRJZFwiOjMsXCJkZXBhc" +
                    "nRtZW50Q29kZVwiOlwiSVRcIixcImdyb3VwSWRc" +
                    "IjpudWxsLFwiZ3JvdXBDb2RlXCI6XCJcIixcImFnZW50RXh0XCI6XCJcIixcInRyYWNraW5nTG9nXCI6dHJ1ZX0ifQ.emx2kJHUdEzBzaWKyG" +
                    "vQ8_TV0uHTa02OYw_EIgz1VJ4";
            RateServiceDTO res = webClient.callInternalService(new ParameterizedTypeReference<RateServiceDTO>() {
            }, rq, HttpMethod.POST, "http://10.33.68.19:8888/api-catalog/api/v1.0/staffs/branch/14", RateServiceDTO.class, token1);
            return wrapperResponse.success(new ResponseData<>(ConstantsUtil.SUCCSESS, ConstantsUtil.SUCCSESS_MESS, res));
        } catch (Exception e) {
            return wrapperResponse.error(new ResponseData<>(ConstantsUtil.ERROR, ConstantsUtil.ERR_BUSINESS, null),
                    HttpStatus.BAD_REQUEST);
        }

    }
}
