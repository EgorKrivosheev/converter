package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonObject;
import by.grodno.krivosheev.objects.XmlObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {
    private final JsonObject jsonObj = new JsonObject("{ \"string\": \"TEXT\", \"object_JSON\": { " +
            "\"string\": \"It_too_TEXT\", \"number\": 12345 }, \"work?\": \"YES\" }");
    private final XmlObject xmlObj = new XmlObject("<string>TEXT</string><object_XML>" +
            "<string>It_too_TEXT</string><nested_obj_XML><key>VALUE</key></nested_obj_XML></object_XML>" +
            "<work?>YES</work?>");

    ConverterTest() throws SyntaxException {

    }

    @Test
    void testXmlToJson() {
        assertEquals("{\"string\":\"TEXT\",\"object_XML\":{\"string\":\"It_too_TEXT\"," +
                "\"nested_obj_XML\":{\"key\":\"VALUE\"}},\"work?\":\"YES\"}",
                Converter.xmlToJson(xmlObj).toString());
    }

    @Test
    void testJsonToXml() {
        assertEquals("<string>TEXT</string><object_JSON><string>It_too_TEXT</string><number>12345</number>" +
                "</object_JSON><work?>YES</work?>", Converter.jsonToXml(jsonObj).toString());
    }

}
