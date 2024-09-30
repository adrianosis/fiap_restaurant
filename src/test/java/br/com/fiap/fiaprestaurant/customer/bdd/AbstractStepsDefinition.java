package br.com.fiap.fiaprestaurant.customer.bdd;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStepsDefinition {

    // HashMap to hold shared data between step definitions
    protected static Map<String, String> sharedData;

    protected Map<String, String> getSharedData() {
        if (sharedData == null) {
            sharedData = new HashMap<>();
        }
        return sharedData;
    }

    public static void setSharedData(String key, String value) {
        if (sharedData == null) {
            sharedData = new HashMap<>();
        }
        sharedData.put(key, value);
    }

    public String getSharedDataValue(String key) {
        if (sharedData == null) {
            sharedData = new HashMap<>();
        }
        return sharedData.get(key);
    }

    public void removeSharedData(String key) {
        if (sharedData != null) {
            sharedData.remove(key);
        }
    }

    public void clearSharedData() {
        if (sharedData != null) {
            sharedData.clear();
        }
    }
}
