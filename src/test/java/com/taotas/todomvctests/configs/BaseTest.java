package com.taotas.todomvctests.configs;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideConfig;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    @BeforeEach
    public void setup() {
        Configuration.baseUrl = System.getProperty("selenide.baseUrl", "http://todomvc4tasj.herokuapp.com");
        Configuration.browser = "chrome";
        Configuration.fastSetValue = true;
    }
}
