package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class CreateProjectManuallyForm extends PageElement {
    private SelenideElement nameInput;
    private SelenideElement projectIdInput;
    private SelenideElement errorName;
    private SelenideElement errorProjectId;


    public CreateProjectManuallyForm(SelenideElement element) {
        super(element);
        this.nameInput = findElement(Selectors.byId("name"));
        this.projectIdInput = findElement(Selectors.byId("externalId"));
        this.errorName = findElement(Selectors.byId("errorName"));
        this.errorProjectId = findElement(Selectors.byId("errorExternalId"));
    }
}