package com.oclet.repository;

import com.oclet.dto.CustomerDetails;

import java.util.List;

public interface PhoneNumberRepository {

    List<CustomerDetails> retrievePhoneNumbers();

    CustomerDetails retrievePhoneNumbers(String customerNumber);

    void activatePhoneNumber(String customerNumber, String phoneNumber);
}
