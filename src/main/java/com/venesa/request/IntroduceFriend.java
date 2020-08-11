package com.venesa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntroduceFriend implements Serializable {
    private String code;
    private String dateComplete;
}
