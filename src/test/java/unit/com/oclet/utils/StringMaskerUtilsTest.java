package unit.com.oclet.utils;

import com.oclet.utils.StringMaskerUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class StringMaskerUtilsTest {

    @Test
    public void maskPhoneNumber_whenNullValue_returnEmptyString(){
        Assertions.assertThat(StringMaskerUtils.maskPhoneNumber(null)).isEqualTo(StringMaskerUtils.EMPTY_STRING);
    }

    @Test
    public void maskPhoneNumber_when4Chars_return4DigitsClearText(){
        Assertions.assertThat(StringMaskerUtils.maskPhoneNumber("1234")).isEqualTo("1234");
    }

    @Test
    public void maskPhoneNumber_when10Chars_returnOnlyLast4DigitsClearText(){
        Assertions.assertThat(StringMaskerUtils.maskPhoneNumber("1234567890")).isEqualTo("######7890");
    }
}
