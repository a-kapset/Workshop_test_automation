package com.example.teamcity.ui.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

import java.time.Duration;

@Getter
public class CreateProjectForm extends PageElement {
    private SelenideElement projectNameInput;
    private SelenideElement buildTypeNameInput;
    private SelenideElement errorProjectName;
    private SelenideElement errorBuildTypeName;
    private SelenideElement cancelButton;

    public CreateProjectForm(SelenideElement element) {
        super(element);
        this.projectNameInput = findElement(Selectors.byId("projectName"));
        this.buildTypeNameInput = findElement(Selectors.byId("buildTypeName"));
        this.errorProjectName = findElement(Selectors.byId("error_projectName"));
        this.errorBuildTypeName = findElement(Selectors.byId("error_buildTypeName"));
        this.cancelButton = findElement(Selectors.byClass("btn cancel"));
    }


}
