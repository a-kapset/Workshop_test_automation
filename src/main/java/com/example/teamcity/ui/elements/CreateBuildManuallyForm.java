package com.example.teamcity.ui.elements;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class CreateBuildManuallyForm extends PageElement {
    private SelenideElement buildConfigNameInput;
    private SelenideElement buildConfigIdInput;
    private SelenideElement errorBuildTypeName;
    private SelenideElement errorBuildTypeExternalId;

    public CreateBuildManuallyForm(SelenideElement element) {
        super(element);
        this.buildConfigNameInput = findElement(Selectors.byId("buildTypeName"));
        this.buildConfigIdInput = findElement(Selectors.byId("buildTypeExternalId"));
        this.errorBuildTypeName = findElement(Selectors.byId("error_buildTypeName"));
        this.errorBuildTypeExternalId = findElement(Selectors.byId("error_buildTypeExternalId"));
    }
}
