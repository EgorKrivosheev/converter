package by.grodno.krivosheev.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testJsonText() throws SyntaxException {

        assertTrue(Validator.isValidJsonText("{\"int\":123, \"arr\":[true, 12345, {\"key\":\"value\"}], " +
                "\"obj\":{\"key\":\"str\"}}"));

        String[] arrayErrorJsonText = {
                "{", "{\"", "{\"key\"", "{\"key\":", "\"key\": }", "{\"key\" \"value\"}", "{\"key\": \"value\",}",
                "{,", "[", "{\"key\":[", "{\"key\": ]", "{\"key\":[ \"obj\":\"value\" ]}"
        };

        for (String errorJsonText : arrayErrorJsonText) {
            assertThrows(SyntaxException.class, () -> Validator.isValidJsonText(errorJsonText));
        }

    }

    @Test
    void testXmlText() throws SyntaxException {

        assertTrue(Validator.isValidXmlText("<key>VALUE</key>"));

        String[] arrayErrorXmlText = {
                "<", "<Key", "<key>", "<key>value", "<key>VALUE</key1>", "<key><key2>VALUE</key>"
        };

        for (String errorXmlText : arrayErrorXmlText) {
            assertThrows(SyntaxException.class, () -> Validator.isValidXmlText(errorXmlText));
        }

    }

}
