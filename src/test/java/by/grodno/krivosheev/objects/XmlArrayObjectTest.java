package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlArrayObjectTest {
    private final XmlArrayObject xmlArrayObject = new XmlArrayObject();

    @Test
    void testToString() {
        xmlArrayObject.add("String");
        xmlArrayObject.add(123456789);
        xmlArrayObject.add(true);
        assertEquals("<element>String</element><element>123456789</element><element>true</element>", xmlArrayObject.toString());
    }
}
