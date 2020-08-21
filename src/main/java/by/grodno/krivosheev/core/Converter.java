package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;

import org.jetbrains.annotations.NotNull;

public abstract class Converter {

    /**
     * Converted XML to JSON
     * @param objXML Not null - object XML
     * @return Object JSON
     */
    @NotNull
    public static ObjectJSON toJSON(@NotNull ObjectXML objXML) {

        ObjectJSON objJSON = new ObjectJSON();

        for (String key : objXML.getMap().keySet()) {

            if (objXML.getMap().get(key).getClass().equals(ObjectXML.class)) {

                objJSON.addKeyAndValue(key, toJSON((ObjectXML) objXML.getMap().get(key)));

            } else {

                objJSON.addKeyAndValue(key, objXML.getMap().get(key));

            }

        }

        return objJSON;

    }

    /**
     * Converted JSON to XML
     * @param objJSON Not null - object JSON
     * @return Object XML
     */
    @NotNull
    public static ObjectXML toXML(@NotNull ObjectJSON objJSON) {

        ObjectXML objXML = new ObjectXML();

        for (String key : objJSON.getMap().keySet()) {

            if(objJSON.getMap().get(key).getClass().equals(ObjectJSON.class)) {

                objXML.addKeyAndValue(key, toXML((ObjectJSON) objJSON.getMap().get(key)));

            } else {

                objXML.addKeyAndValue(key, objJSON.getMap().get(key));

            }

        }

        return objXML;
    }

}
