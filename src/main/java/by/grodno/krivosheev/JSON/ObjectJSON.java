package by.grodno.krivosheev.JSON;

import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectJSON {

    private Map<String, Object> json;

    public ObjectJSON() {

        this.json = new LinkedHashMap<>();

    }

    /**
     * Added new element
     * @param key Element's key
     * @param value Element's value
     * @return Previous the element's value
     */
    public Object AddKeyAndValue(String key, Object value) {

        return this.json.put(key, value);

    }
}
