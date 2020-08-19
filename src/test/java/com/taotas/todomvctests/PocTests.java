package com.taotas.todomvctests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PocTests {

    @Test
    public void checkingMainFeaturesTest() {
        open("http://todomvc4tasj.herokuapp.com/");
        Wait().until(ExpectedConditions
                .jsReturnsValue("return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup')"));

        // Creating tasks
        $("#new-todo").append("a").pressEnter();
        $("#new-todo").append("b").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a", "b"));

        // Editing the task (renaming)
        $$("#todo-list>li").findBy(text("b")).doubleClick();
        $$("#todo-list>li").find(cssClass("editing")).find(".edit").append("_edited").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a", "b_edited"));

        // Deleting the task
        $$("#todo-list>li").findBy(text("a")).hover().find(".destroy").click();
        $$("#todo-list>li").shouldHave(exactTexts("b_edited"));

        // Checking `Toggle All` feature + `Items left count` feature
        $("#new-todo").append("c").pressEnter();
        $("#todo-count").shouldHave(exactText("2 items left"));
        $("#toggle-all").click();
        $$("#todo-list>li").findBy(cssClass("completed")).shouldBe(visible);
        $("#todo-count").shouldHave(exactText("0 items left"));

        // `Clear completed` functionality and visibility + disappearing of `Toggle All` button
        $("#clear-completed").shouldBe(visible);
        $("#clear-completed").click();
        $("#clear-completed").shouldBe(hidden);
        $$("#todo-list>li").shouldHave(size(0));
        $("#toggle-all").shouldBe(hidden);

        // Checking `All`, `Active` and `Completed` tabs on footer
        $("#new-todo").append("d").pressEnter();
        $("#new-todo").append("e").pressEnter();
        $$("#todo-list>li").findBy(text("d")).find(".toggle").click();
        $(By.linkText("Active")).click();
        $$("#todo-list>li").shouldHave(exactTexts("", "e"));
        $(By.linkText("Completed")).click();
        $$("#todo-list>li").shouldHave(exactTexts("d", ""));
    }
}
