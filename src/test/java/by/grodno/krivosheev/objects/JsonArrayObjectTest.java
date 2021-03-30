package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonArrayObjectTest {
    private final JsonArrayObject jsonArrayObject = new JsonArrayObject();

    @Test
    void testToString() {
        jsonArrayObject.add("String");
        jsonArrayObject.add(123456789);
        jsonArrayObject.add(true);
        assertEquals("[\"String\",123456789,true]", jsonArrayObject.toString());
    }
}
