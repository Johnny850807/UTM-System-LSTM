package app.geotools;

import java.util.HashMap;
import java.util.Map;

public class Properties {

    Map<String, String> properties;

    public Properties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Properties(String key,String value) {
        this.properties = new HashMap<>();
        this.properties.put(key, value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
