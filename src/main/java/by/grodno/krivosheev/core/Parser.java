package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonArrayObject;
import by.grodno.krivosheev.objects.JsonObject;
import by.grodno.krivosheev.objects.XmlObject;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public abstract class Parser {
    private static int index = 0;

    private static JsonArrayObject getJsonArrayObject(String source) throws SyntaxException {
        JsonArrayObject jsonArrayObj = new JsonArrayObject();
        char prevCh = '[';
        boolean isSaveSpace = false;
        StringBuilder strBuilder = new StringBuilder();

        while (index < source.length()) {
            char ch = source.charAt(index);
            switch (ch) {
                case '[':

                    break;

                case '{':
                    jsonArrayObj.add(getJsonObject(source));
                    break;

                case ',':

                case ']':
                    if (prevCh != '{') {
                        jsonArrayObj.add(setValue(strBuilder.toString()));
                    }
                    strBuilder.setLength(0);
                    if (ch != ',' && index != source.length() - 1) {
                        return jsonArrayObj;
                    }
                    break;

                case '"':
                    isSaveSpace = prevCh != '"';
                    if (prevCh != '"') {
                        strBuilder.setLength(0);
                    }
                    break;

                default:
                    if (ch == ' ' && !isSaveSpace) {
                        break;
                    }
                    strBuilder.append(ch);
                    break;
            }
            if (Utils.sysCharJson.contains(ch)) {
                prevCh = ch;
            }
            index++;
        }
        return jsonArrayObj;
    }

    /**
     * Get JSON object from string
     * @param source Not null - string JSON
     * @return Object JSON
     * @throws SyntaxException If the {@code source} expression's syntax is invalid
     */
    @NotNull
    public static JsonObject getJsonObject(@NotNull String source) throws SyntaxException {
        Validator.isValidJsonText(source);
        // Next code will used if source is valid JSON text
        JsonObject jsonObj = new JsonObject();
        String key = "";
        char prevCh = ' ';
        boolean isSaveSpace = false;
        StringBuilder strBuilder = new StringBuilder();

        do {
            char ch = source.charAt(index);
            switch (ch) {
                case '{':
                    if (prevCh == ':') {
                        // Recursive
                        jsonObj.addKeyAndValue(key, getJsonObject(source));
                    }
                    break;

                case ',':

                case '}':
                    if (prevCh != '{' && prevCh != '[') {
                        jsonObj.addKeyAndValue(key, setValue(strBuilder.toString()));
                    }
                    if (ch != ',' && index != source.length() - 1) {
                        return jsonObj;
                    }
                    break;

                case ':':
                    key = strBuilder.toString();
                    // Reset builder
                    strBuilder.setLength(0);
                    break;

                case '"':
                    isSaveSpace = prevCh != '"';
                    if (prevCh != '"') {
                        strBuilder.setLength(0);
                    }
                    break;

                case '[':
                    jsonObj.addKeyAndValue(key, getJsonArrayObject(source));
                    break;

                default:
                    if (ch == ' ' && !isSaveSpace) {
                        break;
                    }
                    strBuilder.append(ch);
                    break;
            }
            if (Utils.sysCharJson.contains(ch)) {
                prevCh = ch;
            }
            index++;
        } while (index < source.length());
        // Reset static index
        index = 0;
        return jsonObj;
    }

    /**
     * Get XML object from string
     * @param source Not null - string XML
     * @return Object XML
     * @throws SyntaxException If the {@code source} expression's syntax is invalid
     */
    @NotNull
    public static XmlObject getXmlObject(@NotNull String source) throws SyntaxException {
        Validator.isValidXmlText(source);
        // Next code will used if source is valid XML text
        XmlObject objXML = new XmlObject();
        int index = 0;
        char prevPrevChar = ' ';
        char prevChar = ' ';
        // Stack for open keys
        Stack<String> stackKeys = new Stack<>();
        String value = " ";
        // Nesting stack (example: <key><newKey>VALUE</newKey></key>)
        Stack<XmlObject> stackLinks = new Stack<>();
        // Needs to be nested
        Stack<Boolean> savedToLink = new Stack<>();
        // No nesting by default
        savedToLink.push(false);
        // TODO: Refactor
        while (index < source.length()) {
            switch (source.charAt(index)) {
                case '<':
                    if (index + 1 > source.length() - 1) {
                        throw new SyntaxException("Error!!! index: " + index + " waiting char '>'");
                    }
                    // True = element open KEY, false = element close KEY
                    if (source.charAt(index + 1) != '/') {
                        String key = getStringToFoundChar(source.substring(index + 1), '>', index);
                        // If previous element was be KEY
                        if (prevChar == '<') {
                            if (stackLinks.empty()) {
                                // Create new ObjectXML for stack and add KEY
                                stackLinks.push(new XmlObject());
                                stackLinks.peek().addKeyAndValue(key, new XmlObject());
                                // Add in root ObjectXML
                                objXML.addKeyAndValue(stackKeys.peek(), stackLinks.peek());
                            } else {
                                // If in object of stack not found KEY, add this KEY for last objectXML
                                if (stackLinks.peek().getMap().get(stackKeys.peek()) == null) {
                                    stackLinks.peek().addKeyAndValue(stackKeys.peek(), new XmlObject());
                                }
                                // Get last objectXML
                                XmlObject newLink = (XmlObject) stackLinks.peek().getMap().get(stackKeys.peek());
                                // Add this objectXML new KEY
                                newLink.addKeyAndValue(key, new XmlObject());
                                // Push new object for link
                                stackLinks.push(newLink);
                            }
                            // The next KEYS need save to the link
                            savedToLink.push(true);
                        }
                        stackKeys.push(key);
                        prevPrevChar = prevChar;
                        prevChar = '<';
                        index += key.length();
                    } else {
                        index++;
                        String closeKey = getStringToFoundChar(source.substring(index + 1), '>', index);
                        // Comparing a closing KEY with a previously open KEY
                        if (stackKeys.peek().equals(closeKey)) {
                            // If previous element was be open KEY
                            if (prevPrevChar == '<' && prevChar == '>') {
                                // Save to link or to root
                                if (savedToLink.peek()) stackLinks.peek().addKeyAndValue(stackKeys.pop(), value);
                                else objXML.addKeyAndValue(stackKeys.pop(), value);
                            } else {
                                savedToLink.pop();
                                stackLinks.pop();
                                stackKeys.pop();
                            }
                        } else {
                            throw new SyntaxException("Error!!! index: " + index + ", open key: " + stackKeys.peek() +
                                    ", close key: " + closeKey);
                        }
                        prevPrevChar = prevChar;
                        prevChar = '/';
                        index += closeKey.length();
                    }
                    break;

                case '>':
                    if (index == source.length() - 1) break;
                    value = getStringToFoundChar(source.substring(index + 1), '<', index);
                    index += value.length();
                    if (source.charAt(index + 2) == '/') {
                        prevPrevChar = prevChar;
                        prevChar = '>';
                    }
                    break;
            }
            index++;
        }
        if (!stackKeys.empty() || !stackLinks.empty()) {
            throw new SyntaxException("Error!!! open key: " + stackKeys.peek() +
                    " waiting close key '</" + stackKeys.peek() + ">'");
        }
        return objXML;
    }

    @NotNull
    private static String getStringToFoundChar(@NotNull String source, char findChar, int index) throws SyntaxException {
        if (source.indexOf(findChar) == -1 || source.equals("")) {
            throw new SyntaxException("Error!!! index: " + index + " waiting char '" + findChar + "'");
        }
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
            if (str.length() < 3) return Byte.parseByte(str);
            if (str.length() > 19) return str;
            if (str.length() < 5) {
                short buf = Short.parseShort(str);
                if (buf >= Byte.MIN_VALUE && buf <= Byte.MAX_VALUE) return Byte.parseByte(str);
                else return buf;
            }
            if (str.length() < 10) {
                int buf = Integer.parseInt(str);
                if (buf >= Short.MIN_VALUE && buf <= Short.MAX_VALUE) return Short.parseShort(str);
                else return buf;
            }
            if (str.length() < 19) {
                long buf = Long.parseLong(str);
                if (buf >= Integer.MIN_VALUE && buf <= Integer.MAX_VALUE) return Integer.parseInt(str);
                else return buf;
            }
        }
        if (isDecNumber(str)) {
            if (str.length() > 307) return str;
            else if (str.length() < 38) return Float.parseFloat(str);
            else return Double.parseDouble(str);
        }
        return str;
    }

}
