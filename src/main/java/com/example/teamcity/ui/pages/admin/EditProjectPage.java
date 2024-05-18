package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class EditProjectPage extends Page {
    private SelenideElement messageProjectCreated = element(Selectors.byId("message_projectCreated"));
    private SelenideElement nameInput = element(Selectors.byId("name"));
    private SelenideElement projectIdInput = element(Selectors.byId("externalId"));
}
