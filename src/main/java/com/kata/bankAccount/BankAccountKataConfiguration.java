package com.kata.bankAccount;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankAccountKataConfiguration {


    @Bean
    public ModelMapper modelMapper() {
       return new ModelMapper();

    }
}

