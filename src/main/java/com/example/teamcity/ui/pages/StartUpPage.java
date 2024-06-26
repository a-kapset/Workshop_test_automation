package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.sleep;

@Getter
public class StartUpPage extends Page {
    private SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private SelenideElement restoreFromBackupButton = element(Selectors.byId("restoreButton"));
    private SelenideElement acceptLicenseChBox = element(Selectors.byId("accept"));
    private SelenideElement header = element(Selectors.byId("header"));
    private SelenideElement loginAsSuperUserLink = element(Selectors.byHref("/login.html?super=1"));
    private SelenideElement superUserTokenInput = element(Selectors.byId("password"));



    public StartUpPage open() {
        Selenide.open("/");
        return this;
    }

    public StartUpPage setupTeamcityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLicenseChBox.shouldBe(Condition.enabled, Duration.ofMinutes(5));
        acceptLicenseChBox.scrollTo();
        acceptLicenseChBox.click();
        submitButton.click();

        return this;
    }

    public void loginAsSuperUser() {
        loginAsSuperUserLink.shouldBe(Condition.visible, Duration.ofSeconds(20));
        loginAsSuperUserLink.click();
        sleep(2000);
        superUserTokenInput.shouldBe(Condition.visible, Duration.ofSeconds(5));
        superUserTokenInput.sendKeys(Config.getProperty("superUserToken"));
        submit();
    }
}
