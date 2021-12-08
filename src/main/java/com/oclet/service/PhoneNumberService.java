package com.oclet.service;

import com.oclet.api.model.AddPhoneNumberResponse;
import com.oclet.api.model.PhoneNumbers;
import com.oclet.dto.CustomerDetails;
import com.oclet.repository.PhoneNumberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private static final String SUCCESSFUL_ADD_PHONE_NUMBER_MESSAGE = "Successfully added";

    public List<CustomerDetails> getAllPhoneNumbers() {
        return phoneNumberRepository.retrievePhoneNumbers();
    }

    public PhoneNumbers getPhoneNumbersByCustomerId(String customerId) {
        CustomerDetails customerDetails = phoneNumberRepository.retrievePhoneNumbers(customerId);
        return PhoneNumbers.builder().phoneNumbers(new ArrayList<>(customerDetails.getPhoneNumbers())).build();
    }

    public AddPhoneNumberResponse addPhoneNumberToCustomerDetails(String customerId, String phoneNumber) {
        phoneNumberRepository.activatePhoneNumber(customerId, phoneNumber);
        return new AddPhoneNumberResponse(SUCCESSFUL_ADD_PHONE_NUMBER_MESSAGE);
    }

}
