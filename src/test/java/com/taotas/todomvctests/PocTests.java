package com.taotas.todomvctests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PocTests {

    @Test
    public void checkCrudTest() {
        open("http://todomvc4tasj.herokuapp.com/");
        Wait().until(ExpectedConditions
                .jsReturnsValue("return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup')"));

        // Create
        $("#new-todo").append("a").pressEnter();
        $("#new-todo").append("b").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a", "b"));

        // Edit
        $$("#todo-list>li").findBy(text("b")).doubleClick();
        $$("#todo-list>li").find(cssClass("editing")).find(".edit").append("_edited").pressEnter();

        // Delete
        $$("#todo-list>li").findBy(text("b_edited")).hover().find(".destroy").click();
        $$("#todo-list>li").shouldHave(exactTexts("a"));
    }
}
