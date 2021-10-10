package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonArrayObject;
import by.grodno.krivosheev.objects.JsonObject;
import by.grodno.krivosheev.objects.XmlArrayObject;
import by.grodno.krivosheev.objects.XmlObject;

public abstract class Converter {
    /**
     * Converted XML to JSON
     * @param xmlObj Object XML
     * @return Object JSON
     */
    public static JsonObject xmlToJson(XmlObject xmlObj) {
        return (JsonObject) convertObject(xmlObj);
    }

    /**
     * Converted JSON to XML
     * @param jsonObj Not null - object JSON
     * @return Object XML
     */
    public static XmlObject jsonToXml(JsonObject jsonObj) {
        return (XmlObject) convertObject(jsonObj);
    }

    /**
     * Convert XmlObject to JsonObject, JsonObject to XmlObject
     * @param object Object for converting
     * @return If {@code object} XmlObject return JsonObject, if {@code object} JsonObject return XmlObject
     */
    private static AbstractObject convertObject(AbstractObject object) {
        AbstractObject newObj = object instanceof XmlObject ?
                new JsonObject() :
                object instanceof JsonObject ?
                        new XmlObject() :
                        new AbstractObject() {};

        object.getMap().keySet()
                .forEach(key -> newObj.addKeyAndValue(key, object.getObject(key) instanceof AbstractObject ?
                        // Recursive
                        convertObject((AbstractObject) object.getObject(key)) :
                        object.getObject(key) instanceof AbstractArrayObject ?
                                convertArray((AbstractArrayObject) object.getObject(key)) :
                                object.getObject(key)
                        )
                );
        return newObj;
    }

    /**
     * Convert XmlArray to JsonArray, JsonArray to XmlArray
     * @param array Array for converting
     * @return If {@code array} XmlArray return JsonArray, if {@code array} JsonArray return XmlArray
     */
    private static AbstractArrayObject convertArray(AbstractArrayObject array) {
        AbstractArrayObject newArray = array instanceof XmlArrayObject ?
                new JsonArrayObject() :
                array instanceof JsonArrayObject ?
                        new XmlArrayObject() :
                        new AbstractArrayObject() {};

        array.getValue()
                .forEach(o -> newArray.add(o instanceof AbstractArrayObject ?
                        // Recursive
                        convertArray((AbstractArrayObject) o) :
                        o instanceof AbstractObject ?
                                convertObject((AbstractObject) o) :
                                o
                        )
                );
        return newArray;
    }
}
