package com.future.demo.java.appium;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTests {
    /**
     *
     */
    @Test
    public void test() throws InterruptedException, MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.xy.chat.app.aschat");
        capabilities.setCapability("appActivity", ".MainActivity");

        AndroidDriver driver = new AndroidDriver(new URL("http://192.168.1.124:4723/wd/hub"), capabilities);

        Thread.sleep(3000);

        driver.findElement(By.id("com.xy.chat.app.aschat:id/main_btn_login_entry")).click();
//        driver.findElement(By.name("5")).click();
//        driver.findElement(By.name("9")).click();
//        driver.findElement(By.name("delete")).click();
//        driver.findElement(By.name("+")).click();
//        driver.findElement(By.name("6")).click();
//        driver.findElement(By.name("=")).click();
//        Thread.sleep(2000);
//
//        String result = driver.findElement(By.id("com.android.calculator2:id/formula")).getText();
//        System.out.println(result);

        Thread.sleep(3000);

        driver.quit();
    }
}
