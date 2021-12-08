package com.oclet.validator;

import com.oclet.exception.ValidationException;
import com.oclet.utils.StringMaskerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Slf4j
@Component
public class PhoneNumberValidator {
    //No white space allowed!
    private static final String INVALID_PHONE_NUMBER_MESSAGE = "Failed phoneNumber validation";
    private Pattern pattern = Pattern.compile("^(\\+\\d{1,3}()?)?((\\(\\d{3}\\))|\\d{3})[-.]?\\d{3}[-.]?\\d{4}$");

    /**
     * @param phoneNumber - to validate against pattern
     * will throw ValidationException.class on failure.
     */
    public void validate(String phoneNumber) {
        if (phoneNumber == null || !pattern.matcher(phoneNumber).matches()) {
            log.warn("{} is invalid", StringMaskerUtils.maskPhoneNumber(phoneNumber));
            // I generally like to pass up validation type errors, or a map of errors. Gives error handler more access to info.
            throw new ValidationException(INVALID_PHONE_NUMBER_MESSAGE);
        }
    }
}
