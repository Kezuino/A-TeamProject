package ateamproject.kezuino.com.github.utility.text.lexer;

import ateamproject.kezuino.com.github.utility.text.lexer.types.Lexem;
import ateamproject.kezuino.com.github.utility.text.lexer.types.Symbol;
import ateamproject.kezuino.com.github.utility.text.lexer.types.Token;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Knows the {@link Lexem Token definitions} and the {@link Symbol symbols}.
 * Uses a {@link LexerEngine} to generate {@link Token lexemes} from text.
 */
public class Lexer<TEngine extends LexerEngine> {
    /**
     * {@link Lexem LexemDefinitions} used by the {@link LexerEngine} to generate {@link Token lexemes} from text.
     */
    private final HashMap<String, Lexem> lexemDefinitions;
    private final HashMap<String, List<Symbol>> symbols;

    /**
     * {@link LexerEngine} used for evaluating text with the definitions of this {@link Lexer}.
     */
    private TEngine engine;

    public Lexer(TEngine engine) {
        this.engine = engine;
        this.engine.setLexer(this);
        this.lexemDefinitions = new HashMap<>();
        this.symbols = new HashMap<>();
    }

    public void addSymbols(String type, String possibleCharacters) {
        List<Symbol> newSymbols = possibleCharacters.chars().mapToObj(c -> new Symbol(type, (char) c)).collect(Collectors.toList());
        if (possibleCharacters.chars().anyMatch(i -> getSymbol((char) i) != null))
            throw new IllegalStateException(String.format("Symbols in '%s' overlap other registered symbols.", possibleCharacters));
        this.symbols.put(type, newSymbols);
    }

    @SuppressWarnings("unchecked")
    public void addLexem(String type, Character start, Character end, String... symbolIdentifiers) {
        Stream.of(symbolIdentifiers).forEach(s -> {
            if (s == null || s.isEmpty())
                throw new IllegalStateException("One or multiple identifiers were null or empty.");
            this.lexemDefinitions.put(type, new Lexem(this, type, start, end, s));
        });
    }

    public void addLexem(String type, String... symbolIdentifiers) {
        addLexem(type, null, null, symbolIdentifiers);
    }

    public Symbol getSymbol(char value) {
        return symbols.values().stream().flatMap(Collection::stream).filter(s -> s.getValue() == value).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public List<Token> run(String text) {
        return engine.run(text.chars().mapToObj(i -> getSymbol((char) i)).collect(Collectors.toList()));
    }

    public List<Symbol> getSymbolsFromIdentifiers(String... identifiers) {
        return symbols.values().stream().flatMap(Collection::stream).filter(s -> Stream.of(identifiers).anyMatch(id -> id.equals(s.getType()))).collect(Collectors.toList());
    }

    public Lexem getLexemFromSymbol(Symbol current, char startCharOfLexem) {
        return getLexemFromSymbol(current, getSymbol(startCharOfLexem));
    }

    /**
     * Gets the {@link Lexem} that allows a character of {@code current} and has the {@link Symbol startingSymbol} of {@code startSymbolOfLexem}.<br />
     * If {@code startSymbolOfLexem} has no {@link Lexem}, {@code startSymbolOfLexem} will be <b>ignored</b>.
     *
     * @param current
     * @param startSymbolOfLexem
     * @return
     */
    @SuppressWarnings("unchecked")
    public Lexem getLexemFromSymbol(Symbol current, Symbol startSymbolOfLexem) {
        if (current == null) return null;

        Collection<Lexem> values = lexemDefinitions.values();
        for (Lexem lexem : values) {
            if (lexem.getStart().equals(startSymbolOfLexem)) {
                if (lexem.getAllowedCharacters().contains(current)) {
                    return lexem;
                }
            }
        }

        for (Lexem lexem : values) {
            if (lexem.getAllowedCharacters().contains(current)) {
                return lexem;
            }
        }

        return null;
    }
}
