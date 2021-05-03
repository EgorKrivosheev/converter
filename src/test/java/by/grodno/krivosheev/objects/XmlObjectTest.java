package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.SyntaxException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlObjectTest {
    private final XmlObject objXML = new XmlObject(
            "<byte>123</byte>" +
            "<short>-4567</short>" +
            "<integer>890123</integer>" +
            "<long>-4567890123456</long>" +
            "<array>" +
                "<element>item 1</element>" +
                "<element>2</element>" +
            "</array>" +
            "<string>789012345678901234567</string>" +
            "<object>" +
                "<boolean1>true</boolean1>" +
                "<boolean2>false</boolean2>" +
            "</object>" +
            "<float>8.901</float>" +
            "<double>-23456789012345678901234567890123456789.0</double>");

    XmlObjectTest() throws SyntaxException {

    }

    @Test
    void testToString() {
        assertEquals("<byte>123</byte><short>-4567</short><integer>890123</integer><long>-4567890123456</long><array><element>item 1</element>" +
                "<element>2</element></array><string>789012345678901234567</string><object><boolean1>true</boolean1><boolean2>false</boolean2>" +
                "</object><float>8.901</float><double>-2.3456789012345678E37</double>", objXML.toString());
    }
}
