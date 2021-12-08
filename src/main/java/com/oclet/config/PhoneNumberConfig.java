package com.oclet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.dto.CustomerDetails;
import com.oclet.exception.ResourceLoadingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * PhoneNumberConfig loads in a JSON List of phone numbers into memory!
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class PhoneNumberConfig {

    @Autowired
    ObjectMapper objectMapper;

    @Getter
    @Value("classpath:CustomerPhoneNumbers.json")
    private Resource rawCustomerPhoneNumbers;

    /**
     * @return List of Customers and their single/multiple phonenumbers.
     * Bean and can be loaded by other repository class. This would obviously not be recommended for a large datasource as this approach
     * Is not scalable and limited by memory.
     */
    @Bean
    public List<CustomerDetails> customerDetailsList() {
        //Try-With to ensure InputSteam is closed on fail.
        log.info("customerCustomerDetails 1");
        try (InputStream is = getRawCustomerPhoneNumbers().getInputStream()) {
            log.info("customerCustomerDetails 2");
            String customerDetailsString = IOUtils.toString(is, StandardCharsets.UTF_8);
            log.info("customerCustomerDetails 3 {}", customerDetailsString);
            return Arrays.asList(objectMapper.readValue(customerDetailsString, CustomerDetails[].class));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResourceLoadingException();
        }
    }
}
