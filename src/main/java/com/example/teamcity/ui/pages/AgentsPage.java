package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.sleep;

public class AgentsPage extends Page {
    private SelenideElement unauthorizedAgentsLink = element(Selectors.byHref("/agents/unauthorized"));
    private SelenideElement authorizeButton = element(By.xpath("//*[@data-test='agent']//button"));
    private SelenideElement confirmAuthorizeButton = element(By.xpath("//*[@data-test='ring-popup']//button[@type='submit']"));

    public void authorizeAgent() {
        unauthorizedAgentsLink.shouldBe(Condition.visible, Duration.ofSeconds(15));
        unauthorizedAgentsLink.click();
        authorizeButton.shouldBe(Condition.visible, Duration.ofSeconds(15));
        authorizeButton.click();
        confirmAuthorizeButton.shouldBe(Condition.visible, Duration.ofSeconds(15));
        confirmAuthorizeButton.click();
        sleep(2000);
    }
}
