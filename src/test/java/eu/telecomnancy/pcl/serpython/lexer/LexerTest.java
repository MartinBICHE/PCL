package eu.telecomnancy.pcl.serpython.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import java.util.*;

public class LexerTest {

    @Test
    public void testLexerExists() {
        try {
            Class.forName("eu.telecomnancy.pcl.serpython.lexer.Lexer");
        } catch (ClassNotFoundException e) {
            fail("Lexer class does not exist");
        }
    }

    @Test
    public void testSimpleFile() {
        String source = "420";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertEquals(((IntegerToken) tokens.get(0)).getValue(),420);
    }

    @Test
    public void testSimpleFileWithWhitespaceAfter() {
        String source = "420 ";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testSimpleFileWithWhitespaceBefore() {
        String source = " 420";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testSimpleFileWithWhitespaceAfterAndBefore() {
        String source = " 420 ";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testSimpleFileString()  {
        String source = "\"Hello\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(),"Hello");
    }

    @Test 
    public void testLessThan(){
        String source = "<";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.LessToken);
    } 

    @Test 
    public void testCompareLessThan() {
        String source = "45 < 87";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(3,tokens.size());
        assertTrue(tokens.get(0) instanceof IntegerToken);
        assertTrue(tokens.get(1) instanceof OperatorToken.LessToken);
        assertTrue(tokens.get(2) instanceof IntegerToken);
        assertEquals(((IntegerToken) tokens.get(0)).getValue(),45);
        assertEquals(((IntegerToken) tokens.get(2)).getValue(),87);
    }
}