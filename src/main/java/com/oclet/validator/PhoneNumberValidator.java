package com.oclet.validator;

import com.oclet.utils.StringMaskerUtils;
import com.oclet.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Slf4j
@Component
public class PhoneNumberValidator {
    //No white space allowed!
    Pattern pattern = Pattern.compile("^(\\+\\d{1,3}()?)?((\\(\\d{3}\\))|\\d{3})[-.]?\\d{3}[-.]?\\d{4}$");

    public void validate(String phoneNumber) {
        if (phoneNumber == null || !pattern.matcher(phoneNumber).matches()) {
            log.warn("{} is invalid", StringMaskerUtils.maskPhoneNumber(phoneNumber));
            throw new ValidationException("failed phoneNumber validation");
        }
    }
}
