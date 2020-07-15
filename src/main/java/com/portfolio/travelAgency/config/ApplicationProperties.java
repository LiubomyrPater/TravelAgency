package com.portfolio.travelAgency.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter

@Component
public class ApplicationProperties {

    @Value("${demo.config.token.time-to-live}")
    private int tokenTimeToLiveInHours;

    @Value("${demo.config.ApplicationProperties.passwordLength}")
    private int passwordLength;

    @Value("${demo.config.ApplicationProperties.tokenValiditySeconds}")
    private int tokenValiditySeconds;


}
