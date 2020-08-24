package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public abstract class Parser {

    /**
     * Get JSON object from string
     * @param source Not null - string JSON
     * @return Object JSON
     */
    @NotNull
    public static ObjectJSON getObjectJSON(@NotNull String source) {

        ObjectJSON objJSON = new ObjectJSON();
        int index = 0;

        while (index < source.length()) {
            /*

             */
            index++;
        }

        return objJSON;
    }

    /**
     * Get XML object from string
     * @param source Not null - string XML
     * @return Object XML
     */
    @NotNull
    public static ObjectXML getObjectXML(@NotNull String source) {

        ObjectXML objXML = new ObjectXML();
        int index = 0;

        while (index < source.length()) {
            /*

             */
            index++;
        }
        return objXML;
    }

    private static String getStringToFoundChar(String source, char findChar) {

        return source.substring(0, source.indexOf(findChar));
    }

    private static int addSubObjXML(String source, ObjectXML objXML) {

        int index = 0;

        while (index < source.charAt(index)) {
            /*

             */
            index++;
        }
        return index;
    }
}
