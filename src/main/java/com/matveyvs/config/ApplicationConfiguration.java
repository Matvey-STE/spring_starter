package com.matveyvs.config;

import com.matveyvs.utils.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.matveyvs")
@PropertySource("classpath:app.properties")
public class ApplicationConfiguration {

    @Bean
    @Profile("prod")
    public ConnectionManager connectionManager(
            @Value(value = "${postgres.db}") String urlKey,
            @Value(value = "${postgres.name}") String urlName,
            @Value(value = "${postgres.password}") String urlPassword,
            @Value(value = "${postgres.driver}") String urlDriver) {
        return new ConnectionManager(urlKey, urlName, urlPassword, urlDriver);
    }

    @Bean
    @Profile("test")
    public ConnectionManager testConnectionManager(
            @Value("${test.postgres.db}") String urlKey,
            @Value("${test.postgres.name}") String urlName,
            @Value("${test.postgres.password}") String urlPassword,
            @Value("${test.postgres.driver}") String urlDriver) {
        return new ConnectionManager(urlKey, urlName, urlPassword, urlDriver);
    }
}
