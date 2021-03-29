package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;
import by.grodno.krivosheev.core.Parser;
import by.grodno.krivosheev.core.SyntaxException;
import by.grodno.krivosheev.core.Utils;

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
            .map(key -> Utils.classes.contains(this.getMap().get(key).getClass()) ?
                    "\"" + key + "\":" + this.getMap().get(key) :
                    "\"" + key + "\":\"" + this.getMap().get(key) + "\"")
            .collect(Collectors.joining(",", "{", "}"));
    }

}
