package com.testspringboot.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BalanceRequest {

    @JsonProperty("value")
    private BigDecimal valueToUpdate;

    public BigDecimal getValueToUpdate() {
        return valueToUpdate;
    }

    public void setValueToUpdate(BigDecimal valueToUpdate) {
        this.valueToUpdate = valueToUpdate;
    }
}
