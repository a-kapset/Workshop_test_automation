package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.CreateProjectForm;
import com.example.teamcity.ui.elements.CreateFromUrlForm;
import com.example.teamcity.ui.elements.CreateProjectManuallyForm;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.sleep;

@Getter
public class CreateNewProjectPage extends Page {
    private SelenideElement fromUrlFormBaseElement = element(Selectors.byId("createFromUrlForm"));
    private SelenideElement projectFormBaseElement = element(Selectors.byId("createProjectForm"));
    private SelenideElement manuallyFormBaseElement = element(Selectors.byId("editProjectForm"));

    private CreateFromUrlForm createFromUrlForm = new CreateFromUrlForm(fromUrlFormBaseElement);
    private CreateProjectForm createProjectForm = new CreateProjectForm(projectFormBaseElement);
    private CreateProjectManuallyForm createManuallyForm = new CreateProjectManuallyForm(manuallyFormBaseElement);

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
        fromUrlFormBaseElement.shouldBe(Condition.visible, Duration.ofSeconds(6));

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
        manuallyFormBaseElement.shouldBe(Condition.visible, Duration.ofSeconds(6));

        createManuallyForm.getNameInput().shouldBe(Condition.visible, Duration.ofSeconds(5));
        createManuallyForm.getNameInput().sendKeys(name);
        createManuallyForm.getProjectIdInput().clear();
        createManuallyForm.getProjectIdInput().sendKeys(projectId);
    }

    public void setupUrlProject(String projectName, String buildTypeName) {
        projectFormBaseElement.shouldBe(Condition.exist, Duration.ofSeconds(30));
        projectFormBaseElement.shouldBe(Condition.visible, Duration.ofSeconds(10));

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
