package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private final ObjectJSON objJSON = new ObjectJSON();
    private final ObjectXML objXML = new ObjectXML("<string>TEXT</string>\n" +
                    "<object_XML>\n" +
                        "<string>It_too_TEXT</string>\n" +
                        "<nested_obj_XML>" +
                            "<key>VALUE</key>" +
                        "</nested_obj_XML>" +
                    "</object_XML>" +
                    "<work?>YES</work?>");

    @Test
    void toJSON() {

        assertEquals("{ \"string\": \"TEXT\", \"object_XML\": { \"string\": \"It_too_TEXT\", " +
                "\"nested_obj_XML\": { \"key\": \"VALUE\" } }, " +
                "\"work?\": \"YES\" }", Converter.toJSON(objXML).toString());
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
