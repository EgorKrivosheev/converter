package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;

import java.util.Map;

public class ObjectXML extends AbstractObject {

    public ObjectXML() {

        super();

    }

    public ObjectXML(Map<String, Object> obj) {

        super(obj);

    }

    public Map<String, Object> getMap() {

        return this.getObject();

    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        for (String key : this.getObject().keySet()) {
            builder.append("<").append(key).append(">")
                .append(this.getObject().get(key))
                    .append("</").append(key).append(">");
        }

        return builder.toString();

    }

}
