package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;
import by.grodno.krivosheev.core.Parser;
import by.grodno.krivosheev.core.SyntaxException;

import org.jetbrains.annotations.NotNull;

public class XmlObject extends AbstractObject {
    public XmlObject() {
        super();
    }

    /**
     * Construct ObjectXML
     * @param source Not null - string XML
     * @throws SyntaxException If the {@code source} expression's syntax is invalid
     */
    public XmlObject(@NotNull String source) throws SyntaxException {
        super(Parser.getXmlObject(source).getMap());
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return null;

        StringBuilder builder = new StringBuilder();
        for (String key : this.getMap().keySet()) {
            builder.append("<").append(key).append(">")
                .append(this.getMap().get(key))
                    .append("</").append(key).append(">");
        }
        return builder.toString();
    }

}
