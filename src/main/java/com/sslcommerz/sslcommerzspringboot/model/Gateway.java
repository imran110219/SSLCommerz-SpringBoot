package com.sslcommerz.sslcommerzspringboot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null fields
public class Gateway {
    private String visa;
    private String master;
    private String amex;
    private String othercards;
    private String internetbanking;
    private String mobilebanking;
}
