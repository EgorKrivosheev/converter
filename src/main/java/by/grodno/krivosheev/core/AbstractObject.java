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

    public AbstractObject(Map<String, Object> obj) {

        this.object = obj;

    }

    protected Map<String, Object> getObject() {

        return object;

    }

    /**
     * Added new element
     * @param key Element's key
     * @param value Element's value
     * @return Previous the element's value
     */
    public Object addKeyAndValue(String key, Object value) {

        return this.object.put(key, value);

    }

    public abstract String toString();

}
