package unit.com.oclet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oclet.config.PhoneNumberConfig;
import com.oclet.dto.CustomerDetails;
import com.oclet.exception.ResourceLoadingException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class PhoneNumberConfigTest {
    Resource rawCustomerPhoneNumbers = new ClassPathResource("CustomerPhoneNumbers.json");
    PhoneNumberConfig phoneNumberConfig = new PhoneNumberConfig();
    CustomerDetails validCustomer1 = CustomerDetails.builder()
            .customerId("Jerry")
            .phoneNumbers(new TreeSet<>(Arrays.asList("1111111111", "2222222222")))
            .build();

    CustomerDetails validCustomer2 = CustomerDetails.builder()
            .customerId("Tom")
            .phoneNumbers(new TreeSet<>(Arrays.asList("3333333333")))
            .build();

    CustomerDetails invalidCustomer = CustomerDetails.builder()
            .customerId("NotAdded")
            .phoneNumbers(new TreeSet<>(Arrays.asList("1111111111")))
            .build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void initTests() {
        //very ordinary quickhand way of glueing *very* basic spring components together.
        ReflectionTestUtils.setField(phoneNumberConfig, "rawCustomerPhoneNumbers", rawCustomerPhoneNumbers);
        ReflectionTestUtils.setField(phoneNumberConfig, "objectMapper", new ObjectMapper());
    }

    @Test
    public void customerDetailsList_whenResourceExist_ResourceIsLoaded() {
        Assertions.assertThat(rawCustomerPhoneNumbers.getFilename()).isEqualTo("CustomerPhoneNumbers.json");
        List<CustomerDetails> list = phoneNumberConfig.customerDetailsList();
        Assertions.assertThat(list).contains(validCustomer1);
        Assertions.assertThat(list).contains(validCustomer2);
        Assertions.assertThat(list).doesNotContain(invalidCustomer);
    }

    @Test
    public void customerDetailsList_whenResourceDoesNotExist_throwResourceLoadingException() {
        thrown.expect(ResourceLoadingException.class);
        Resource badFile = new ClassPathResource("NotArealFile.json");
        ReflectionTestUtils.setField(phoneNumberConfig, "rawCustomerPhoneNumbers", badFile);
        Assertions.assertThat(badFile.getFilename()).isEqualTo("NotArealFile.json");
        List<CustomerDetails> list = phoneNumberConfig.customerDetailsList();
    }
}
