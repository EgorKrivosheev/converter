package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;
import by.grodno.krivosheev.core.Parser;

import org.jetbrains.annotations.NotNull;

public class ObjectXML extends AbstractObject {

    public ObjectXML() {

        super();
    }

    /**
     * Construct ObjectXML
     * @param source Not null - string XML
     */
    public ObjectXML(@NotNull String source) {

        super(Parser.getObjectXML(source).getMap());
    }

    @Override
    public String toString() {

        if (!this.getError().isEmpty()) return this.getError();
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
