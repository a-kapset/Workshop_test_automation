package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.*;

public abstract class Page {
    private SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring-loader"));
    protected SelenideElement submitButton = element(Selectors.byType("submit"));
    private SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));

    protected void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    protected void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(Condition.visible, Duration.ofSeconds(60));
    }

    protected void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(Condition.visible, Duration.ofSeconds(60));
    }

    protected <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();

        collection.forEach(webElement -> elements.add(creator.apply(webElement)));

        return elements;
    }

    protected void waitForAjax() {
        while (true) {
            Boolean ajaxComplete = executeJavaScript("return jQuery.active === 0");
            if (Boolean.TRUE.equals(ajaxComplete)) {
                break;
            }
            sleep(100);
        }
    }
}