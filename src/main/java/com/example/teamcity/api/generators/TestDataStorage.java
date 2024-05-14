package com.example.teamcity.api.generators;

import com.example.teamcity.api.model.User;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specification;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataStorage {
    private static TestDataStorage testDataStorage;
    private final List<TestData> testDataList;
    private static Map<String, Class<?>> createdEntities;

    private TestDataStorage() {
        this.testDataList = new ArrayList<>();
    }

    public static TestDataStorage getStorage() {
        if (testDataStorage == null) {
            testDataStorage = new TestDataStorage();
            createdEntities = new HashMap<>();
        }
        return testDataStorage;
    }

    public TestData addTestData() {
        var testData = TestDataGenerator.generate();
        addTestData(testData);
        return testData;
    }

    public void addTestData(TestData testData) {
        getStorage().testDataList.add(testData);
    }

    public void addCreatedEntity(Object object) {
        java.lang.reflect.Method methodGetId;
        String id;

        try {
            methodGetId = object.getClass().getMethod("getId");
        } catch (NoSuchMethodException e) {
            try {
                methodGetId = object.getClass().getMethod("getUsername");
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            id = (String) methodGetId.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        createdEntities.put(id, object.getClass());
    }

    public void deleteCreatedEntities() {
        for (Map.Entry<String, Class<?>> entry : createdEntities.entrySet()) {
            String id = entry.getKey();
            Class<?> objectClass = entry.getValue();

            if (entry instanceof User) {
                new UncheckedBase(Specification.getSpec().superUserSpec(), objectClass).delete("username", id);
            } else {
                new UncheckedBase(Specification.getSpec().superUserSpec(), objectClass).delete(id);
            }

            System.out.println("Object id = " + id + " was deleted");
        }
        createdEntities.clear();
    }


}
