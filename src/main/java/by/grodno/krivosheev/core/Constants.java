package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonArrayObject;
import by.grodno.krivosheev.objects.JsonObject;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Constants {
    public enum tagXml {
        INIT, // For initialize
        OpenTag,
        CloseTag
    }

    /**
     * Classes whom don't print between symbols "..."
     */
    public static HashSet<Object> classes = new HashSet<>(
            Arrays.asList(
                    JsonObject.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class,
                    JsonArrayObject.class
            )
    );

    /**
     * System chars JSON
     */
    public static HashSet<Character> sysCharJson = new HashSet<>(
            Arrays.asList(
                    '{', ':', ',', '}', '"', '[', ']'
            )
    );

    /**
     * System chars XML
     */
    public static HashSet<Character> sysCharXml = new HashSet<>(
            Arrays.asList(
                    '<', '>', '/'
            )
    );
}
