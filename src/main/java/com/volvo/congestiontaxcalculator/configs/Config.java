package com.volvo.congestiontaxcalculator.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class Config
{
    @Getter @Setter
    private List<City> cities;

    public Optional<City> getCityConfigs(String cityName)
    {
        return cities.stream().filter(city -> city.getName().equals(cityName)).findFirst();
    }
}
