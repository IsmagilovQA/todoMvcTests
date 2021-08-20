package com.taotas.todomvctests;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class UserWorkflowsTests {

    private ElementsCollection todos = $$("#todo-list>li");

    @Test
    public void commonTasksManagement() {
        Configuration.fastSetValue = true;

        openApp();

        add("a", "b", "c");
        todosShouldBe("a", "b", "c");

        edit("a", "a edited");

        toggle("a edited");

        clearCompleted();
        todosShouldBe("b", "c");

        cancelEditing("c", "cancel editing");

        delete("c");
        todosShouldBe("b");
    }


    private void openApp() {
        Selenide.open("http://todomvc4tasj.herokuapp.com/");
        Wait().until(jsReturnsValue(
                "return $._data($('#clear-completed').get(0), 'events')" +
                        ".hasOwnProperty('click')"));
    }


    private void add(String... texts) {
        for (String text : texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private void edit(String textToEdit, String newText) {
        todoBy(exactText(textToEdit)).doubleClick();
        todoBy(cssClass("editing")).find(".edit").setValue(newText)
                .pressEnter();
    }

    private void toggle(String text) {
        todoBy(exactText(text)).find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void cancelEditing(String textToEdit, String textToCancel) {
        todoBy(exactText(textToEdit)).doubleClick();
        todoBy(cssClass("editing")).find(".edit").setValue(textToCancel)
                .pressEscape();
    }

    private void delete(String text) {
        todoBy(exactText(text)).hover().find(".destroy").click();
    }

    private void todosShouldBe(String... texts) {
        this.todos.shouldHave(exactTexts(texts));
    }

    private SelenideElement todoBy(Condition condition) {
        return this.todos.findBy(condition);
    }
}