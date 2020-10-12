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
    public static ObjectJSON getObjectJSON(@NotNull String source) throws Exception {
        ObjectJSON objJSON = new ObjectJSON();
        int index = 0;
        char prevChar = ' ';
        Stack<String> stackKeys = new Stack<>(); // Stack for keys
        String str = "";
        StringBuilder strBuilder = new StringBuilder();
        Stack<ObjectJSON> stackLinks = new Stack<>(); // Nesting stack (example: { "key": { "newKey": "Value" } })
        Stack<Boolean> savedToLink = new Stack<>(); // Needs to be nested
        savedToLink.push(false);                // No nesting by default

        while (index < source.length()) {
            switch (source.charAt(index)) {
                case '{':
                    if (prevChar == ':') {
                        if (stackLinks.empty()) {
                            stackLinks.push(new ObjectJSON());                          // Create new ObjectJSON for link
                            objJSON.addKeyAndValue(stackKeys.peek(), stackLinks.peek());// add in root objectJSON
                        } else {
                            stackLinks.peek().addKeyAndValue(stackKeys.peek(), new ObjectJSON());
                            stackLinks.push((ObjectJSON) stackLinks.peek().getMap().get(stackKeys.peek()));
                        }
                        savedToLink.push(true);
                    }
                    prevChar = '{';
                    break;

                case '"':
                    if (prevChar == ' ') throw new Exception("Error!!! index: " + index + " waiting '{'");
                    if (prevChar != '"') {
                        str = getStringToFoundChar(source.substring(index + 1), '"', index); // Get VALUE between "..."
                        index += str.length();
                    }
                    prevChar = '"';
                    break;

                case ':':
                    if (prevChar != '"') throw new Exception("Error!!! index: " + index + " waiting \"key\"");
                    stackKeys.push(str);
                    prevChar = ':';
                    break;

                case ',':
                    if (prevChar == '{' || prevChar == ' ') throw new Exception("Error!!! index: " + index + " waiting \"value\"");
                    if (prevChar == ':' || prevChar == '"') {
                        if (stackKeys.isEmpty()) throw new Exception("Error!!! index: " + index + " missing previous char ':'");
                        if (savedToLink.peek()) stackLinks.peek().addKeyAndValue(stackKeys.pop(), prevChar == ':' ?
                                                    setValue(strBuilder.toString()) : str); // Save to link
                        else objJSON.addKeyAndValue(stackKeys.pop(), prevChar == ':' ?
                                setValue(strBuilder.toString()) : str); // Save to root
                        strBuilder.setLength(0);
                        prevChar = ',';
                        break;
                    }
                    stackKeys.pop();
                    prevChar = ',';
                    break;

                case '}':
                    if (prevChar == ',' || prevChar == '{')
                        throw new Exception("Error!!! index: " + index + " previous char '" + prevChar + "'");
                    if (prevChar == ':' || prevChar == '"') {
                        if (stackKeys.isEmpty()) throw new Exception("Error!!! index: " + index + " missing previous char ':'");
                        if (savedToLink.peek()) {
                            stackLinks.pop().addKeyAndValue(stackKeys.pop(), prevChar == ':' ?
                                setValue(strBuilder.toString()) : str); // Save to link
                            savedToLink.pop();
                        } else objJSON.addKeyAndValue(stackKeys.pop(), prevChar == ':' ?
                                setValue(strBuilder.toString()) : str); // Save to root
                        strBuilder.setLength(0);
                        prevChar = '}';
                        break;
                    }
                    if (prevChar == '}' && !stackKeys.isEmpty()) {
                        if (!stackLinks.isEmpty()) {
                            if (stackLinks.peek().getMap().containsKey(stackKeys.peek())) {
                                stackLinks.pop();
                                savedToLink.pop();
                            }
                        }
                    } else throw new Exception("Error!!! index: " + index);
                    stackKeys.pop();
                    prevChar = '}';
                    break;

                default:
                    if (prevChar == ':') strBuilder.append(source.charAt(index) != ' ' ? source.charAt(index) : "");
                    break;
            }
            index++;
        }
        if (!stackKeys.isEmpty() || !stackLinks.empty()) throw new Exception("Error!!! key: " + stackKeys.peek() +
                                                            " waiting char ',' or '}'");
        if (prevChar == '{' || prevChar == '"' || prevChar == ':' || prevChar == ',')
            throw new Exception("Error!!! index: " + index);
        return objJSON;
    }

    /**
     * Get XML object from string
     * @param source Not null - string XML
     * @return Object XML
     */
    @NotNull
    public static ObjectXML getObjectXML(@NotNull String source) throws Exception{
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
                    if (index + 1 > source.length() - 1) throw new Exception("Error!!! index: " + index + " waiting char '>'");
                    if (source.charAt(index + 1) != '/') { // True = element open KEY, false = element close KEY
                        String key = getStringToFoundChar(source.substring(index + 1), '>', index);
                        if (prevChar == '<') { // If previous element was be KEY
                            if (stackLinks.empty()) {
                                stackLinks.push(new ObjectXML());                       // Create new ObjectXML
                                stackLinks.peek().addKeyAndValue(key, new ObjectXML()); // for stack and add KEY
                                objXML.addKeyAndValue(stackKeys.peek(), stackLinks.peek()); // Add in root ObjectXML
                            } else {
                                if (stackLinks.peek().getMap().get(stackKeys.peek()) == null)           // If in object of stack not found KEY
                                    stackLinks.peek().addKeyAndValue(stackKeys.peek(), new ObjectXML());// add this KEY for last objectXML
                                ObjectXML newLink = (ObjectXML) stackLinks.peek().getMap().get(stackKeys.peek());   // Get last objectXML
                                newLink.addKeyAndValue(key, new ObjectXML());                                       // add this objectXML new KEY
                                stackLinks.push(newLink);                                                           // push new object for link
                            }
                            savedToLink.push(true); // The next KEYS need save to the link
                        }
                        stackKeys.push(key);
                        prevPrevChar = prevChar;
                        prevChar = '<';
                        index += key.length();
                    } else {
                        index++;
                        String closeKey = getStringToFoundChar(source.substring(index + 1), '>', index);
                        if (stackKeys.peek().equals(closeKey)) { // Comparing a closing KEY with a previously open KEY
                            if (prevPrevChar == '<' && prevChar == '>') { // If previous element was be open KEY
                                if (savedToLink.peek()) stackLinks.peek().addKeyAndValue(stackKeys.pop(), value); // Save to link
                                else objXML.addKeyAndValue(stackKeys.pop(), value); // Save to root
                            } else {
                                savedToLink.pop();  //
                                stackLinks.pop();   // Popped stacks
                                stackKeys.pop();    //
                            }
                        } else throw new Exception("Error!!! index: " + index + ", open key: " + stackKeys.peek() +
                                    ", close key: " + closeKey);
                        prevPrevChar = prevChar;
                        prevChar = '/';
                        index += closeKey.length();
                    }
                    break;

                case '>':
                    if (index == source.length() - 1) break;
                    value = getStringToFoundChar(source.substring(index + 1), '<', index); // Get VALUE
                    index += value.length();
                    if (source.charAt(index + 2) == '/') {
                        prevPrevChar = prevChar;
                        prevChar = '>';
                    }
                    break;
            }
            index++;
        }
        if (!stackKeys.empty() || !stackLinks.empty()) throw new Exception("Error!!! open key: " + stackKeys.peek() +
                                                            " waiting close key '</" + stackKeys.peek() + ">'");
        return objXML;
    }

    @NotNull
    private static String getStringToFoundChar(@NotNull String source, char findChar, int index) throws Exception {
        if (source.indexOf(findChar) == -1 || source.equals("")) throw new Exception("Error!!! index: " + index + " waiting char '" + findChar + "'");
        return source.substring(0, source.indexOf(findChar));
    }

    protected static boolean isIntNumber(@NotNull String str) {
        return str.matches("(?:-)?\\d+");
    }

    protected static boolean isDecNumber(@NotNull String str) {
        return str.matches("(?:-)?\\d+\\.\\d+");
    }

    protected static Object setValue(@NotNull String str) {
        if (str.equals("true")) return Boolean.TRUE;
        if (str.equals("false")) return Boolean.FALSE;
        if (isIntNumber(str)) {
            if (str.length() < 3) return Byte.parseByte(str); // The value is of type byte
            if (str.length() > 19) return str; // The value is of type string
            if (str.length() < 5) {
                short buf = Short.parseShort(str);
                if (buf >= Byte.MIN_VALUE && buf <= Byte.MAX_VALUE) return Byte.parseByte(str); // The value is of type byte
                else return buf;    // The value is of type short
            }
            if (str.length() < 10) {
                int buf = Integer.parseInt(str);
                if (buf >= Short.MIN_VALUE && buf <= Short.MAX_VALUE) return Short.parseShort(str); // The value is of type short
                else return buf;    // The value is of type int
            }
            if (str.length() < 19) {
                long buf = Long.parseLong(str);
                if (buf >= Integer.MIN_VALUE && buf <= Integer.MAX_VALUE) return Integer.parseInt(str); // The value is of type int
                else return buf;    // The value is of type long
            }
        }
        if (isDecNumber(str)) {
            if (str.length() > 307) return str; // The value is of type string
            else if (str.length() < 38) return Float.parseFloat(str); // The value is of type float
            else return Double.parseDouble(str);    // The value is of type double
        }
        return str;
    }
}
