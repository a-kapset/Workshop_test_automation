package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.CreateProjectForm;
import com.example.teamcity.ui.elements.CreateProjectFromUrlForm;
import com.example.teamcity.ui.elements.CreateProjectManuallyForm;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class CreateNewProjectPage extends Page {
    private CreateProjectFromUrlForm createFromUrlForm = new CreateProjectFromUrlForm(element(Selectors.byId("createFromUrlForm")));
    private CreateProjectForm createProjectForm = new CreateProjectForm(element(Selectors.byId("createProjectForm")));
    private CreateProjectManuallyForm createManuallyForm = new CreateProjectManuallyForm(element(Selectors.byId("editProjectForm")));
    private SelenideElement createFromUrlButton = element(Selectors.byHref("#createFromUrl"));
    private SelenideElement createManuallyButton = element(Selectors.byHref("#createManually"));

    public CreateNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" +
                parentProjectId +
                "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProjectPage createProjectByUrl(String url) {
        createFromUrlButton.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        createFromUrlForm.getUrlInput().sendKeys(url);
        submit();
        return this;
    }

    public void createProjectManually(String name, String projectId) {
        setupManualProject(name, projectId);
        submit();
    }

    public void setupManualProject(String name, String projectId) {
        createManuallyButton.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        createManuallyForm.getNameInput().sendKeys(name);
        createManuallyForm.getProjectIdInput().clear();
        createManuallyForm.getProjectIdInput().sendKeys(projectId);
    }

    public void setupUrlProject(String projectName, String buildTypeName) {
        createProjectForm.getProjectNameInput().shouldBe(Condition.visible, Duration.ofSeconds(5));
        createProjectForm.getProjectNameInput().clear();
        createProjectForm.getProjectNameInput().sendKeys(projectName);
        createProjectForm.getBuildTypeNameInput().clear();
        createProjectForm.getBuildTypeNameInput().sendKeys(buildTypeName);
    }

    public void setupUrlProjectAndSubmit(String projectName, String buildTypeName) {
        setupUrlProject(projectName, buildTypeName);
        submit();
    }

    public CreateNewProjectPage cancel() {
        createProjectForm.getCancelButton().click();
        return this;
    }
}
