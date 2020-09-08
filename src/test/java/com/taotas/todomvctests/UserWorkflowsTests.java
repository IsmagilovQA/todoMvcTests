package com.taotas.todomvctests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class UserWorkflowsTests {

    @Test
    public void commonTasksManagement() {
        open("http://todomvc4tasj.herokuapp.com/");
        Wait().until(ExpectedConditions
                .jsReturnsValue("return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup')"));

        add("a", "b", "c");
        todosShouldBe("a", "b", "c");

        startEdit("a", " edited").pressEnter();
        todo("a edited").find(".toggle").click();

        $("#clear-completed").click();
        todosShouldBe("b", "c");

        startEdit("c", "cancel editing").pressEscape();

        todo("c").hover().find(".destroy").click();
        todosShouldBe("b");
    }


    private void add(String... texts) {
        for (String text : texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private SelenideElement startEdit(String text, String appendText) {
        todo(text).doubleClick();
        return todos().find(cssClass("editing")).find(".edit").append(appendText);
    }

    private ElementsCollection todos() {
        return $$("#todo-list>li");
    }

    private void todosShouldBe(String... texts) {
        todos().shouldHave(exactTexts(texts));
    }

    private SelenideElement todo(String text) {
        return todos().findBy(exactText(text));
    }
}