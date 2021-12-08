package com.oclet.service;

import com.oclet.api.model.AddPhoneNumberResponse;
import com.oclet.dto.CustomerDetails;
import com.oclet.repository.PhoneNumberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    public List<CustomerDetails> getAllPhoneNumbers(){
        return phoneNumberRepository.retrievePhoneNumbers();
    }

    public CustomerDetails getPhoneNumbersByCustomerId(String customerId) {
            return phoneNumberRepository.retrievePhoneNumbers(customerId);
    }

    public AddPhoneNumberResponse addPhoneNumberToCustomerDetails(String customerId, String phoneNumber){
        phoneNumberRepository.activatePhoneNumber(customerId, phoneNumber);
        return new AddPhoneNumberResponse("Successfully added");
    }

}
