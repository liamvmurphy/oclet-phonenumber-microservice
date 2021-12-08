package unit.com.oclet.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.api.model.AddPhoneNumberRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AddPhoneNumberRequestTest {
    private String addPhoneNumberRequest = "{\n" +
            "  \"phoneNumber\": \"1234567890\"\n" +
            "}";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void willDeserializeIntoObject() throws JsonProcessingException {
        AddPhoneNumberRequest result = objectMapper.readValue(addPhoneNumberRequest, AddPhoneNumberRequest.class);
        Assertions.assertThat(result.getPhoneNumber()).isEqualTo("1234567890");
    }
}
