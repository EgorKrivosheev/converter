package by.grodno.krivosheev.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract class for create new object
 */
public abstract class AbstractObject {
    private final Map<String, Object> object;

    public AbstractObject() {
        this.object = new LinkedHashMap<>();
    }

    protected AbstractObject(Map<String, Object> map) {
        this.object = map;
    }

    public String toString() {
        return null;
    }

    public boolean isEmpty() {
        return this.object.isEmpty();
    }

    /**
     * Added new element
     * @param key Not null - element's key
     * @param value Element's value
     */
    protected void addKeyAndValue(String key, Object value) {
        this.object.put(key, value);
    }

    protected Map<String, Object> getMap() {
        return object;
    }

    protected Object getObject(String key) {
        return this.object.get(key);
    }
}
