package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;
import by.grodno.krivosheev.core.Parser;
import by.grodno.krivosheev.core.SyntaxException;
import by.grodno.krivosheev.core.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class JsonObject extends AbstractObject {
    public JsonObject() {
        super();
    }

    /**
     * Construct ObjectJSON
     * @param source Not null - string JSON
     * @throws SyntaxException If the {@code source} expression's syntax is invalid
     */
    public JsonObject(@NotNull String source) throws SyntaxException {
        super(Parser.getJsonObject(source).getMap());
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return null;

        return this.getMap().keySet().stream()
            .map(key -> Constants.classes.contains(this.getObject(key).getClass()) ?
                    "\"" + key + "\":" + this.getObject(key) :
                    "\"" + key + "\":\"" + this.getObject(key) + "\"")
            .collect(Collectors.joining(",", "{", "}"));
    }
}
