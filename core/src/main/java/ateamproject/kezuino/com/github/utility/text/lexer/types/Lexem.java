package ateamproject.kezuino.com.github.utility.text.lexer.types;

import ateamproject.kezuino.com.github.utility.text.lexer.Lexer;

import java.util.HashSet;

public class Lexem<TLexer extends Lexer> {
    private final TLexer lexer;
    private String name;
    private Symbol start;
    private Symbol end;
    private HashSet<Symbol> allowedCharacters;

    @SuppressWarnings("unchecked")
    public Lexem(TLexer lexer, String name, String symbolIdentifierName) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Parameter name must not be null or empty.");
        if (symbolIdentifierName == null || symbolIdentifierName.isEmpty())
            throw new IllegalArgumentException("Parameter symbolIdentifierName must not be null or empty.");

        this.lexer = lexer;
        this.name = name;
        this.allowedCharacters = new HashSet<>(this.lexer.getSymbolsFromIdentifiers(symbolIdentifierName.split("\\s+")));
        if (this.allowedCharacters.size() <= 0)
            throw new IllegalStateException(String.format("Engine does not know any symbols from identifier: '%s'", symbolIdentifierName));
        this.start = lexer.getSymbol(' ');
        this.end = lexer.getSymbol(' ');
    }

    public Lexem(TLexer lexer, String name, Character start, Character end, String symbolIdentifierName) {
        this(lexer, name, symbolIdentifierName);
        this.start = start == null ? lexer.getSymbol(' ') : lexer.getSymbol(start);
        this.end = end == null ? lexer.getSymbol(' ') : lexer.getSymbol(end);
    }

    /**
     * Gets the {@link Symbol symbols} that were set for this {@link Lexem}.
     *
     * @return {@link Symbol symbols} that were set for this {@link Lexem}.
     */
    public HashSet<Symbol> getAllowedCharacters() {
        return allowedCharacters;
    }

    public String getName() {
        return name;
    }

    public Symbol getStart() {
        return start;
    }

    public Symbol getEnd() {
        return end;
    }
}
