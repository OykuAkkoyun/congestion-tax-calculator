package com.volvo.congestiontaxcalculator.service;

import com.volvo.congestiontaxcalculator.helpers.Constants;
import com.volvo.congestiontaxcalculator.helpers.DateTimeHelper;
import com.volvo.congestiontaxcalculator.service.model.CongestionTaxCalculatorResponse;
import com.volvo.congestiontaxcalculator.configs.City;
import com.volvo.congestiontaxcalculator.configs.Config;
import com.volvo.congestiontaxcalculator.configs.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CongestionTaxCalculatorService
{
    @Autowired
    Config config;

    Optional<City> cityConfigs;

    public CongestionTaxCalculatorResponse congestionTaxCalculation(String cityName, String vehicleType, List<LocalDateTime> dates) {
        CongestionTaxCalculatorResponse response = new CongestionTaxCalculatorResponse();
        int taxAmount = -1;
        cityConfigs = config.getCityConfigs(cityName);

        if(!cityConfigs.isEmpty()) {
            taxAmount = getTaxAmount(vehicleType, dates, cityConfigs.get().getDurationTime());
            response.setDetailMessage(Constants.TAX_CALCULATED_MESSAGE + vehicleType);
        }
        else {
            response.setDetailMessage(Constants.CONFIG_NOT_FOUND + cityName);
        }
        response.setTaxAmount(taxAmount);
        return response;
    }

    public int getTaxAmount(String vehicleType, List<LocalDateTime> dates, int timeConfig) {
        int totalFee = 0;
        int tollFee = 0;
        int taxAmountInSameDuration = 0;

        Collections.sort(dates);

        if(!checkVehicleIsTollFree(vehicleType)) {
            for (int i = 0; i < dates.size(); i++) {
                if (i == 0 || DateTimeHelper.checkDateBetweenConfigMinutes(dates.get(i - 1), dates.get(i), timeConfig)) {
                    tollFee = getTollFee(dates.get(i));
                    totalFee += tollFee;
                } else {
                    taxAmountInSameDuration = getTollFee(dates.get(i));
                    if (tollFee != 0 && tollFee < taxAmountInSameDuration) {
                        totalFee = taxAmountInSameDuration;
                    }
                }
            }

            if (totalFee > cityConfigs.get().getMaxAmount()) {
                totalFee = cityConfigs.get().getMaxAmount();
            }
        }
        return totalFee;
    }

    public int getTollFee(LocalDateTime date) {
        if (DateTimeHelper.checkIsDateTaxFree(date)) {
            return 0;
        }

        String hour = date.getHour() + ":" + date.getMinute();
        return cityConfigs.get().getRules().stream().filter(obj -> obj.isTimeBetweenRuleTimes(hour))
                .findFirst().orElse(new Rule()).getAmount();
    }

    private boolean checkVehicleIsTollFree(String vehicleType) {
        if (vehicleType == null) {
            return false;
        }
        return cityConfigs.get().getTaxExemptVehicles().contains(vehicleType);
    }
}
