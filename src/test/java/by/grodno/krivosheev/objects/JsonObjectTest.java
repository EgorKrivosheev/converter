package by.grodno.krivosheev.objects;

import by.grodno.krivosheev.core.SyntaxException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectTest {
    private final JsonObject jsonObj = new JsonObject("{" +
                "\"byte\": 123," +
                "\"short\": -4567," +
                "\"integer\": 890123," +
                "\"long\": -4567890123456," +
                "\"array\": [" +
                    "\"item 1\", 2" +
                "]," +
                "\"string\": \"789012345678901234567\"," +
                "\"object\": {" +
                    "\"boolean1\": true," +
                    "\"boolean2\": false" +
                "}," +
                "\"float\": 8.901," +
                "\"double\": -234567890123456.78901234567890123456789" +
            "}");

    JsonObjectTest() throws SyntaxException {

    }

    @Test
    void testToString() {
        assertEquals("{\"byte\":123,\"short\":-4567,\"integer\":890123,\"long\":-4567890123456," +
                "\"array\":[\"item 1\",2],\"string\":\"789012345678901234567\",\"object\":{\"boolean1\":true," +
                "\"boolean2\":false},\"float\":8.901,\"double\":-2.3456789012345678E14}", jsonObj.toString());
    }
}
