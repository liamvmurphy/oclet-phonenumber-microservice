package com.oclet.repository;

import com.oclet.dto.CustomerDetails;
import com.oclet.exception.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class PhoneNumberRepositoryImpl implements PhoneNumberRepository {

    /**
     * Used to grab reference to statically loaded files initalised on run in the PhoneNumberConfig class.
     */
    @Autowired
    List<CustomerDetails> customerDetailsList;

    /**
     * Retrieve all customer details
     * returns list of customers details
     */
    public List<CustomerDetails> retrievePhoneNumbers() {
        return customerDetailsList;
    }

    /**
     * Retrieves a single set of customer details
     * returns list of customers and their corresponding list of phone numbers
     */
    public CustomerDetails retrievePhoneNumbers(@NonNull String customerNumber) {
        return customerDetailsList.stream()
                .filter(item -> customerNumber.equals(item.getCustomerId()))
                .findFirst()
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * Adds a phonenumber to a customer already in the books
     * throws error when customer does not exist.
     */
    public void activatePhoneNumber(@NonNull String customerNumber, String customerPhoneNumber) {
        addPhoneNumber(customerNumber, customerPhoneNumber);
    }


    /**
     * discrete method to add to our phone number datasource.
     */
    private void addPhoneNumber(@NonNull String customerNumber, String phoneNumber) {
        //locate customer
        if (phoneNumber == null) {
            return;
        }
        CustomerDetails customerDetails = customerDetailsList.stream()
                .filter(item -> customerNumber.equals(item.getCustomerId()))
                .findFirst()
                .orElseThrow(CustomerNotFoundException::new);
        customerDetails.addPhoneNumber(phoneNumber);
    }
}
