package by.grodno.krivosheev.core;

import java.util.Stack;

abstract class Validator {
    /**
     * Checks {@code text} is valid string json
     * @param text JSON string
     * @return true - if text is valid string JSON
     * @throws SyntaxException If the {@code text} expression's syntax is invalid
     */
    public static boolean isValidJsonText(String text) throws SyntaxException {
        int index = 0;
        Stack<Character> stackPrevChar = new Stack<>();
        Stack<Boolean> stackNeedKey = new Stack<>(),
                stackNeedValue = new Stack<>(),
                stackOpenArray = new Stack<>();
        // If symbol '"' is open
        boolean isOpen = false;
        // If this stack empty => throw syntax exception
        stackPrevChar.push(' ');

        while (index < text.length()) {
            char ch = text.charAt(index);
            if (stackPrevChar.isEmpty()) {
                throw new SyntaxException("index: " + (index + 1));
            }
            char prevCh = stackPrevChar.peek();
            switch (ch) {
                case '{':
                    if (isOpen) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '\"'");
                    }
                    if (prevCh != ':' && prevCh != ',' && prevCh != '[' && prevCh != ' ') {
                        throw new SyntaxException("index: " + (index + 1) + " char: " + ch);
                    }
                    stackNeedKey.push(true);
                    break;

                case '"':
                    if (stackNeedKey.isEmpty() && stackNeedValue.isEmpty()) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '{'");
                    }
                    if (prevCh == '}' || prevCh == ']') {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol ','");
                    }
                    isOpen = prevCh != '"';
                    break;

                case ':':
                    if (isOpen) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '\"'");
                    }
                    if (stackNeedKey.isEmpty() || prevCh != '"') {
                        if (!stackOpenArray.isEmpty()) {
                            throw new SyntaxException("index: " + (index + 1) + " need symbol '{'");
                        }
                        throw new SyntaxException("index: " + (index + 1) + " need \"...\"");
                    }
                    stackNeedKey.pop();
                    stackNeedValue.push(true);
                    break;

                case '[':
                    if (isOpen) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '\"'");
                    }
                    if (stackNeedValue.isEmpty()) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol ':'");
                    }
                    stackOpenArray.push(true);
                    break;

                case ']':
                    if (isOpen) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '\"'");
                    }
                    if (stackOpenArray.isEmpty()) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '['");
                    }
                    stackOpenArray.pop();
                    break;

                case ',':
                    if (prevCh == '{') {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol ':'");
                    }
                    if (!stackOpenArray.isEmpty()) {
                        break;
                    }
                    stackNeedKey.push(true);
                case '}':
                    if (isOpen) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '\"'");
                    }
                    if (stackNeedValue.isEmpty()) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol ':'");
                    }
                    if (prevCh == ',') {
                        throw new SyntaxException("index: " + (index + 1) + " previous symbol cannot be ','");
                    }
                    stackNeedValue.pop();
                    break;
            }
            if (Utils.sysCharJson.contains(ch)) {
                stackPrevChar.push(ch);
            }
            index++;
        }
        if (!stackNeedKey.isEmpty()) {
            throw new SyntaxException("open symbol '{' need symbol '}'");
        }
        if (!stackNeedValue.isEmpty()) {
            throw new SyntaxException("after symbol ':' need symbol ',' or '}'");
        }
        if (!stackOpenArray.isEmpty()) {
            throw new SyntaxException("open symbol '[' need symbol ']'");
        }
        return true;
    }

    /**
     * Checks {@code text} is valid string xml
     * @param text XML string
     * @return true - if text is valid string XML
     * @throws SyntaxException If the {@code text} expression's syntax is invalid
     */
    public static boolean isValidXmlText(String text) throws SyntaxException {
        int index = 0;
        Stack<Character> stackPrevChar = new Stack<>();
        // If open symbol '<'
        boolean isOpenTag = false;
        Stack<String> stackNeedCloseTag = new Stack<>();
        StringBuilder strBuilder = new StringBuilder();
        // If this stack empty => throw syntax exception
        stackPrevChar.push(' ');

        while (index < text.length()) {
            char ch = text.charAt(index);
            if (stackPrevChar.isEmpty()) {
                throw new SyntaxException("index: " + (index + 1));
            }
            char prevCh = stackPrevChar.peek();
            switch (ch) {
                case '<':
                    if (isOpenTag) {
                        throw new SyntaxException("index: " + (index + 1) + " previous symbol cannot be '<'");
                    }
                    isOpenTag = true;
                    strBuilder.setLength(0);
                    break;

                case '>':
                    if (!isOpenTag) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '<'");
                    }
                    isOpenTag = false;
                    if (prevCh == '<') {
                        stackNeedCloseTag.push(strBuilder.toString());
                    }
                    if (prevCh == '/') {
                        if (!strBuilder.toString().equals(stackNeedCloseTag.peek())) {
                            throw new SyntaxException("index: " + (index + 1) + " opened tag '<" + stackNeedCloseTag.peek() + ">' closing tag '</" +
                                    strBuilder + ">'");
                        }
                        stackNeedCloseTag.pop();
                    }
                    break;

                case '/':
                    if (!isOpenTag) {
                        throw new SyntaxException("index: " + (index + 1) + " need symbol '<'");
                    }
                    if (stackNeedCloseTag.isEmpty()) {
                        throw new SyntaxException("index: " + index + " need open tag '<...>'");
                    }
                    break;

                default:
                    if (prevCh == '<' || prevCh == '/') {
                        strBuilder.append(ch);
                    }
                    break;
            }
            if (Utils.sysCharXml.contains(ch)) {
                stackPrevChar.push(ch);
            }
            index++;
        }
        if (isOpenTag) {
            throw new SyntaxException("open symbol '<' need symbol '>'");
        }
        if (!stackNeedCloseTag.isEmpty()) {
            throw new SyntaxException("opened tag '<" + stackNeedCloseTag.peek() + ">' need closing tag '</" + stackNeedCloseTag.peek() + ">'");
        }
        return true;
    }
}
