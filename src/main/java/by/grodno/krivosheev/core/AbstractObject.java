package by.grodno.krivosheev.core;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract class for create new object
 */
public abstract class AbstractObject {

    private final Map<String, Object> object;
    private String error = "";

    public AbstractObject() {

        this.object = new LinkedHashMap<>();
    }

    protected AbstractObject(Map<String, Object> map) {

        this.object = map;
    }

    protected Map<String, Object> getMap() {

        return object;
    }

    public void setError(String str) {

        this.error = str;
    }

    protected String getError() {

        return error;
    }

    /**
     * Added new element
     * @param key Not null - element's key
     * @param value Element's value
     * @return Previous the element's value
     */
    public Object addKeyAndValue(@NotNull String key, Object value) {

        return this.object.put(key, value);
    }

    public abstract String toString();
}
