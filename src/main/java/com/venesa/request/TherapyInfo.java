package com.venesa.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TherapyInfo implements Serializable {
    private String serviceCode;
    private String startTime;
    private String endTime;
    private Integer sessionNumber;
    private Integer sessionTotal;
    private Integer profileId;
}
