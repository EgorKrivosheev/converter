package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;
import by.grodno.krivosheev.core.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class ObjectJSON extends AbstractObject {

    public ObjectJSON() {

        super();
    }

    /**
     * Construct ObjectJSON
     * @param source Not null - string JSON
     */
    public ObjectJSON(@NotNull String source) {

        super(Parser.getObjectJSON(source).getMap());
    }

    @Override
    public String toString() {

        if (!this.getError().isEmpty()) return this.getError();
        if (this.getMap().isEmpty()) return "Empty object!";

        return this.getMap().keySet().stream()
            .map(key -> this.getMap().get(key).getClass() != ObjectJSON.class &&
                        this.getMap().get(key).getClass() != Byte.class &&
                        this.getMap().get(key).getClass() != Short.class &&
                        this.getMap().get(key).getClass() != Integer.class &&
                        this.getMap().get(key).getClass() != Long.class &&
                        this.getMap().get(key).getClass() != Float.class &&
                        this.getMap().get(key).getClass() != Double.class &&
                        this.getMap().get(key).getClass() != Boolean.class ?
                    "\"" + key + "\": \"" + this.getMap().get(key) + "\"" :
                    "\"" + key + "\": " + this.getMap().get(key))
            .collect(Collectors.joining(", ", "{ ", " }"));
    }
}
