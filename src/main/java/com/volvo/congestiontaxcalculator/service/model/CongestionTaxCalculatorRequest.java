package com.volvo.congestiontaxcalculator.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volvo.congestiontaxcalculator.helpers.Constants;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel
@Getter
@Setter
public class CongestionTaxCalculatorRequest
{
    private String cityName;
    private String vehicleType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> dates;

    public String validateInput(CongestionTaxCalculatorRequest request)
    {
       if(request.cityName == null)
           return Constants.INPUT_CANNOT_BE_NULL + "cityName";
       if(request.vehicleType == null)
           return Constants.INPUT_CANNOT_BE_NULL + "vehicleType";
       if(request.dates == null || request.dates.size() == 0)
           return Constants.INPUT_CANNOT_BE_NULL + "dates";

       return Constants.VALID_INPUT_MESSAGE;
    }
}
