package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class CreateNewProjectPage extends Page {
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement errorUrlMessage = element(Selectors.byId("error_url"));
    private SelenideElement errorProjectName = element(Selectors.byId("error_projectName"));
    private SelenideElement errorBuildTypeName = element(Selectors.byId("error_buildTypeName"));
    private SelenideElement cancelButton = element(Selectors.byClass("btn cancel"));

    public CreateNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" +
                parentProjectId +
                "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProjectPage createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.shouldBe(Condition.visible, Duration.ofSeconds(60));
        projectNameInput.clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
    }

    public void setupProjectAndSubmit(String projectName, String buildTypeName) {
        setupProject(projectName, buildTypeName);
        submit();
    }

    public CreateNewProjectPage cancel() {
        cancelButton.click();
        return this;
    }
}
