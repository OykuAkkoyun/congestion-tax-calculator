package com.volvo.congestiontaxcalculator.controller;

import com.volvo.congestiontaxcalculator.helpers.Constants;
import com.volvo.congestiontaxcalculator.service.CongestionTaxCalculatorService;
import com.volvo.congestiontaxcalculator.service.model.CongestionTaxCalculatorRequest;
import com.volvo.congestiontaxcalculator.service.model.CongestionTaxCalculatorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tax")
public class CongestionTaxCalculatorController
{
    @Autowired
    CongestionTaxCalculatorService congestionTaxCalculator;

    @RequestMapping(value = "/calculator", method = RequestMethod.POST)
    @Operation(summary = "Congestion Tax Calculator")
    public ResponseEntity<CongestionTaxCalculatorResponse> congestionTaxCalculate(@ApiParam(value = "Date Format: yyyy-MM-dd HH:mm:ss", required = true)
            @RequestBody CongestionTaxCalculatorRequest request)
    {
        CongestionTaxCalculatorResponse response = new CongestionTaxCalculatorResponse();
        try {
            String validationMessage = request.validateInput(request);
            if(validationMessage.equals(Constants.VALID_INPUT_MESSAGE)) {
                response = congestionTaxCalculator.congestionTaxCalculation(request.getCityName(), request.getVehicleType(), request.getDates());

            }
            else {
                response.setDetailMessage(validationMessage);
            }
            return new ResponseEntity<CongestionTaxCalculatorResponse>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            response = new CongestionTaxCalculatorResponse(-1, Constants.SERVICE_FAILED);
            return new ResponseEntity<CongestionTaxCalculatorResponse>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
