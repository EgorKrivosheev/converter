package by.grodno.krivosheev.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void getJsonObject() throws SyntaxException {
        String jsonText =
                "{" +
                    "\"json_obj\": {" +
                        "\"str\": \"STRING 1\"," +
                        "\"json_obj\": {" +
                            "\"str\": \"STRING 2\"," +
                            "\"json_obj\": {" +
                                "\"number\": 1," +
                                "\"boolean\": true," +
                                "\"array\": [" +
                                    "\"item 1\", \"item 2\", {\"obj item 3\": \"STRING 3\"}" +
                                "]" +
                            "}" +
                        "}" +
                    "}" +
                "}";

        assertEquals("{\"json_obj\":{\"str\":\"STRING 1\",\"json_obj\":{\"str\":\"STRING 2\",\"json_obj\":{\"number\":1,\"boolean\":true,\"array\":" +
                "[\"item 1\",\"item 2\",{\"obj item 3\":\"STRING 3\"}]}}}}", Parser.getJsonObject(jsonText).toString());
    }

    @Test
    void getXmlObject() throws SyntaxException {
        String xmlText =
                "<xml_obj>" +
                    "<str>STRING 1</str>" +
                    "<xml_obj>" +
                        "<str>STRING 2</str>" +
                        "<xml_obj>" +
                            "<number>1</number>" +
                            "<boolean>true</boolean>" +
                            "<array>" +
                                "<element>item 1</element>" +
                                "<element>item 2</element>" +
                                "<element>" +
                                    "<obj item 3>STRING 3</obj item 3>" +
                                "</element>" +
                            "</array>" +
                        "</xml_obj>" +
                    "</xml_obj>" +
                "</xml_obj>";

        assertEquals("<xml_obj><str>STRING 1</str><xml_obj><str>STRING 2</str><xml_obj><number>1</number><boolean>true</boolean><array>" +
                "<element>item 1</element><element>item 2</element><element><obj item 3>STRING 3</obj item 3></element></array></xml_obj></xml_obj>" +
                "</xml_obj>", Parser.getXmlObject(xmlText).toString());
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
