package com.taotas.todomvctests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.taotas.todomvctests.configs.BaseTest;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class TodoMvcTests extends BaseTest {

    private ElementsCollection todos = $$("#todo-list>li");

    @Test
    public void basicTodosActions() {
        givenAppOpened();

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


    @Test
    public void filterTodos() {
        givenAppOpenedWith("a", "b", "c");
        toggle("b");

        filterActive();
        todosShouldBe("a", "c");

        filterCompleted();
        todosShouldBe("b");

        filterAll();
        todosShouldBe("a", "b", "c");
    }


    public void givenAppOpenedWith(String... todos) {
        givenAppOpened();
        add(todos);
    }


    public void givenAppOpened() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.clearBrowserLocalStorage();
        }
        openApp();
    }

    public void openApp() {
        open("/");
        Wait().until(jsReturnsValue("return $._data($('#clear-completed').get(0), 'events')" +
                ".hasOwnProperty('click')"));
    }

    private void add(String... texts) {
        for (String text : texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }

    private void edit(String textToEdit, String newText) {
        startEditing(textToEdit, newText).pressEnter();
    }

    private SelenideElement startEditing(String textToEdit, String newText) {
        todo(textToEdit).doubleClick();
        return this.todos.findBy(cssClass("editing")).find(".edit").setValue(newText);
    }

    private void cancelEditing(String textToEdit, String textToCancel) {
        startEditing(textToEdit, textToCancel).pressEscape();
    }

    private void toggle(String text) {
        todo(text).find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void delete(String text) {
        todo(text).hover().find(".destroy").click();
    }

    private void todosShouldBe(String... texts) {
        this.todos.filterBy(visible).shouldHave(exactTexts(texts));
    }

    private SelenideElement todo(String text) {
        return this.todos.findBy(exactText(text));
    }

    private void filterAll() {
        $("[href='#/']").click();
    }

    private void filterActive() {
        $("[href='#/active']").click();
    }

    private void filterCompleted() {
        $("[href='#/completed']").click();
    }
}