package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonObject;
import by.grodno.krivosheev.objects.XmlObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {
    private final JsonObject jsonObj = new JsonObject("{" +
                "\"key\": \"value\"," +
                "\"object\": {" +
                    "\"boolean\": true" +
                "}," +
                "\"array\": [" +
                    "1, {\"float\": 2.3}, \"str\"" +
                "]" +
            "}");
    private final XmlObject xmlObj = new XmlObject("" +
            "<key>value</key>" +
            "<object>" +
                "<boolean>true</boolean>" +
            "</object>");

    ConverterTest() throws SyntaxException {

    }

    @Test
    void testXmlToJson() {
        assertEquals("{\"key\":\"value\",\"object\":{\"boolean\":\"true\"}}",
                Converter.xmlToJson(xmlObj).toString());
    }

    @Test
    void testJsonToXml() {
        assertEquals("<key>value</key><object><boolean>true</boolean></object><array><element>1</element>" +
                "<element><float>2.3</float></element><element>str</element></array>",
                Converter.jsonToXml(jsonObj).toString());
    }
}
