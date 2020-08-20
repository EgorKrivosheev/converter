package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;

import java.util.Map;
import java.util.stream.Collectors;

public class ObjectJSON extends AbstractObject {

    public ObjectJSON() {

        super();

    }

    public ObjectJSON(Map<String, Object> obj) {

        super(obj);

    }

    public Map<String, Object> getMap() {

        return this.getObject();

    }

    @Override
    public String toString() {

        return this.getObject().keySet().stream()
            .map(key -> this.getObject().get(key).getClass() != ObjectJSON.class &&
                    this.getObject().get(key).getClass() != Integer.class &&
                    this.getObject().get(key).getClass() != Boolean.class &&
                    this.getObject().get(key).getClass() != Double.class ?
                    "\"" + key + "\": \"" + this.getObject().get(key) + "\"" :
                    "\"" + key + "\": " + this.getObject().get(key))
            .collect(Collectors.joining(", ", "{ ", " }"));

    }

}
