package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;

public class ObjectXML extends AbstractObject {

    public ObjectXML() {

        super();
    }

    @Override
    public String toString() {

        if (this.getMap().isEmpty()) return "Empty object!";

        StringBuilder builder = new StringBuilder();

        for (String key : this.getMap().keySet()) {
            builder.append("<").append(key).append(">")
                .append(this.getMap().get(key))
                    .append("</").append(key).append(">");
        }

        return builder.toString();
    }
}
