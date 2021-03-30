package by.grodno.krivosheev.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlArrayObjectTest {
    private final XmlArrayObject xmlArrayObject = new XmlArrayObject();

    @Test
    void testToString() {
        xmlArrayObject.add(false);
        xmlArrayObject.add(-98);
        xmlArrayObject.add("str");
        assertEquals("<element>false</element><element>-98</element><element>str</element>", xmlArrayObject.toString());
    }
}
