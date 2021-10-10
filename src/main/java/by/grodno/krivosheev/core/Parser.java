package by.grodno.krivosheev.core;

import by.grodno.krivosheev.objects.JsonArrayObject;
import by.grodno.krivosheev.objects.JsonObject;
import by.grodno.krivosheev.objects.XmlArrayObject;
import by.grodno.krivosheev.objects.XmlObject;

import java.util.Stack;

public abstract class Parser {
    /**
     * Get JSON object from string
     * @param source String JSON
     * @return Object JSON
     * @throws SyntaxException If the {@code source} expression's syntax is invalid
     */
    public static JsonObject getJsonObject(String source) throws SyntaxException {
        Validator.isValidJsonText(source);
        // Next code will used if source is valid JSON text
        Wrapper indexWrapper = new Wrapper();
        return getJsonObject(source, indexWrapper);
    }

    /**
     * Get XML object from string
     * @param source String XML
     * @return Object XML
     * @throws SyntaxException If the {@code source} expression's syntax is invalid
     */
    public static XmlObject getXmlObject(String source) throws SyntaxException {
        Validator.isValidXmlText(source);
        // Next code will used if source is valid XML text
        int index = 0;
        XmlObject objXML = new XmlObject();
        char prevCh = ' ';
        Stack<String> key = new Stack<>();
        String value = "";
        Stack<Object> linkStack = new Stack<>();
        StringBuilder strBuilder = new StringBuilder();
        Constants.tagXml last = Constants.tagXml.INIT;
        // Default save to xml object
        linkStack.push(objXML);

        do {
            char ch = source.charAt(index);
            switch (ch) {
                case '<':
                    // Ignored
                    break;

                case '>':
                    if (prevCh == '/') {
                        if (last == Constants.tagXml.CloseTag) {
                            if (!strBuilder.toString().equalsIgnoreCase("element")) {
                                linkStack.pop();
                            }
                            key.pop();
                            strBuilder.setLength(0);
                            break;
                        }
                        if (linkStack.peek() instanceof XmlObject) {
                            ((XmlObject) linkStack.peek()).addKeyAndValue(key.pop(), setValue(value));
                        }
                        if (linkStack.peek() instanceof XmlArrayObject) {
                            if (key.peek().equalsIgnoreCase("element")) {
                                ((XmlArrayObject) linkStack.peek()).add(setValue(value));
                                key.pop();
                            } else {
                                XmlObject bufObj = new XmlObject();
                                bufObj.addKeyAndValue(key.pop(), setValue(value));
                                ((XmlArrayObject) linkStack.peek()).add(bufObj);
                            }
                        }
                        last = Constants.tagXml.CloseTag;
                        strBuilder.setLength(0);
                        break;
                    }
                    if (last == Constants.tagXml.OpenTag) {
                        if (linkStack.peek() instanceof XmlObject) {
                            ((XmlObject) linkStack.peek()).addKeyAndValue(key.peek(), strBuilder.toString().equalsIgnoreCase("element") ?
                                    linkStack.push(new XmlArrayObject()) :
                                    linkStack.push(new XmlObject()));
                        }
                    }
                    key.push(strBuilder.toString());
                    last = Constants.tagXml.OpenTag;
                    strBuilder.setLength(0);
                    break;

                case '/':
                    value = strBuilder.toString();
                    strBuilder.setLength(0);
                    break;

                default:
                    strBuilder.append(ch);
                    break;
            }
            if (Constants.sysCharXml.contains(ch)) {
                prevCh = ch;
            }
            index++;
        } while (index < source.length());
        return objXML;
    }

    protected static Object setValue(String str) {
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (isIntNumber(str)) {
            if (str.length() > 19) {
                return str;
            }
            if (str.length() < 3) {
                return Byte.parseByte(str);
            }
            if (str.length() < 5) {
                short buf = Short.parseShort(str);
                if (buf >= Byte.MIN_VALUE && buf <= Byte.MAX_VALUE) {
                    return Byte.parseByte(str);
                }
                return buf;
            }
            if (str.length() < 10) {
                int buf = Integer.parseInt(str);
                if (buf >= Short.MIN_VALUE && buf <= Short.MAX_VALUE) {
                    return Short.parseShort(str);
                }
                return buf;
            }
            if (str.length() < 19) {
                long buf = Long.parseLong(str);
                if (buf >= Integer.MIN_VALUE && buf <= Integer.MAX_VALUE) {
                    return Integer.parseInt(str);
                }
                return buf;
            }
        }
        if (isDecNumber(str)) {
            if (str.length() > 307) {
                return str;
            }
            if (str.length() < 38) {
                return Float.parseFloat(str);
            }
            return Double.parseDouble(str);
        }
        return str;
    }

    protected static boolean isIntNumber(String str) {
        return str.matches("-?\\d+");
    }

    protected static boolean isDecNumber(String str) {
        return str.matches("-?\\d+\\.\\d+");
    }

    private static JsonObject getJsonObject(String source, Wrapper indexWrapper) {
        JsonObject jsonObj = new JsonObject();
        char prevCh = ' ';
        String key = "";
        boolean isSaveSpace = false;
        StringBuilder strBuilder = new StringBuilder();

        do {
            char ch = source.charAt(indexWrapper.getIndex());
            switch (ch) {
                case '{':
                    if (prevCh == ':') {
                        // Recursive
                        jsonObj.addKeyAndValue(key, getJsonObject(source, indexWrapper));
                    }
                    break;

                case ',':
                case '}':
                    if (prevCh != '{' && prevCh != '[') {
                        jsonObj.addKeyAndValue(key, setValue(strBuilder.toString()));
                    }
                    // Return from recursive
                    if (ch != ',' && indexWrapper.getIndex() != source.length() - 1) {
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
                    jsonObj.addKeyAndValue(key, getJsonArrayObject(source, indexWrapper));
                    break;

                default:
                    if (ch == ' ' && !isSaveSpace) {
                        break;
                    }
                    strBuilder.append(ch);
                    break;
            }
            if (Constants.sysCharJson.contains(ch)) {
                prevCh = ch;
            }
            indexWrapper.increaseIndex();
        } while (indexWrapper.getIndex() < source.length());
        return jsonObj;
    }

    private static JsonArrayObject getJsonArrayObject(String source, Wrapper indexWrapper) {
        JsonArrayObject jsonArrayObj = new JsonArrayObject();
        char prevCh = '[';
        boolean isSaveSpace = false;
        StringBuilder strBuilder = new StringBuilder();

        while (indexWrapper.getIndex() < source.length()) {
            char ch = source.charAt(indexWrapper.getIndex());
            switch (ch) {
                case '[':
                    // Ignored
                    break;

                case '{':
                    jsonArrayObj.add(getJsonObject(source, indexWrapper));
                    break;

                case ',':
                case ']':
                    if (prevCh != '{') {
                        jsonArrayObj.add(setValue(strBuilder.toString()));
                    }
                    strBuilder.setLength(0);
                    if (ch != ',' && indexWrapper.getIndex() != source.length() - 1) {
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
            if (Constants.sysCharJson.contains(ch)) {
                prevCh = ch;
            }
            indexWrapper.increaseIndex();
        }
        return jsonArrayObj;
    }
}
