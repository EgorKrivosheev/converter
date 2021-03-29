# Converter
`Java: 8`, 2 dependencies (`JUnit`, `Annotations`)

## Install: Add this to pom.xml:
    <dependency>
      <groupId>by.grodno.krivosheev</groupId>
      <artifactId>converter</artifactId>
      <version>RELEASE</version>
    </dependency>
    
### Objects
[JsonObject], [XmlObject]

[JsonObject]:https://github.com/EgorKrivosheev/converter/blob/master/src/main/java/by/grodno/krivosheev/objects/ObjectXML.java
[XmlObject]:https://github.com/EgorKrivosheev/converter/blob/master/src/main/java/by/grodno/krivosheev/objects/ObjectXML.java

#### Get JSON object from string
    String jsonStr = "{\"key\":\"value\"}";
    JsonObject jsonObj = new JsonObject(jsonStr);

#### Get XML object from string
    String xmlStr = "<key>VALUE</key>";
    XmlObject xmlObj = new XmlObject(xmlStr);

### Convert

#### Convert JSON object to XML object
    String jsonStr = "{\"key\":\"value\"}";
    JsonObject jsonObj = new JsonObject(jsonStr);
    XmlObject xmlObj = Converter.jsonToXml(jsonObj);

#### Convert XML object to JSON object
    String xmlStr = "<key>VALUE</key>";
    XmlObject xmlObj = new XmlObject(xmlStr);
    JsonObject jsonObj = Converter.xmlToJson(xmlObj);

##### License
[MIT]

[MIT]:https://github.com/EgorKrivosheev/converter/blob/master/LICENSE
