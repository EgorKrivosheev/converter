package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonArrayObject;
import by.grodno.krivosheev.objects.JsonObject;

import java.util.Arrays;
import java.util.HashSet;

public interface Utils {
    /**
     * Classes whom don't print between symbols "..."
     */
    HashSet<Object> classes = new HashSet<>(
            Arrays.asList(
                    JsonObject.class, Byte.class, Short.class, Integer.class, Long.class, Float.class,
                    Double.class, Boolean.class, JsonArrayObject.class
            )
    );

    /**
     * System chars JSON
     */
    HashSet<Character> sysCharJson = new HashSet<>(
            Arrays.asList(
                    '{', ':', ',', '}', '"', '[', ']'
            )
    );

    /**
     * System chars XML
     */
    HashSet<Character> sysCharXml = new HashSet<>(
            Arrays.asList(
                    '<', '>', '/'
            )
    );
}
