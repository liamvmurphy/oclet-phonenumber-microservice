package unit.com.oclet.validator;

import com.oclet.exception.ValidationException;
import com.oclet.validator.PhoneNumberValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PhoneNumberValidatorTest {
    PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
    List<String> validCombinations = Arrays.asList("0414142432", "+61414142432", "+61414133323", "+61414133323", "+611414133323");
    List<String> invalidCombinations = Arrays.asList(null, "X0414142432", "+6114$14133323", "+61S1414133323", "+611414 O133 323", "+61 }{1414 133323");


    //Usually I would use Junit5 Assertions.assertThrows(() -> ... but time constraints!
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //Normally would use @ParameterisedTest for usecases like this, but time constraints!
    @Test
    public void validate_whenGivenValidPhoneNumberCombinations_return() {
        validCombinations.stream().forEach(item -> {
            log.info("phoneNumber={}", item);
            phoneNumberValidator.validate(item);
        });
    }


    /**
     * @throws ValidationException -> try/catch required to not get exited in loop in Junit4. Junit5 has parameterised tests which look and feel much better.
     */
    @Test
    public void validate_whenGivenInvalidPhoneNumberCombinations_return() throws ValidationException {
        invalidCombinations.stream().forEach(item -> {
            try {
                phoneNumberValidator.validate(item);
                assert false;
            } catch (ValidationException exp) {
                thrown = ExpectedException.none();
            }
        });
    }
}
