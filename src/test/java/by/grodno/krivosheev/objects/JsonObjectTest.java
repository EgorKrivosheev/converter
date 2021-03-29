package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.SyntaxException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectTest {
    private final JsonObject jsonObj = new JsonObject("{ \"key\": \"VALUE\", \"number\": 123456789, " +
            "\"bool\": false, \"object_JSON\": { \"work?\": YES }, \"array\": [ 1, \"str\", 3 ], \"add_key\": true }");

    JsonObjectTest() throws SyntaxException {

    }

    @Test
    void testToString() {
        assertEquals("{ \"key\": \"VALUE\", \"number\": 123456789, \"bool\": false, " +
                "\"object_JSON\": { \"work?\": \"YES\" }, \"array\": [ 1, \"str\", 3 ], \"add_key\": true }",
                jsonObj.toString());
    }

}
