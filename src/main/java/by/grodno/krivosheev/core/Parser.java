package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.ObjectJSON;
import by.grodno.krivosheev.objects.ObjectXML;

import org.jetbrains.annotations.NotNull;

public abstract class Parser {

    /**
     * Get JSON object from string
     * @param source Not null - string JSON
     * @return Object JSON
     */
    @NotNull
    public static ObjectJSON getObjectJSON(@NotNull String source) {

        ObjectJSON objJSON = new ObjectJSON();
        int i = 0;
        char prevChar = ' ';

        while (i < source.length()) {

            /*

             */

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
        int i = 0;
        char prevChar = ' ';

        while (i < source.length()) {

            /*

             */

        }

        return objXML;

    }

}
