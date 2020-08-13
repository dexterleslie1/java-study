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

        try {
            phone = "13560189481";
            phoneNumberUtil.parse(phone, null);
            Assert.fail("预期错误没有抛出");
        } catch (NumberParseException ex) {
            Assert.assertEquals("Missing or invalid default region.", ex.getMessage());
        }
    }

    /**
     *
     * @throws NumberParseException
     */
    @Test
    public void test_parse_E164_phone_number() throws NumberParseException {
        // 大陆手机
        String phone = "+8613560189481";
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, null);
        int countryCode = phoneNumber.getCountryCode();
        long nationalNumber = phoneNumber.getNationalNumber();
        Assert.assertEquals(86, countryCode);
        Assert.assertEquals(nationalNumber, 13560189481l);

        // 大陆固定电话
        phone = "+8607567750292";
        phoneNumber = phoneNumberUtil.parse(phone, null);
        countryCode = phoneNumber.getCountryCode();
        nationalNumber = phoneNumber.getNationalNumber();
        Assert.assertEquals(86, countryCode);
        Assert.assertEquals(nationalNumber, 7567750292l);

        // 香港号码
        phone = "+85261500911";
        phoneNumber = phoneNumberUtil.parse(phone, null);
        countryCode = phoneNumber.getCountryCode();
        nationalNumber = phoneNumber.getNationalNumber();
        Assert.assertEquals(852, countryCode);
        Assert.assertEquals(nationalNumber, 61500911l);

        // 日本号码
        phone = "+815055332721";
        phoneNumber = phoneNumberUtil.parse(phone, null);
        countryCode = phoneNumber.getCountryCode();
        nationalNumber = phoneNumber.getNationalNumber();
        Assert.assertEquals(81, countryCode);
        Assert.assertEquals(nationalNumber, 5055332721l);

        // 美国号码
        phone = "+12027596693";
        phoneNumber = phoneNumberUtil.parse(phone, null);
        countryCode = phoneNumber.getCountryCode();
        nationalNumber = phoneNumber.getNationalNumber();
        Assert.assertEquals(1, countryCode);
        Assert.assertEquals(nationalNumber, 2027596693l);
    }

    /**
     * 判断号码格式是否正确
     */
    @Test
    public void test_invalid_phone_number() throws NumberParseException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String phone = "+110";
        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, null);
        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        Assert.assertFalse(isValid);

        phone = "+12027596693";
        phoneNumber = phoneNumberUtil.parse(phone, null);
        isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        Assert.assertTrue(isValid);

        try {
            phone = "*#130#";
            phoneNumber = phoneNumberUtil.parse(phone, null);
            Assert.fail("预期异常没有抛出");
        } catch(NumberParseException ex) {
        }
    }
}
