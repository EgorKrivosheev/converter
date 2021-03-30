package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonArrayObject;
import by.grodno.krivosheev.objects.JsonObject;
import by.grodno.krivosheev.objects.XmlArrayObject;
import by.grodno.krivosheev.objects.XmlObject;

import org.jetbrains.annotations.NotNull;

public abstract class Converter {
    /**
     * Converted XML to JSON
     * @param xmlObj Not null - object XML
     * @return Object JSON
     */
    @NotNull
    public static JsonObject xmlToJson(@NotNull XmlObject xmlObj) {
        return (JsonObject) convertObject(xmlObj);
    }

    /**
     * Converted JSON to XML
     * @param jsonObj Not null - object JSON
     * @return Object XML
     */
    @NotNull
    public static XmlObject jsonToXml(@NotNull JsonObject jsonObj) {
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
                        // Maybe is bad practice...
                        new AbstractObject() {
                            @Override
                            public String toString() {
                                return null;
                            }
                        };

        for (String key : object.getMap().keySet()) {
            if (object.getObject(key) instanceof XmlObject || object.getObject(key) instanceof JsonObject) {
                // Recursive
                newObj.addKeyAndValue(key, convertObject((AbstractObject) object.getObject(key)));
            } else if (object.getObject(key) instanceof XmlArrayObject || object.getObject(key) instanceof JsonArrayObject) {
                newObj.addKeyAndValue(key, convertArray((AbstractArrayObject) object.getObject(key)));
            } else {
                newObj.addKeyAndValue(key, object.getObject(key));
            }
        }
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

        for (Object o : array.getValue()) {
            if (o instanceof XmlArrayObject || o instanceof JsonArrayObject) {
                // Recursive
                newArray.add(convertArray((AbstractArrayObject) o));
            } else if (o instanceof XmlObject || o instanceof JsonObject) {
                newArray.add(convertObject((AbstractObject) o));
            } else {
                newArray.add(o);
            }
        }
        return newArray;
    }
}
