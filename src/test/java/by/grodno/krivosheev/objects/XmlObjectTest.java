package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.SyntaxException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlObjectTest {
    private final XmlObject objXML = new XmlObject("<string>TEXT</string><object_XML>" +
                "<string>It_too_TEXT</string></object_XML><work?>YES</work?>");

    XmlObjectTest() throws SyntaxException {

    }

    @Test
    void testToString() {
        assertEquals("<string>TEXT</string><object_XML><string>It_too_TEXT</string></object_XML>" +
                "<work?>YES</work?>", objXML.toString());
    }

}
