package by.grodno.krivosheev.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class for create new array object
 */
public abstract class AbstractArrayObject {
    private final List<Object> value;

    public AbstractArrayObject() {
        this.value = new LinkedList<>();
    }

    public void add(Object obj) {
        this.value.add(obj);
    }

    protected List<Object> getValue() {
        return this.value;
    }

    public abstract String toString();

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

}
