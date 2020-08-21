package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectXMLTest {

    private final ObjectXML objXML = new ObjectXML();

    @Test
    void testToString() {

        objXML.addKeyAndValue("string", "TEXT");
        ObjectXML subObjXML = new ObjectXML();
        subObjXML.addKeyAndValue("string", "It_too_TEXT");
        objXML.addKeyAndValue("object_XML", subObjXML);
        objXML.addKeyAndValue("work?", "YES");

        assertEquals("<string>TEXT</string><object_XML><string>It_too_TEXT</string></object_XML>" +
                "<work?>YES</work?>", objXML.toString());

    }

}
