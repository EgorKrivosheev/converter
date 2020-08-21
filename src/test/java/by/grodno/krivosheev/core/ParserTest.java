package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private final ObjectJSON objJSON = new ObjectJSON();
    private final ObjectXML objXML = new ObjectXML();

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
                         "</object_XML>";

        assertEquals("<string>TEXT</string><object_XML><string>It_too_TEXT</string></object_XML>" +
                "<work?>YES</work?>", Parser.getObjectXML(textXML).toString());

    }
}
