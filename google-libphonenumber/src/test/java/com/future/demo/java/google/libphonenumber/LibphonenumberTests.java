package com.future.demo.java.google.libphonenumber;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class LibphonenumberTests {
    /**
     * 测试中国号码类型 fixed line、mobile
     * @throws NumberParseException
     */
    @Test
    public void test_cn_phone_number_type() throws NumberParseException {
        String phone = "+8607567750292";
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, null);
        PhoneNumberUtil.PhoneNumberType phoneNumberType = phoneNumberUtil.getNumberType(phoneNumber);
        Assert.assertEquals(PhoneNumberUtil.PhoneNumberType.FIXED_LINE, phoneNumberType);

        phone = "+8613560189481";
        phoneNumber = phoneNumberUtil.parse(phone, null);
        phoneNumberType = phoneNumberUtil.getNumberType(phoneNumber);
        Assert.assertEquals(PhoneNumberUtil.PhoneNumberType.MOBILE, phoneNumberType);
    }
}
