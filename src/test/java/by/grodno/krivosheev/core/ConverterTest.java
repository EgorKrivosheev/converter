package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private final ObjectJSON objJSON = new ObjectJSON();
    private final ObjectXML objXML = new ObjectXML();

    @Test
    void toJSON() {

        objXML.addKeyAndValue("byte", (byte) 5);
        objXML.addKeyAndValue("short", (short) 55);
        objXML.addKeyAndValue("int", 555);
        objXML.addKeyAndValue("long", (long) 5555);
        objXML.addKeyAndValue("float", (float) 5.5);
        objXML.addKeyAndValue("double", 5.55);
        objXML.addKeyAndValue("bool", true);
        ObjectXML subObjXML = new ObjectXML();
        subObjXML.addKeyAndValue("work?", "YES");
        objXML.addKeyAndValue("object_JSON", subObjXML);

        assertEquals("{ \"byte\": 5, \"short\": 55, \"int\": 555, \"long\": 5555," +
                " \"float\": 5.5, \"double\": 5.55, \"bool\": true, \"object_JSON\": { \"work?\": \"YES\" } }",
                Converter.toJSON(objXML).toString());

    }

    @Test
    void toXML() {

        objJSON.addKeyAndValue("string", "TEXT");
        ObjectJSON subObjJSON = new ObjectJSON();
        subObjJSON.addKeyAndValue("string", "It_too_TEXT");
        objJSON.addKeyAndValue("object_XML", subObjJSON);
        objJSON.addKeyAndValue("work?", "YES");

        assertEquals("<string>TEXT</string><object_XML><string>It_too_TEXT</string></object_XML>" +
                "<work?>YES</work?>", Converter.toXML(objJSON).toString());

    }

}
