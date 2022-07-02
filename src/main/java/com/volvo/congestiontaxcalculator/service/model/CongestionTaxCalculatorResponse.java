package com.volvo.congestiontaxcalculator.service.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
public class CongestionTaxCalculatorResponse
{
    @Getter
    @Setter
    private int taxAmount;
    @Getter
    @Setter
    private String detailMessage;


    public CongestionTaxCalculatorResponse(int taxAmount, String message)
    {
        super();
        this.taxAmount = taxAmount;
        this.detailMessage = message;
    }

}
