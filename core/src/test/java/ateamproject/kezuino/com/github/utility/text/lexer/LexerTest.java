package ateamproject.kezuino.com.github.utility.text.lexer;

import ateamproject.kezuino.com.github.utility.text.lexer.types.Symbol;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LexerTest {

    private LexerEngine engine;
    private Lexer<?> lexer;
    private String[] codes = new String[] {
            "help",
            "clients -fname \"Test\" -kick",
            "     whut topkek ha\":D\\\" \"\" \"   \r\n",
            "exit -d 10"
    };

    @Before
    public void setUp() throws Exception {
        engine = new LexerEngine();
        lexer = new Lexer<>(engine);

        lexer.addSymbols("character", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        lexer.addSymbols("character_special", "`~!@#$%^&*()_:;'");
        lexer.addSymbols("newline", "\r\n");
        lexer.addSymbols("whitespace", " ");
        lexer.addSymbols("quote", "\"");
        lexer.addSymbols("number", "0123456789");
        lexer.addSymbols("dot", ".");
        lexer.addSymbols("minus", "-");
        lexer.addSymbols("plus", "+");
        lexer.addSymbols("equals", "=");
        lexer.addSymbols("slash", "\\");

        lexer.addLexem("Identifier", "character minus");
        lexer.addLexem("IntegerLiteral", "number dot");
        lexer.addLexem("StringLiteral", '"', '"', "character character_special number dot minus plus equals slash quote whitespace");
        lexer.addLexem("SpaceLiteral", "whitespace");
        lexer.addLexem("Newline", "newline");
    }

    @Test
    public void testGetSymbolsFromIdentifiers() throws Exception {
        assertThat(Arrays.asList(new Symbol("number", '0'), new Symbol("number", '1'), new Symbol("number", '2'), new Symbol("number", '3'), new Symbol("number", '4'), new Symbol("number", '5'), new Symbol("number", '6'), new Symbol("number", '7'), new Symbol("number", '8'), new Symbol("number", '9'), new Symbol("dot", '.')), is(Arrays.toString(lexer.getSymbolsFromIdentifiers("number", "dot").toArray())));
        assertThat(Arrays.asList(new Symbol("number", '0'), new Symbol("number", '1'), new Symbol("number", '2'), new Symbol("number", '3'), new Symbol("number", '4'), new Symbol("number", '5'), new Symbol("number", '6'), new Symbol("number", '7'), new Symbol("number", '8'), new Symbol("number", '9'), new Symbol("dot", '.'), new Symbol("plus", '+'), new Symbol("equals", '=')), is(Arrays.toString(lexer.getSymbolsFromIdentifiers("dot", "number", "plus", "equals").toArray())));

        try {
            lexer.getSymbolsFromIdentifiers("number", "test", "dot");
            fail("Identifier should not exist in lexer.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testRun() throws Exception {
        // Test quality.
        for (Object o : lexer.run(codes[2]).toArray()) {
            System.out.println(o);
        }

        // Test performance.
//        int amount = 30000;
//        double time = System.nanoTime();
//        for (int i = 0; i < amount; i++) {
//            lexer.run(codes[1]);
//        }
//        time = (System.nanoTime() - time) / 1000000000.0;
//        assertTrue("Lexer performance is to slow.", time < 1);
//        System.out.println(amount + " times took: " + time + " seconds.");
    }
}