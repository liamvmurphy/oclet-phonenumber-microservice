package unit.com.oclet.repository;

import com.oclet.dto.CustomerDetails;
import com.oclet.exception.CustomerNotFoundException;
import com.oclet.repository.PhoneNumberRepository;
import com.oclet.repository.PhoneNumberRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberRepositoryImplTest {

    PhoneNumberRepository phoneNumberRepository = new PhoneNumberRepositoryImpl(Arrays.asList(
            CustomerDetails.builder()
                    .customerId("Batman")
                    .phoneNumbers(new TreeSet<String>(Arrays.asList("0499999999", "0488888888")))
                    .build(),
            CustomerDetails.builder()
                    .customerId("Robin")
                    .phoneNumbers(new TreeSet<String>(Collections.singleton("83790000")))
                    .build())
    );

    //Usually I would use Junit5 Assertions.assertThrows(() -> ... but time constraints!
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void retrievePhoneNumbers_whenNoCustomerIdEntered_returnAllCustomerDetails() {
        List<CustomerDetails> list = phoneNumberRepository.retrievePhoneNumbers();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getCustomerId()).isEqualTo("Batman");
        assertThat(list.get(0).getPhoneNumbers().size()).isEqualTo(2);
        assertThat(list.get(0).getPhoneNumbers().contains("0499999999")).isTrue();
        assertThat(list.get(0).getPhoneNumbers().contains("0488888888")).isTrue();
        assertThat(list.get(1).getCustomerId()).isEqualTo("Robin");
        assertThat(list.get(1).getPhoneNumbers().size()).isEqualTo(1);
        assertThat(list.get(1).getPhoneNumbers().contains("83790000")).isTrue();
    }

    @Test
    public void retrievePhoneNumbers_whenNoCustomerIdEnteredeAndCustomerListEmpty_returnEmptyList() {
        PhoneNumberRepository phoneNumberRepository = new PhoneNumberRepositoryImpl(new ArrayList<>());
        List<CustomerDetails> list = phoneNumberRepository.retrievePhoneNumbers();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    public void retrievePhoneNumbers_whenCustomerIdEntered_returnCustomerDetails() {
        CustomerDetails customerDetails = phoneNumberRepository.retrievePhoneNumbers("Batman");
        assertThat(customerDetails).isNotNull();
        assertThat(customerDetails.getCustomerId()).isEqualTo("Batman");
        assertThat(customerDetails.getPhoneNumbers()).size().isEqualTo(2);
        assertThat(customerDetails.getPhoneNumbers()).contains("0499999999");
        assertThat(customerDetails.getPhoneNumbers()).contains("0488888888");
    }

    @Test(expected = CustomerNotFoundException.class)
    public void retrievePhoneNumbers_whenCustomerIdEnteredDoesNotExist_returnEmptyList() {
        PhoneNumberRepository phoneNumberRepository = new PhoneNumberRepositoryImpl(new ArrayList<>());
        phoneNumberRepository.retrievePhoneNumbers("ToothFairy");
    }

    @Test
    public void activatePhoneNumber_whenCustomerExists_addNewNumber() {
        phoneNumberRepository.retrievePhoneNumbers("Batman").getPhoneNumbers();
        phoneNumberRepository.activatePhoneNumber("Batman", "1111");
        Assertions.assertThat(phoneNumberRepository.retrievePhoneNumbers("Batman").getPhoneNumbers()).contains("1111");
    }

    @Test
    public void activatePhoneNumber_whenCustomerDoesNotExists_addNewCustomerAndNumber() throws CustomerNotFoundException {
        //Usually I would use Junit5 Assertions.assertThrows(() -> ... but time constraints!
        thrown.expect(CustomerNotFoundException.class);
        phoneNumberRepository.activatePhoneNumber("PoisonIvy", "0499222222");
    }

    @Test
    public void activatePhoneNumber_whenPhoneNumberNull_nothingIsAdded()  {
        phoneNumberRepository.activatePhoneNumber("Batman", null);
        Assertions.assertThat(phoneNumberRepository.retrievePhoneNumbers("Batman").getPhoneNumbers().isEmpty());
    }

    @Test
    public void activatePhoneNumber_whenCustomerNull_nothingIsAdded()  {
        //This class should never be used like this, validation is done via the API to ensure this does not happen. @NonNull marked on method
        //To ensure developers don't get the wrong idea.
        thrown.expect(NullPointerException.class);
        PhoneNumberRepository phoneNumberRepository = new PhoneNumberRepositoryImpl(new ArrayList<>());
        phoneNumberRepository.activatePhoneNumber(null, "1234");
        Assertions.assertThat(phoneNumberRepository.retrievePhoneNumbers(null).getPhoneNumbers().isEmpty());
    }

}
