package com.projectdepot.ecommerce.Order_Service.Configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ModelMapperConfigs {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
