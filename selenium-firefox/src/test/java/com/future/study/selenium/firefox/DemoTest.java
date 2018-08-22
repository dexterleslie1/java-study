package com.future.study.selenium.firefox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * @author Dexterleslie
 */
public class DemoTest {
    private WebDriver driver;

    @Before
    public void setup(){
        System.setProperty("webdriver.gecko.driver","d:\\tools\\geckodriver.exe");
        FirefoxOptions options=new FirefoxOptions();
        options.setCapability("marionette",false);
        driver=new FirefoxDriver(options);
    }

    @After
    public void teardown(){
        driver.quit();
    }

    @Test
    public void test_demo1() throws InterruptedException {
        driver.get("https://auth.alipay.com/login/index.htm");
        WebElement element=((FirefoxDriver) driver).findElementByXPath("//li[text()='账密登录']");
        element.click();

        String loginname="";
        element=((FirefoxDriver) driver).findElementById("J-input-user");
        for(char ch1:loginname.toCharArray()) {
            Thread.sleep(200);
            element.sendKeys(String.valueOf(ch1));
        }

        String password="";
        element=((FirefoxDriver) driver).findElementById("password_rsainput");
        for(char ch1:password.toCharArray()) {
            Thread.sleep(200);
            element.sendKeys(String.valueOf(ch1));
        }

        element=((FirefoxDriver) driver).findElementById("J-login-btn");
        element.click();

        element=((FirefoxDriver) driver).findElementByXPath("//a[text()='交易记录']");
        element.click();
    }
}
