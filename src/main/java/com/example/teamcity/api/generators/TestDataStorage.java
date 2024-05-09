package com.example.teamcity.api.generators;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private List<TestData> generatedTestDataList;
    private List<Runnable> deletionTasks;

    private TestDataStorage() {
        generatedTestDataList = new ArrayList<>();
        deletionTasks = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
        }

        return testDataStorage;
    }

    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        addTestData(testData);
        return testData;
    }

    public TestData addTestData(TestData testData) {
        getStorage().generatedTestDataList.add(testData);
        return testData;
    }

    public void addToDeletionTasks(Response response, Runnable run) {
        if (response.statusCode() == HttpStatus.SC_CREATED || response.statusCode() == HttpStatus.SC_OK) {
            getStorage().deletionTasks.add(run);
        };
    }

    public void delete() {
        generatedTestDataList.forEach(TestData::delete);
    }

    public void deleteOnlyCreated() {
        for (Runnable task : deletionTasks) {
            task.run();
        }
    }

    // TODO add public method to access testDataList ?
}
