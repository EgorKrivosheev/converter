package by.grodno.krivosheev.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Test
    void testJsonText() throws SyntaxException {
        assertTrue(Validator.isValidJsonText(
                "{" +
                    "\"key\": \"value\"," +
                    "\"object\": {" +
                        "\"object\": {" +
                            "\"object\": {" +
                                "\"boolean\": true" +
                            "}" +
                        "}," +
                        "\"byte\": 0" +
                    "}," +
                    "\"array\": [" +
                        "1, 2, {\"float\": 3.33}, 4, 5, \"str\"" +
                    "]" +
                "}"));

        String[] arrayErrorJsonText = {
                "{", "{\"", "{\"key\"", "{\"key\":", "\"key\": }", "{\"key\" \"value\"}", "{\"key\": \"value\",}",
                "{,", "{:", "{:}", "[", "{\"key\":[", "{\"key\": ]", "{\"key\":[ \"obj\":\"value\" ]}"
        };
        for (String errorJsonText : arrayErrorJsonText) {
            assertThrows(SyntaxException.class, () -> Validator.isValidJsonText(errorJsonText));
        }
    }

    @Test
    void testXmlText() throws SyntaxException {
        assertTrue(Validator.isValidXmlText(
                "<key>value</key>" +
                "<object>" +
                    "<object>" +
                        "<object>" +
                            "<boolean>true</boolean>" +
                        "</object>" +
                    "</object>" +
                    "<byte>0</byte>" +
                "</object>" +
                "<array>" +
                    "<element>1</element>" +
                    "<element>2</element>" +
                    "<element>" +
                        "<float>3.33</float>" +
                    "</element>" +
                    "<element>4</element>" +
                    "<element>5</element>" +
                    "<element>str</element>" +
                "</array>"));

        String[] arrayErrorXmlText = {
                ">", "/", "<", "<key>", "</key>", "<key<", "<key>value</ke>"
        };
        for (String errorXmlText : arrayErrorXmlText) {
            assertThrows(SyntaxException.class, () -> Validator.isValidXmlText(errorXmlText));
        }
    }
}
