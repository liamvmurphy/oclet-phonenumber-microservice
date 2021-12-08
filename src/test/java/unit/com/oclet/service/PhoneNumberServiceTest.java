package unit.com.oclet.service;

import com.oclet.api.model.AddPhoneNumberResponse;
import com.oclet.dto.CustomerDetails;
import com.oclet.exception.CustomerNotFoundException;
import com.oclet.repository.PhoneNumberRepository;
import com.oclet.service.PhoneNumberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberServiceTest {

    @Mock
    PhoneNumberRepository phoneNumberRepository;

    @InjectMocks
    PhoneNumberService phoneNumberService;

    List<CustomerDetails> validList = Arrays.asList(
            CustomerDetails.builder()
                    .customerId("Batman")
                    .phoneNumbers(new TreeSet<>(Arrays.asList("0499999999", "0488888888")))
                    .build(),
            CustomerDetails.builder()
                    .customerId("Robin")
                    .phoneNumbers(new TreeSet<String>(Collections.singleton("83790000")))
                    .build());


    @Before
    public void initMocks() {
        Mockito.when(phoneNumberRepository.retrievePhoneNumbers()).thenReturn(validList);
        Mockito.when(phoneNumberRepository.retrievePhoneNumbers("Batman")).thenReturn(validList.get(0));
    }

    @Test
    public void getAllPhoneNumbers_whenPhoneNumbersExist() {
        List<CustomerDetails> customerDetailsList = phoneNumberService.getAllPhoneNumbers();
        Mockito.verify(phoneNumberRepository, Mockito.times(1)).retrievePhoneNumbers();
        assertThat(customerDetailsList.size()).isEqualTo(2);
        assertThat(customerDetailsList.get(0).getCustomerId()).isEqualTo("Batman");
        assertThat(customerDetailsList.get(0).getPhoneNumbers().size()).isEqualTo(2);
        assertThat(customerDetailsList.get(0).getPhoneNumbers().contains("0499999999")).isTrue();
        assertThat(customerDetailsList.get(0).getPhoneNumbers().contains("0488888888")).isTrue();
        assertThat(customerDetailsList.get(1).getCustomerId()).isEqualTo("Robin");
        assertThat(customerDetailsList.get(1).getPhoneNumbers().size()).isEqualTo(1);
        assertThat(customerDetailsList.get(1).getPhoneNumbers().contains("83790000")).isTrue();

    }

    @Test
    public void getPhoneNumbersByCustomerId_whenPhoneNumbersExist() {
        CustomerDetails customerDetails = phoneNumberService.getPhoneNumbersByCustomerId("Batman");
        Mockito.verify(phoneNumberRepository, Mockito.times(1)).retrievePhoneNumbers("Batman");
        assertThat(customerDetails.getCustomerId()).isEqualTo("Batman");
        assertThat(customerDetails.getPhoneNumbers().size()).isEqualTo(2);
        assertThat(customerDetails.getPhoneNumbers().contains("0499999999")).isTrue();
        assertThat(customerDetails.getPhoneNumbers().contains("0488888888")).isTrue();
    }

    @Test
    public void activatePhoneNumber_whenCustomerDoesNotExists_addNewCustomerAndNumber() throws CustomerNotFoundException {
        AddPhoneNumberResponse addPhoneNumberResponse = phoneNumberService.addPhoneNumberToCustomerDetails("Hogan", "1111111111");
        Mockito.verify(phoneNumberRepository, Mockito.times(1)).activatePhoneNumber("Hogan", "1111111111");
    }


}
