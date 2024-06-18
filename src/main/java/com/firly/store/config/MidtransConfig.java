package com.firly.store.config;

import com.midtrans.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransConfig {

    @Bean(name = "MidtransConfig")
    public Config midtransConfiguration() {
        return Config.builder()
                .setClientKey("YOUR_SERVER_KEY")
                .setServerKey("YOUR_CLIENT_KEY")
                .setIsProduction(false)
                .build();
    }
}
