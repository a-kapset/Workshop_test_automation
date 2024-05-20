package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class CreateBuildForm extends PageElement {
    private SelenideElement buildConfigNameInput;
    private SelenideElement cancelButton;
    private SelenideElement errorBuildTypeName;

    public CreateBuildForm(SelenideElement element) {
        super(element);
        this.buildConfigNameInput = findElement(Selectors.byId("buildTypeName"));
        this.cancelButton = findElement(Selectors.byClass("btn cancel"));
        this.errorBuildTypeName = findElement(Selectors.byId("error_buildTypeName"));
    }
}
