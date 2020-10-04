package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {
    private final ObjectJSON objJSON = new ObjectJSON("{ \"string\": \"TEXT\", \"object_JSON\": { " +
                        "\"string\": \"It_too_TEXT\", \"number\": 12345 }, " +
                    "\"work?\": \"YES\" }");
    private final ObjectXML objXML = new ObjectXML("<string>TEXT</string>\n" +
                    "<object_XML>\n" +
                        "<string>It_too_TEXT</string>\n" +
                        "<nested_obj_XML>" +
                            "<key>VALUE</key>" +
                        "</nested_obj_XML>" +
                    "</object_XML>" +
                    "<work?>YES</work?>");

    ConverterTest() throws Exception {

    }

    @Test
    void toJSON() {
        assertEquals("{ \"string\": \"TEXT\", \"object_XML\": { \"string\": \"It_too_TEXT\", " +
                "\"nested_obj_XML\": { \"key\": \"VALUE\" } }, " +
                "\"work?\": \"YES\" }", Converter.toJSON(objXML).toString());
    }

    @Test
    void toXML() {
        assertEquals("<string>TEXT</string><object_JSON><string>It_too_TEXT</string><number>12345</number></object_JSON>" +
                "<work?>YES</work?>", Converter.toXML(objJSON).toString());
    }
}
