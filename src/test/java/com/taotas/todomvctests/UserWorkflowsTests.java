package com.taotas.todomvctests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class UserWorkflowsTests {

    private ElementsCollection todoList = $$("#todo-list>li");

    @Test
    public void commonTasksManagement() {
        open("http://todomvc4tasj.herokuapp.com/");
        Wait().until(jsReturnsValue(
                "return $._data($('#clear-completed').get(0), 'events')" +
                        ".hasOwnProperty('click')"));

        add("a", "b", "c");
        todosShouldBe("a", "b", "c");

        edit("a", " edited");

        complete("a edited");

        clearCompleted();
        todosShouldBe("b", "c");

        cancelEditing("c", "cancel editing");

        delete("c");
        todosShouldBe("b");
    }


    private void add(String... texts) {
        for (String text : texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private void edit(String text, String textToEdit) {
        todoBy(exactText(text)).doubleClick();
        todoBy(cssClass("editing")).find(".edit").append(textToEdit)
                .pressEnter();
    }

    private void complete(String text) {
        todoBy(exactText(text)).find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void cancelEditing(String text, String textToCancel) {
        todoBy(exactText(text)).doubleClick();
        todoBy(cssClass("editing")).find(".edit").append(textToCancel)
                .pressEscape();
    }

    private void delete(String text) {
        todoBy(exactText(text)).hover().find(".destroy").click();
    }

    private void todosShouldBe(String... texts) {
        this.todoList.shouldHave(exactTexts(texts));
    }

    private SelenideElement todoBy(Condition condition) {
        return this.todoList.findBy(condition);
    }
}