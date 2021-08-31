package com.taotas.todomvctests.configs;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.Wait;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class AtTodoMvcWithClearedStorageAfterEachTest extends BaseTest {

    @BeforeEach
    public void loadApp() {
        Selenide.open("/");
        Wait().until(jsReturnsValue("return $._data($('#clear-completed').get(0), 'events')" +
                ".hasOwnProperty('click')"));
    }

    @AfterEach
    public void clearData() {
        Selenide.clearBrowserLocalStorage();
    }
}
