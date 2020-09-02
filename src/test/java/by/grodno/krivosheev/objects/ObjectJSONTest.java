package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectJSONTest {

    private final ObjectJSON objJSON = new ObjectJSON();

    @Test
    void testToString() {

        objJSON.addKeyAndValue("byte", (byte) 5);
        objJSON.addKeyAndValue("short", (short) 55);
        objJSON.addKeyAndValue("int", 555);
        objJSON.addKeyAndValue("long", (long) 5555);
        objJSON.addKeyAndValue("float", (float) 5.5);
        objJSON.addKeyAndValue("double", 5.55);
        objJSON.addKeyAndValue("bool", true);
        ObjectJSON subObjJSON = new ObjectJSON();
        subObjJSON.addKeyAndValue("work?", "YES");
        objJSON.addKeyAndValue("object_JSON", subObjJSON);

        assertEquals("{ \"byte\": 5, \"short\": 55, \"int\": 555, \"long\": 5555," +
                " \"float\": 5.5, \"double\": 5.55, \"bool\": true, \"object_JSON\": { \"work?\": \"YES\" } }",
                objJSON.toString());

    }
}
