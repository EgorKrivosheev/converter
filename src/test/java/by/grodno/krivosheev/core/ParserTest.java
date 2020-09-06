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
    void isIntNumber() {

        assertTrue(Parser.isIntNumber("1"));
        assertTrue(Parser.isIntNumber("234"));
        assertTrue(Parser.isIntNumber("-5678"));
        assertTrue(Parser.isIntNumber("-9"));
        assertFalse(Parser.isIntNumber("abc"));
        assertFalse(Parser.isIntNumber("1.2"));
        assertFalse(Parser.isIntNumber("-3,45"));
    }

    @Test
    void isDecNumber() {

        assertTrue(Parser.isDecNumber("1.2"));
        assertTrue(Parser.isDecNumber("-3.4"));
        assertTrue(Parser.isDecNumber("56.789"));
        assertTrue(Parser.isDecNumber("-901.2345"));
        assertFalse(Parser.isDecNumber("abc"));
        assertFalse(Parser.isDecNumber("1"));
        assertFalse(Parser.isDecNumber("-23"));
        assertFalse(Parser.isDecNumber("4,56"));
        assertFalse(Parser.isDecNumber("-78,9"));
    }

    @Test
    void setValue() {

        assertEquals(Byte.class, Parser.setValue("123").getClass());
        assertEquals(Short.class, Parser.setValue("-4567").getClass());
        assertEquals(Integer.class, Parser.setValue("890123").getClass());
        assertEquals(Long.class, Parser.setValue("-4567890123456").getClass());
        assertEquals(String.class, Parser.setValue("789012345678901234567").getClass());
        assertEquals(Boolean.class, Parser.setValue("true").getClass());
        assertEquals(Float.class, Parser.setValue("8.901").getClass());
        assertEquals(Double.class, Parser.setValue("-234567890123456.78901234567890123456789").getClass());
    }
}
