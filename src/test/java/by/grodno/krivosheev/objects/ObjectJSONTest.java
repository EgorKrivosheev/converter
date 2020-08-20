package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectJSONTest {

    private final ObjectJSON objJSON = new ObjectJSON();

    @Test
    void testToString() {

        objJSON.addKeyAndValue("1", "one");
        ObjectJSON o = new ObjectJSON();
        o.addKeyAndValue("4", "four");
        objJSON.addKeyAndValue("2", o);

        assertEquals("{ \"1\": \"one\", \"2\": { \"4\": \"four\" } }", objJSON.toString());

    }

}
