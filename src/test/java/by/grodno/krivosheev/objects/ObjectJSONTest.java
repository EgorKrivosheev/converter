package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectJSONTest {

    private final ObjectJSON objJSON = new ObjectJSON("{ \"key\": \"VALUE\", \"number\": 123456789, \"bool\": false, " +
                    "\"object_JSON\": { \"work?\": \"YES\" } }");

    @Test
    void testToString() {

        objJSON.addKeyAndValue("add_key", true);
        assertEquals("{ \"key\": \"VALUE\", \"number\": 123456789, \"bool\": false, " +
                "\"object_JSON\": { \"work?\": \"YES\" }, \"add_key\": true }", objJSON.toString());
    }
}
