package com.taotas.todomvctests.configs;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class AtTodoMvcWithClearedStorageAfterEachTest extends BaseTest {

    @BeforeAll
    public static void loadApp() {
        Selenide.open("/");
        Wait().until(jsReturnsValue("return $._data($('#clear-completed').get(0), 'events')" +
                ".hasOwnProperty('click')"));
    }

    @BeforeEach
    public void clearStorage() {
        Selenide.clearBrowserLocalStorage();
        Selenide.refresh();
    }
}


// Second version:
    /*
    @BeforeEach
    public void loadApp() {
        //if (!$("#todoapp").is(visible))
        //if (!Selenide.title().contains("TodoMVC"))
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.clearBrowserLocalStorage();
        }
        open("/");
        Wait().until(jsReturnsValue("return $._data($('#clear-completed').get(0), 'events')" +
                ".hasOwnProperty('click')"));
    }
    */
