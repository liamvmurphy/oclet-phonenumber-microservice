package com.oclet.controller;

import com.oclet.api.model.AddPhoneNumberRequest;
import com.oclet.api.model.AddPhoneNumberResponse;
import com.oclet.api.model.PhoneNumbers;
import com.oclet.dto.CustomerDetails;
import com.oclet.exception.UnsupportedVersionException;
import com.oclet.service.PhoneNumberService;
import com.oclet.utils.StringMaskerUtils;
import com.oclet.validator.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Standard REST Controller, could have opted for a reactive setup, but kept it simple.
 * These API's correspond 1 to 1 with PhoneNumberApi-v1.swagger.yaml
 */
@AllArgsConstructor
@Slf4j
@RestController
class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    private final PhoneNumberValidator phoneNumberValidator;


    /**
     * @param version - version of the API -> Not useful for this example - I would have a version handling annotation that returns
     *                VERSION NOT FOUND exceptions to users when requesting to an invalid version
     * @return standard ResponseEntity.
     */
    @GetMapping("/v{version}/customers/phonenumbers")
    public ResponseEntity<List<CustomerDetails>> getAllPhoneNumbers(@PathVariable long version) {
        if (version < 0 || version > 1) {
            throw new UnsupportedVersionException();
        }
        log.info("GetAllPhoneNumbers request received");
        List<CustomerDetails> customerDetailsList = phoneNumberService.getAllPhoneNumbers();

        log.info("GetAllPhoneNumbers completed");
        return ResponseEntity.ok(customerDetailsList);
    }

    /**
     * @param version - version of the API -> Not useful for this example - I would have a version handling annotation that returns
     *                VERSION NOT FOUND exceptions to users when requesting to an invalid version
     * @return standard ResponseEntity.
     */
    @GetMapping("/v{version}/customers/{customer_id}/phonenumbers")
    public ResponseEntity<PhoneNumbers> getPhoneNumbersByCustomerId(
            @PathVariable long version,
            @PathVariable("customer_id") String customerId
    ) {
        if (version < 0 || version > 1) {
            throw new UnsupportedVersionException();
        }
        log.info("GetPhoneNumbers for Customer request received, customer={}", customerId);

        PhoneNumbers getPhoneNumberResponse = phoneNumberService.getPhoneNumbersByCustomerId(customerId);

        log.info("GetPhoneNumbers for Customer completed, customer={}", customerId);
        return ResponseEntity.ok(getPhoneNumberResponse);
    }

    /**
     * @param version - version of the API -> Not useful for this example - I would have a version handling annotation that returns
     *                VERSION NOT FOUND exceptions to users when requesting to an invalid version
     * @return standard ResponseEntity.
     */
    //make these accept only json
    @PostMapping("/v{version}/customers/{customer_id}/phonenumber")
    public ResponseEntity<AddPhoneNumberResponse> addPhoneNumberToCustomerDetails(
            @PathVariable long version,
            @PathVariable("customer_id") String customerId,
            @RequestBody AddPhoneNumberRequest addPhoneNumberRequest
    ) {
        if (version < 0 || version > 1) {
            throw new UnsupportedVersionException();
        }
        phoneNumberValidator.validate(addPhoneNumberRequest.getPhoneNumber());
        String maskedPhoneNumber = StringMaskerUtils.maskPhoneNumber(addPhoneNumberRequest.getPhoneNumber());
        log.info("AddPhoneNumber request received, customer={}, phoneNumber={}", customerId, maskedPhoneNumber);

        AddPhoneNumberResponse addPhoneNumberResponse = phoneNumberService.addPhoneNumberToCustomerDetails(customerId, addPhoneNumberRequest.getPhoneNumber());

        log.info("AddPhoneNumber request completed, customer={}, phoneNumber={}", customerId, maskedPhoneNumber);
        return new ResponseEntity<>(
                addPhoneNumberResponse, new HttpHeaders(), HttpStatus.CREATED);
    }


}
