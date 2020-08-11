package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TherapyInfo implements Serializable {
    private String serviceCode;
    private String startTime;
    private String endTime;
    private Integer sessionNumber;
    private Integer sessionTotal;
    private Integer profileId;
}
