package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectJSONTest {

    private final ObjectJSON objJSON = new ObjectJSON("{ \"key\": \"VALUE\", \"number\": 123456789, \"bool\": false, " +
                    "\"object_JSON\": { \"work?\": \"YES\" } }");

    ObjectJSONTest() throws Exception {

    }

    @Test
    void testToString() {
        objJSON.addKeyAndValue("add_key", true);
        assertEquals("{ \"key\": \"VALUE\", \"number\": 123456789, \"bool\": false, " +
                "\"object_JSON\": { \"work?\": \"YES\" }, \"add_key\": true }", objJSON.toString());
    }
}
