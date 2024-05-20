package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class BuildStepsConfigurationPage extends Page {
    private SelenideElement messageBuildConfigCreated = element(Selectors.byId("unprocessed_objectsCreated"));
}
