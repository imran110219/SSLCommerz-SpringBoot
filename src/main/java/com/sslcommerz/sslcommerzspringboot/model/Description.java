package com.sslcommerz.sslcommerzspringboot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null fields
public class Description {
    private String name;
    private String type;
    private String logo;
    private String gw;
    private String rFlag;
    private String redirectGatewayURL;
}
