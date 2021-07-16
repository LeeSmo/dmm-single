package com.app.dmm.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
//@PropertySource("wxPay.properties")
@PropertySource("classpath:config/config.properties")
public class ConfigProperties {

    @Value("${FILE_PATH}")
    private String FILE_PATH;


}
