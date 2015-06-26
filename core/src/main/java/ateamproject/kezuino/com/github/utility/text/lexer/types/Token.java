package ateamproject.kezuino.com.github.utility.text.lexer.types;

import java.util.Arrays;
import java.util.List;

public class Token {
    private String type;
    private List<Symbol> symbols;

    private Token(String type) {
        this.type = type;
    }

    public Token(String type, Symbol... symbols) {
        this(type);
        if (symbols == null) throw new IllegalArgumentException("Parameter symbols must not be null.");
        if (symbols.length <= 0)
            throw new IllegalArgumentException("Parameter symbols must have at least one element.");
        this.symbols = Arrays.asList(symbols);
    }

    public Token(String type, List<Symbol> symbols) {
        this(type);
        if (symbols == null) throw new IllegalArgumentException("Parameter symbols must not be null.");
        if (symbols.size() <= 0)
            throw new IllegalArgumentException("Parameter symbols must have at least one element.");
        this.symbols = symbols;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * Gets the textual representation of this {@link Token}.
     *
     * @return Textual representation of this {@link Token}.
     */
    public String getText() {
        StringBuilder sb = new StringBuilder();
        symbols.forEach(sb::append);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", symbols=" + symbols +
                '}';
    }
}
