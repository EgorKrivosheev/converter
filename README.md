# Converter

### Install: Add this to pom.xml:

    <dependency>
      <groupId>by.grodno.krivosheev</groupId>
      <artifactId>converter</artifactId>
      <version>RELEASE</version>
    </dependency>
    
#### Parser

##### Get JSON object from text

    ObjectJSON objJSON = Parser.getObjectJSON("{ "key": "value" }");

##### Get XML object from text

    ObjectXML objXML = Parser.getObjectXML("<key>VALUE</key>");

#### Convert

##### Convert JSON object to XML object

    ObjectJSON objJSON = new ObjectJSON("{ "key": "value" }");
    ObjectXML objXML = Converter.toXML(objJSON);

##### Convert XML object to JSON object

    ObjectXML objXML = new ObjectXML("<key>VALUE</key>");
    ObjectJSON objJSON = Converter.toJSON(objXML);
