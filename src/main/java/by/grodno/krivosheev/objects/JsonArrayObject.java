package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractArrayObject;
import by.grodno.krivosheev.core.Utils;

import java.util.stream.Collectors;

public class JsonArrayObject extends AbstractArrayObject {
    public JsonArrayObject() {
        super();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return null;

        return this.getValue().stream()
                .map(obj -> Utils.classes.contains(obj.getClass()) ?
                        obj.toString() :
                        "\"" + obj + "\"")
                .collect(Collectors.joining(",", "[", "]"));
    }

}
