package component.com.ocelet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.Application;
import com.oclet.api.model.AddPhoneNumberRequest;
import com.oclet.repository.PhoneNumberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class)
@AutoConfigureMockMvc
public class PhoneNumberControllerTest {
    @Autowired
    private MockMvc mvc;

    @Spy
    private PhoneNumberRepository phoneNumberRepository;

    @Test
    public void getAllPhoneNumbers_requestOk_returnPhoneNumbers() throws Exception {
        mvc.perform(get("/v1/customers/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerId", is("Jerry")))
                .andExpect(jsonPath("$[0].phoneNumbers[0]", is("1111111111")))
                .andExpect(jsonPath("$[0].phoneNumbers.[1]", is("2222222222")))
                .andExpect(jsonPath("$[1].customerId", is("Tom")))
                .andExpect(jsonPath("$[1].phoneNumbers.[0]", is("3333333333")));
    }

    @Test
    public void getAllPhoneNumbers_whenVersionWrong_return404NotFound() throws Exception {
        mvc.perform(get("/v2/customers/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API version unsupported")));
    }

    @Test
    public void getAllPhoneNumbers_whenVersionNegative_return404NotFound() throws Exception {
        mvc.perform(get("/v-1/customers/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API version unsupported")));
    }

    @Test
    public void getPhoneNumbersByCustomerId_requestOk_returnPhoneNumbers() throws Exception {
        mvc.perform(get("/v1/customers/Jerry/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("phoneNumbers[0]", is("1111111111")))
                .andExpect(jsonPath("phoneNumbers.[1]", is("2222222222")));
    }

    @Test
    public void getPhoneNumbersByCustomerId_customerIdDoesNotExist_return404NotFound() throws Exception {
        mvc.perform(get("/v1/customers/PoisonIvy/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("Customer does not exist")));
    }

    @Test
    public void getPhoneNumbersByCustomerId_whenVersionWrong_return404NotFound() throws Exception {
        mvc.perform(get("/v2/customers/PoisonIvy/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API version unsupported")));
    }

    @Test
    public void getPhoneNumbersByCustomerId_whenVersionNegative_return404NotFound() throws Exception {
        mvc.perform(get("/v-1/customers/PoisonIvy/phonenumbers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API version unsupported")));
    }

    @Test
    public void addPhoneNumberToCustomerDetails_validRequest_return201() throws Exception {
        mvc.perform(post("/v1/customers/Jerry/phonenumber")
                .content(objectToJSON(new AddPhoneNumberRequest("0414141414")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message", is("Successfully added")));
    }

    @Test
    public void addPhoneNumberToCustomerDetails_customerIdDoesNotExist_return404NotFound() throws Exception {
        mvc.perform(post("/v1/customers/PoisonIvy/phonenumber")
                .content(objectToJSON(new AddPhoneNumberRequest("0414141414")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("Customer does not exist")));
    }

    @Test
    public void addPhoneNumberToCustomerDetails_phoneNumberBadFormat_return400BadRequest() throws Exception {
        mvc.perform(post("/v1/customers/Jerry/phonenumber")
                .content(objectToJSON(new AddPhoneNumberRequest("041 414 1414")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("BAD_REQUEST")))
                .andExpect(jsonPath("message", is("Failed phoneNumber validation")));
    }

    @Test
    public void addPhoneNumberToCustomerDetails_whenVersionWrong_return404NotFound() throws Exception {
        mvc.perform(post("/v2/customers/Jerry/phonenumber")
                .content(objectToJSON(new AddPhoneNumberRequest("0414141414")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API version unsupported")));
    }

    @Test
    public void addPhoneNumberToCustomerDetails_whenVersionNegative_return404NotFound() throws Exception {
        mvc.perform(post("/v-1/customers/Jerry/phonenumber")
                .content(objectToJSON(new AddPhoneNumberRequest("0414141414")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API version unsupported")));
    }

    @Test
    public void callUnknownApi_return404NotFound() throws Exception {
        mvc.perform(post("/v1/what/is/thisr")
                .content(objectToJSON(new AddPhoneNumberRequest("041 414 1414")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status", is("NOT_FOUND")))
                .andExpect(jsonPath("message", is("API path / method combination not found")));
    }

    //little test utility to save write to json.
    public static String objectToJSON(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
