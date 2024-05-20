package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class CreateFromUrlForm extends PageElement {
    private SelenideElement urlInput;
    private SelenideElement errorUrlMessage;
    
    public CreateFromUrlForm(SelenideElement element) {
        super(element);
        this.urlInput = findElement(Selectors.byId("url"));
        this.errorUrlMessage = findElement(Selectors.byId("error_url"));
    }
}
