package ateamproject.kezuino.com.github.utility.text.lexer.types;

public class Symbol {
    private char value;
    private String type;

    public Symbol(String type, char value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        String displayValue = value == '\r' ? "\\r" : value == '\n' ? "\\n" : String.valueOf(value);

        return "Symbol{" +
                "type=" + type +
                ", value='" + (displayValue) + "'" +
                '}';
    }
}
