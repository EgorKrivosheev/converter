package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectXMLTest {

    private final ObjectXML objXML = new ObjectXML();

    @Test
    void testToString() {

        objXML.addKeyAndValue("one", "1");
        ObjectXML obj = new ObjectXML();
        obj.addKeyAndValue("four", "4");
        objXML.addKeyAndValue("two", obj);
        objXML.addKeyAndValue("three", "3");

        assertEquals("<one>1</one><two><four>4</four></two><three>3</three>", objXML.toString());

    }

}
