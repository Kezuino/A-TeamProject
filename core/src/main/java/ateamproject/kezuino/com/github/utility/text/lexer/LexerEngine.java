package ateamproject.kezuino.com.github.utility.text.lexer;

import ateamproject.kezuino.com.github.utility.text.lexer.types.Lexem;
import ateamproject.kezuino.com.github.utility.text.lexer.types.Symbol;
import ateamproject.kezuino.com.github.utility.text.lexer.types.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LexerEngine {
    /**
     * Current name of the identifier this {@link LexerEngine} is on.
     */
    protected String currentIdentifier;
    /**
     * Represents the text given to the {@link LexerEngine} by the {@link Lexer} as {@link Symbol symbols}.
     */
    protected Symbol[] symbols;
    /**
     * Current index of the {@link LexerEngine} on the {@link #symbols}.
     */
    protected int index;

    protected Lexer lexer;

    public LexerEngine() {
    }

    public List<Token> run(Symbol... symbols) {
        if (lexer == null) throw new IllegalStateException("No lexer was set for this engine. See setLexer().");
        if (symbols == null) throw new IllegalArgumentException("Parameter symbols must not be null.");
        if (symbols.length <= 0)
            throw new IllegalArgumentException("Parameter symbols must contain at least one element.");

        this.currentIdentifier = null;
        this.symbols = symbols;
        this.index = 0;

        System.out.println(Arrays.asList(this.symbols).stream().map(c -> c.getValue()).collect(Collectors.toList()));

        // Generate tokens.
        List<Token> result = new ArrayList<>();
        Token token = next();
        while (token != null) {
            result.add(token);
            token = next();
        }
        return result;
    }

    public List<Token> run(List<Symbol> symbols) {
        return run(symbols.toArray(new Symbol[symbols.size()]));
    }

    /**
     * Generates a new {@link Token} from the {@link #symbols}.
     *
     * @return {@link Token} from the {@link #symbols}.
     */
    public Token next() {
        // Top at end of line.
        if (index >= symbols.length) return null;

        // Convert lexem to token.
        int tokenStartIndex = index;
        Lexem lexemStart = lexer.getLexemFromSymbol(symbols[index], symbols[index]);
        Lexem lexemPrevious = null;
        Lexem lexemCurrent = null;
        Lexem lexemNext = null;

        // TODO: Index 28 (Fix that space is in both StringLiteral and Whitespace)!!!!
        for (; index < symbols.length; index++) {

            Symbol current = symbols[index];
            Symbol next = index + 1 < symbols.length ? symbols[index + 1] : null;

            lexemPrevious = lexemCurrent;
            lexemCurrent = lexer.getLexemFromSymbol(current, lexemStart.getStart());
            lexemNext = lexer.getLexemFromSymbol(next, lexemStart.getStart());

            currentIdentifier = lexemCurrent.getName();
            String nextIdentifier = lexemNext != null ? lexemNext.getName() : "";

            if (current.equals(next) && lexemCurrent.equals(lexemPrevious)) {
                nextIdentifier = null;
            }

            if (currentIdentifier.equals(nextIdentifier)) continue;

            return new Token(currentIdentifier, Arrays.copyOfRange(symbols, tokenStartIndex, ++index));
        }
        return null;
    }

    /**
     * Sets the {@link Lexer} for this {@link LexerEngine}.
     *
     * @param lexer     {@link Lexer} that knows the {@link Symbol symbols} and {@link Lexem lexemes}.
     * @param <TEngine> Type of {@link LexerEngine}.
     */
    public <TEngine extends LexerEngine> void setLexer(Lexer<TEngine> lexer) {
        this.lexer = lexer;
    }
}
