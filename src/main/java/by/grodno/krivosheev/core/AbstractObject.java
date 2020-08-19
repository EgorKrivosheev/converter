package by.grodno.krivosheev.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract class for create new object
 */
public abstract class AbstractObject {

    private Map<String, Object> object;

    public AbstractObject() {

        this.object = new LinkedHashMap<>();

    }

    /**
     * Added new element
     * @param key Element's key
     * @param value Element's value
     * @return Previous the element's value
     */
    public Object AddKeyAndValue(String key, Object value) {

        return this.object.put(key, value);

    }

    public abstract String toString();

}
