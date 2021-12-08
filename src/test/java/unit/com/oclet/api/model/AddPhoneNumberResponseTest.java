package unit.com.oclet.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.api.model.AddPhoneNumberResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AddPhoneNumberResponseTest {
    private String addPhoneNumberExpectation = "{\"message\":\"Phone number successfully added to customer details\"}";
    private AddPhoneNumberResponse addPhoneNumberResponse = new AddPhoneNumberResponse("Phone number successfully added to customer details");

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void willSerialize() throws JsonProcessingException {
        String result = objectMapper.writeValueAsString(addPhoneNumberResponse);
        Assertions.assertThat(result).isEqualTo(addPhoneNumberExpectation);
    }
}
