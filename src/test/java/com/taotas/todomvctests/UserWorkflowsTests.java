package com.taotas.todomvctests;

import com.codeborne.selenide.ElementsCollection;
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
        getTaskList().shouldHave(exactTexts("a", "b", "c"));

        edit("a", " edited");
        complete("a edited");

        clearCompleted();
        getTaskList().shouldHave(exactTexts("b", "c"));

        cancelEditing("c", " cancel editing");

        delete("c");
        getTaskList().shouldHave(exactTexts("b"));
    }


    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private void edit(String task, String text) {
        getTaskList().findBy(exactText(task)).doubleClick();
        getTaskList().find(cssClass("editing")).find(".edit").append(text).pressEnter();
    }

    private void complete(String task) {
        getTaskList().findBy(exactText(task)).find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void cancelEditing(String task, String text) {
        getTaskList().findBy(exactText(task)).doubleClick();
        getTaskList().find(cssClass("editing")).find(".edit").append(text).pressEscape();
    }

    private void delete(String task) {
        getTaskList().findBy(exactText(task)).hover().find(".destroy").click();
    }

    private ElementsCollection getTaskList() {
        return $$("#todo-list>li");
    }
}