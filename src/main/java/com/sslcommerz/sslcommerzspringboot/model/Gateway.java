package com.sslcommerz.sslcommerzspringboot.model;

import lombok.Data;

import java.util.List;

@Data
public class Gateway {
    private List<String> visa;
    private List<String> master;
    private List<String> amex;
    private List<String> othercards;
    private List<String> internetbanking;
    private List<String> mobilebanking;
}
