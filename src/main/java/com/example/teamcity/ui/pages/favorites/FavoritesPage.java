package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.sleep;

public class FavoritesPage extends Page {
    private SelenideElement header = element(Selectors.byClass("ProjectPageHeader__title--ih"));
    private SelenideElement ringLoaderInline = element(Selectors.byDataTest("ring-loader-inline"));
    private SelenideElement loadingWarningMark = element(Selectors.byId("#loadingWarning"));

    protected void waitUntilFavoritePageIsLoaded() {
        waitUntilPageIsLoaded();
        waitForAjax();
        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
        sleep(3000);
    }
}
