package unit.com.oclet.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.api.model.PhoneNumbers;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;

public class PhoneNumbersTest {
    private String phoneNumbersExpectation = "{\"phoneNumbers\":[\"1111111111\",\"2222222222\"]}";
    private PhoneNumbers phoneNumber = PhoneNumbers.builder()
            .phoneNumbers((Arrays.asList("1111111111", "2222222222"))).build();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void willSerialize() throws JsonProcessingException {
        String result = objectMapper.writeValueAsString(phoneNumber);
        Assertions.assertThat(result).isEqualTo(phoneNumbersExpectation);
    }
}
