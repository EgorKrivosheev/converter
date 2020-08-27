package by.grodno.krivosheev.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void getObjectJSON() {

        String textJSON = "{\"byte\": 5,\"short\": 55,\"int\":555, \"long\": 5555,\"float\": 5.5," +
                " \"double\": 5.55, \"bool\": true, \"object_JSON\": {\"work?\": \"YES\"}}";

        assertEquals("{ \"byte\": 5, \"short\": 55, \"int\": 555, \"long\": 5555," +
                " \"float\": 5.5, \"double\": 5.55, \"bool\": true, \"object_JSON\": { \"work?\": \"YES\" } }",
                Parser.getObjectJSON(textJSON).toString());
    }

    @Test
    void getObjectXML() {

        String textXML = "<string>TEXT</string>\n" +
                         "<object_XML>\n" +
                            "<string>It_too_TEXT</string>\n" +
                            "<nested_obj_XML>" +
                                "<key>VALUE</key>" +
                            "</nested_obj_XML>" +
                         "</object_XML>" +
                         "<work?>YES</work?>";

        assertEquals("<string>TEXT</string><object_XML><string>It_too_TEXT</string>" +
                "<nested_obj_XML><key>VALUE</key></nested_obj_XML></object_XML>" +
                "<work?>YES</work?>", Parser.getObjectXML(textXML).toString());
    }

    @Test
    void isNumeric() {

        assertTrue(Parser.isNumeric("-128"));
        assertTrue(Parser.isNumeric("123456"));
        assertTrue(Parser.isNumeric("5.5"));
        assertTrue(Parser.isNumeric("-5.55"));
        assertFalse(Parser.isNumeric("abc"));
        assertFalse(Parser.isNumeric("5,55"));
    }
}
