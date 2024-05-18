package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class CreateProjectFromUrlForm extends PageElement {
    private SelenideElement urlInput;
    private SelenideElement errorUrlMessage;
    
    public CreateProjectFromUrlForm(SelenideElement element) {
        super(element);
        this.urlInput = findElement(Selectors.byId("url"));
        this.errorUrlMessage = findElement(Selectors.byId("error_url"));
    }
}
