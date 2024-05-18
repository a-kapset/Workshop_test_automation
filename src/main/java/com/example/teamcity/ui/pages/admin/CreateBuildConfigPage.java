package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.*;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class CreateBuildConfigPage extends Page {
    private CreateFromUrlForm createFromUrlForm = new CreateFromUrlForm(element(Selectors.byId("createFromUrlForm")));
    private CreateBuildForm createBuildForm = new CreateBuildForm(element(Selectors.byId("createProjectForm")));
    private CreateBuildManuallyForm createManuallyForm = new CreateBuildManuallyForm(element(Selectors.byId("editProjectForm")));
    private SelenideElement createFromUrlButton = element(Selectors.byHref("#createFromUrl"));
    private SelenideElement createManuallyButton = element(Selectors.byHref("#createManually"));

    public CreateBuildConfigPage open(String projectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" +
                projectId +
                "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateBuildConfigPage createBuildByUrl(String url) {
        createFromUrlButton.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        createFromUrlForm.getUrlInput().sendKeys(url);
        submit();
        return this;
    }

//    public void createBuildManually(String name, String projectId) {
//        setupManualProject(name, projectId);
//        submit();
//    }

//    public void setupManualBuild(String name, String projectId) {
//        createManuallyButton.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
//        createManuallyForm.getNameInput().sendKeys(name);
//        createManuallyForm.getProjectIdInput().clear();
//        createManuallyForm.getProjectIdInput().sendKeys(projectId);
//    }

    public void setupUrlBuild(String buildConfigName) {
        createBuildForm.getBuildConfigNameInput().shouldBe(Condition.visible, Duration.ofSeconds(5));
        createBuildForm.getBuildConfigNameInput().clear();
        createBuildForm.getBuildConfigNameInput().sendKeys(buildConfigName);
    }

    public void setupUrlBuildAndSubmit(String buildConfigName) {
        setupUrlBuild(buildConfigName);
        submit();
    }

    public CreateBuildConfigPage cancel() {
        createBuildForm.getCancelButton().click();
        return this;
    }
}