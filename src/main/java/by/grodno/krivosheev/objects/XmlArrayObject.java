package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractArrayObject;

import java.util.stream.Collectors;

public class XmlArrayObject extends AbstractArrayObject {
    public XmlArrayObject() {
        super();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return null;

        return this.getValue().stream()
                .map(obj -> "<element>" + obj + "</element>")
                .collect(Collectors.joining(""));
    }
}
