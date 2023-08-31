package com.password.password;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = {"file:////Users/b0224854/Trade/configs.conf"}, ignoreResourceNotFound = true),
        @PropertySource(value = {"file:D:\\Trade\\tradeConfigs.conf"}, ignoreResourceNotFound = true),
        @PropertySource(value = {"file:////home/vijaykumarvijay886cloud2/Trade/tradeConfigs.conf"}, ignoreResourceNotFound = true),
        @PropertySource(value = {"file:////home/vijaykumarvijay886/Trade/tradeConfigs.conf"}, ignoreResourceNotFound = true)
})
@Data
@Log4j2
public class Configs {

    @Value("${gmailPassword}")
    String gmailPassword;


}
