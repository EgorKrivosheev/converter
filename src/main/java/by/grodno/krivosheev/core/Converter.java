package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonObject;
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
        JsonObject jsonObj = new JsonObject();
        // TODO: Refactor
        for (String key : xmlObj.getMap().keySet()) {
            if (xmlObj.getMap().get(key).getClass().equals(XmlObject.class)) {
                jsonObj.addKeyAndValue(key, xmlToJson((XmlObject) xmlObj.getMap().get(key)));
            } else {
                jsonObj.addKeyAndValue(key, xmlObj.getMap().get(key));
            }
        }
        return jsonObj;
    }

    /**
     * Converted JSON to XML
     * @param jsonObj Not null - object JSON
     * @return Object XML
     */
    @NotNull
    public static XmlObject jsonToXml(@NotNull JsonObject jsonObj) {
        XmlObject objXML = new XmlObject();
        // TODO: Refactor
        for (String key : jsonObj.getMap().keySet()) {
            if(jsonObj.getMap().get(key).getClass().equals(JsonObject.class)) {
                objXML.addKeyAndValue(key, jsonToXml((JsonObject) jsonObj.getMap().get(key)));
            } else {
                objXML.addKeyAndValue(key, jsonObj.getMap().get(key));
            }
        }
        return objXML;
    }

}
