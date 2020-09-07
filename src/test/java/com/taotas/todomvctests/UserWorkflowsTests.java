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
        shouldBeTasks("a", "b", "c");

        editMode("a", " edited").pressEnter();
        complete("a edited");

        clearCompleted();
        shouldBeTasks("b", "c");

        editMode("c", "cancel editing").pressEscape();

        delete("c");
        shouldBeTasks("b");
    }


    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private SelenideElement editMode(String task, String text) {
        searchByText(task).doubleClick();
        return getTaskList().find(cssClass("editing")).find(".edit").append(text);
    }

    private void complete(String task) {
        searchByText(task).find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void delete(String task) {
        searchByText(task).hover().find(".destroy").click();
    }

    private ElementsCollection getTaskList() {
        return $$("#todo-list>li");
    }

    private void shouldBeTasks(String... texts) {
        getTaskList().shouldHave(exactTexts(texts));
    }

    private SelenideElement searchByText(String task) {
        return getTaskList().findBy(exactText(task));
    }
}