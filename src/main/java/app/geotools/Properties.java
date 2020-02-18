package app.geotools;

import java.util.Map;

public class Properties {

    Map<String, String> properties;

    public Properties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
