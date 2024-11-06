package eu.telecomnancy.pcl.serpython.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import java.util.*;
import eu.telecomnancy.pcl.serpython.errors.LexerError;

public class LexerTest {

    @Test
    public void testLexerExists() throws LexerError{
        try {
            Class.forName("eu.telecomnancy.pcl.serpython.lexer.Lexer");
        } catch (ClassNotFoundException e) {
            fail("Lexer class does not exist");
        }
    }

    @Test
    public void testSimpleFile() throws LexerError {
        String source = "42";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }
}