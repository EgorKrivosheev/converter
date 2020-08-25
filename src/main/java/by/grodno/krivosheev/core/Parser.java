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
        char prevPrevChar = ' ';
        char prevChar = ' ';
        Stack<String> stackKeys = new Stack<>();
        String value = " ";
        Stack<ObjectXML> links = new Stack<>();
        boolean savedToLink = false;

        while (index < source.length()) {
            switch (source.charAt(index)) {
                case '<':
                    if (source.charAt(index + 1) != '/') {
                        String key = getStringToFoundChar(source.substring(index + 1), '>');
                        if (prevChar == '<') {
                            if (links.empty()) {
                                links.push(new ObjectXML());
                                links.peek().addKeyAndValue(key, new ObjectXML());
                                objXML.addKeyAndValue(stackKeys.peek(), links.peek());
                            } else {
                                ObjectXML buf = (ObjectXML) links.peek().getMap().get(stackKeys.peek());
                                buf.addKeyAndValue(key, new ObjectXML());
                                links.push(buf);
                            }


                            System.out.println(links.peek().toString());

                            /*ObjectXML newLink = new ObjectXML();
                            newLink.addKeyAndValue(key, new ObjectXML());
                            objXML.addKeyAndValue(stackKeys.peek(), newLink);*/

                            System.out.println(objXML.toString());

                            //links.push(newLink);
                            savedToLink = true;
                        }
                        stackKeys.push(key);
                        prevPrevChar = prevChar;
                        prevChar = '<';
                        index += key.length();
                    } else {
                        index++;
                        String closeKey = getStringToFoundChar(source.substring(index + 1), '>');
                        if (stackKeys.peek().equals(closeKey)) {

                            System.out.println(prevPrevChar + " " + prevChar);

                            if (prevPrevChar == '<' && prevChar == '>') {
                                if (savedToLink) links.peek().addKeyAndValue(stackKeys.pop(), value);
                                else objXML.addKeyAndValue(stackKeys.pop(), value);
                            } else {
                                savedToLink = false;
                                links.pop();
                            }
                        } else System.out.println("ERROR!!! index: " + index + ", open key: " + stackKeys.peek() +
                                                    ", close key: " + closeKey);
                        prevPrevChar = prevChar;
                        prevChar = '/';
                        index += closeKey.length();
                    }
                    break;

                case '>':
                    if (index == source.length() - 1) break;
                    value = getStringToFoundChar(source.substring(index + 1), '<');
                    index += value.length();
                    if (source.charAt(index + 2) == '/') {
                        prevPrevChar = prevChar;
                        prevChar = '>';
                    }
                    break;
            }
            index++;
        }
        if (stackKeys.empty()) System.out.println("Stack keys is empty!");
        else  {
            System.out.println("Stack keys is not empty!");
            for (String key : stackKeys) {
                System.out.println(key);
            }
        }
        if (links.empty()) System.out.println("Links is empty!");
        else {
            System.out.println("Links is no empty!");
            for (ObjectXML obj : links) {
                System.out.println(obj.toString());
            }
        }
        return objXML;
    }

    private static String getStringToFoundChar(String source, char findChar) {

        return source.substring(0, source.indexOf(findChar));
    }
}
