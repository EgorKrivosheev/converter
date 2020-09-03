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

        char prevChar = ' ';
        Stack<String> stackKeys = new Stack<>();
        String str = "";
        StringBuilder strBuilder = new StringBuilder();

        while (index < source.length()) {
            switch (source.charAt(index)) {
                case '{':
                    prevChar = '{';
                    break;

                case '"':
                    if (prevChar != '"') {
                        str = getStringToFoundChar(source.substring(index + 1), '"');
                        index += str.length();
                    }
                    prevChar = '"';
                    break;

                case ':':
                    stackKeys.push(str);
                    prevChar = ':';
                    break;

                case ',':
                    if (prevChar == ':' || prevChar == '"') {
                        objJSON.addKeyAndValue(stackKeys.pop(), prevChar == ':' ? setValue(strBuilder.toString()) : str);
                        strBuilder.setLength(0);
                        prevChar = ',';
                        break;
                    }
                    stackKeys.pop();
                    prevChar = ',';
                    break;

                case '}':
                    if (prevChar == ',') System.out.println("Error! index:" + index + " previous char ','");
                    if (prevChar == ':' || prevChar == '"') {
                        objJSON.addKeyAndValue(stackKeys.pop(), prevChar == ':' ? setValue(strBuilder.toString()) : str);
                        strBuilder.setLength(0);
                        prevChar = '}';
                        break;
                    }
                    stackKeys.pop();
                    prevChar = '}';
                    break;

                default:
                    if (prevChar == ':') strBuilder.append(source.charAt(index) != ' ' ? source.charAt(index) : "");
                    break;
            }
            index++;
        }

        if (!stackKeys.isEmpty()) System.out.println("Error!!! Stack keys is not empty!");
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
        Stack<String> stackKeys = new Stack<>(); // Stack for open keys
        String value = " ";
        Stack<ObjectXML> stackLinks = new Stack<>(); // Nesting stack (example: <key><newKey>VALUE</newKey></key>)
        Stack<Boolean> savedToLink = new Stack<>();  // Needs to be nested
        savedToLink.push(false);                // No nesting by default

        while (index < source.length()) {
            switch (source.charAt(index)) {
                case '<':
                    if (source.charAt(index + 1) != '/') { // True = element open KEY, false = element close KEY
                        String key = getStringToFoundChar(source.substring(index + 1), '>');
                        if (prevChar == '<') { // If previous element was be KEY
                            if (stackLinks.empty()) {
                                stackLinks.push(new ObjectXML());
                                stackLinks.peek().addKeyAndValue(key, new ObjectXML());
                                objXML.addKeyAndValue(stackKeys.peek(), stackLinks.peek());
                            } else {
                                if (stackLinks.peek().getMap().get(stackKeys.peek()) == null)
                                    stackLinks.peek().addKeyAndValue(stackKeys.peek(), new ObjectXML());
                                ObjectXML newLink = (ObjectXML) stackLinks.peek().getMap().get(stackKeys.peek());
                                newLink.addKeyAndValue(key, new ObjectXML());
                                stackLinks.push(newLink);
                            }
                            savedToLink.push(true);
                        }
                        stackKeys.push(key);
                        prevPrevChar = prevChar;
                        prevChar = '<';
                        index += key.length();
                    } else {
                        index++;
                        String closeKey = getStringToFoundChar(source.substring(index + 1), '>');
                        if (stackKeys.peek().equals(closeKey)) { // Comparing a closing KEY with a previously open KEY
                            if (prevPrevChar == '<' && prevChar == '>') { // If previous element was be open KEY
                                if (savedToLink.peek()) stackLinks.peek().addKeyAndValue(stackKeys.pop(), value); // Save to link
                                else objXML.addKeyAndValue(stackKeys.pop(), value); // Save to root
                            } else {
                                savedToLink.pop();  //
                                stackLinks.pop();   // Popped stacks
                                stackKeys.pop();    //
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
        if (!stackKeys.empty()) System.out.println("Error!!! Stack keys is not empty!");
        if (!stackLinks.empty()) System.out.println("Error!!! Links is not empty!");
        return objXML;
    }

    private static String getStringToFoundChar(String source, char findChar) {

        return source.substring(0, source.indexOf(findChar));
    }

    protected static boolean isIntNumber(@NotNull String str) {

        return str.matches("^(?:(?:-)?\\d+)$");
    }

    protected static boolean isDecNumber(@NotNull String str) {

        return str.matches("^(?:(?:-)?\\d+\\.\\d+)$");
    }

    protected static Object setValue(@NotNull String str) {

        if (!isIntNumber(str) && !isDecNumber(str)) {       //
            if (str.equals("true")) return Boolean.TRUE;    // The value is of type boolean
            if (str.equals("false")) return Boolean.FALSE;  //
        }
        if (isIntNumber(str)) {
            if (str.length() < 3) return Byte.parseByte(str);   // The value is of type byte
            if (str.length() > 19) return str;  // The value is of type string
            if (str.length() < 5) {
                short buf = Short.parseShort(str);
                if (buf >= -128 && buf < 128) return Byte.parseByte(str);   // The value is of type byte
                else return buf;    // The value is of type short
            }
            if (str.length() < 10) {
                int buf = Integer.parseInt(str);
                if (buf >= -32768 && buf < 32767) return Short.parseShort(str);     // The value is of type short
                else return buf;    // The value is of type int
            }
            if (str.length() < 19) {
                long buf = Long.parseLong(str);
                if (buf >= -2147483648 && buf < 2147483647) return Integer.parseInt(str);   // The value is of type int
                else return buf;    // The value is of type long
            }
        }
        return str;
    }
}
