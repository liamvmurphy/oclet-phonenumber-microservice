package com.oclet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDetails {
    @JsonProperty("customerId")
    String customerId;

    //TreeSet allows all phonenumbers to be auto-ordered alphabetically.
    @JsonProperty("phoneNumbers")
    TreeSet<String> phoneNumbers;

    public void addPhoneNumber(String phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

}
