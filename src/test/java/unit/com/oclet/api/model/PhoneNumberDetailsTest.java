package unit.com.oclet.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.api.model.PhoneNumberDetails;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

public class PhoneNumberDetailsTest {
    private String phoneNumberDetailsExpectation = "{\"customerId\":\"Batman\",\"phoneNumbers\":[\"1111111111\",\"2222222222\"]}";
    private PhoneNumberDetails phoneNumberDetails = PhoneNumberDetails.builder()
            .customerId("Batman")
            .phoneNumbers((Arrays.asList("1111111111", "2222222222"))).build();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void willSerialize() throws JsonProcessingException {
        String result = objectMapper.writeValueAsString(phoneNumberDetails);
        Assertions.assertThat(result).isEqualTo(phoneNumberDetailsExpectation);
    }
}
