package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectXMLTest {

    private final ObjectXML objXML = new ObjectXML("<string>TEXT</string><object_XML>" +
                "<string>It_too_TEXT</string></object_XML>");

    ObjectXMLTest() throws Exception {

    }

    @Test
    void testToString() {
        objXML.addKeyAndValue("work?", "YES");
        assertEquals("<string>TEXT</string><object_XML><string>It_too_TEXT</string></object_XML>" +
                "<work?>YES</work?>", objXML.toString());
    }
}
