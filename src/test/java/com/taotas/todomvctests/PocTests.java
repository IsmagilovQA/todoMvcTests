package com.taotas.todomvctests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
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
        $("#new-todo").append("c").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a", "b", "c"));

        // Edit
        $$("#todo-list>li").findBy(exactText("a")).doubleClick();
        $$("#todo-list>li").find(cssClass("editing")).find(".edit").append("_edited").pressEnter();

        // Delete by 'Clear completed' button
        $$("#todo-list>li").findBy(exactText("a_edited")).find(".toggle").click();
        $("#clear-completed").click();
        $$("#todo-list>li").shouldHave(exactTexts("b", "c"));

        // Edit with canceling
        $$("#todo-list>li").findBy(exactText("c")).doubleClick();
        $$("#todo-list>li").find(cssClass("editing")).find(".edit").append("_shouldn't be saved")
                .pressEscape();

        // Delete by 'x' button
        $$("#todo-list>li").findBy(exactText("c")).hover().find(".destroy").click();
        $$("#todo-list>li").shouldHave(exactTexts("b"));
    }
}
