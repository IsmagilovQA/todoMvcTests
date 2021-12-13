package com.taotas.todomvctests.configs;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    {
        Configuration.baseUrl = System.getProperty("selenide.baseUrl", "http://todomvc4tasj.herokuapp.com");
        Configuration.browser = "chrome";
        Configuration.fastSetValue = true;
    }
}
