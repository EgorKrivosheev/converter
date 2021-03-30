package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.AbstractObject;
import by.grodno.krivosheev.core.Parser;
import by.grodno.krivosheev.core.SyntaxException;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

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

        return this.getMap().keySet().stream()
                .map(key -> "<" + key + ">" + this.getObject(key) + "</" + key + ">")
                .collect(Collectors.joining(""));
    }
}
