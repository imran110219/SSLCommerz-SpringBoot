package com.sslcommerz.sslcommerzspringboot.model;

import lombok.Data;

import java.util.List;

@Data
public class Gateway {
    private String visa;
    private String master;
    private String amex;
    private String othercards;
    private String internetbanking;
    private String mobilebanking;
}
