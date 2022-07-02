package com.volvo.congestiontaxcalculator.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "config.city")
public class City
{
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int durationTime;
    @Getter @Setter
    private int maxAmount;
    @Getter @Setter
    private List<Rule> rules;
    @Getter @Setter
    private List<String> taxExemptVehicles;
}
