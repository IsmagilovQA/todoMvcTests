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

    @Test
    public void commonTasksManagement() {
        open("http://todomvc4tasj.herokuapp.com/");
        Wait().until(jsReturnsValue(
                "return $._data($('#clear-completed').get(0), 'events')" +
                        ".hasOwnProperty('click')"));

        // Create
        add("a", "b", "c");
        todosShouldBe("a", "b", "c");

        // Edit
        startEdit("a", " edited").pressEnter();

        // Complete
        todo("a edited").find(".toggle").click();

        // Clear Completed
        $("#clear-completed").click();
        todosShouldBe("b", "c");

        // Cancel editing
        startEdit("c", "cancel editing").pressEscape();

        // Delete
        todo("c").hover().find(".destroy").click();
        todosShouldBe("b");
    }


    private void add(String... texts) {
        for (String text : texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private SelenideElement startEdit(String text, String textToAdd) {
        todo(text).doubleClick();
        return todoBy(cssClass("editing")).find(".edit").append(textToAdd);
    }

    private ElementsCollection todos() {
        return $$("#todo-list>li");
    }

    private void todosShouldBe(String... texts) {
        todos().shouldHave(exactTexts(texts));
    }

    private SelenideElement todo(String text) {
        return todoBy(exactText(text));
    }

    private SelenideElement todoBy(Condition condition) {
        return todos().findBy(condition);
    }
}