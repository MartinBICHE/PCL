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

    @Test 
    public void testComma(){
        String source = ",";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.CommaToken);
    }

    @Test 
    public void testSimpleArray(){
        String source = "[2, 3]";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(5, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.OpeningBracketToken);
        assertTrue(tokens.get(1) instanceof IntegerToken);
        assertTrue(tokens.get(2) instanceof OperatorToken.CommaToken);
        assertTrue(tokens.get(3) instanceof IntegerToken);
        assertTrue(tokens.get(4) instanceof OperatorToken.ClosingBracketToken);
    }

    @Test
    public void testEmptyString() {
        String source = "\"\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "");
    }

    @Test
    public void testSimpleString() {
        String source = "\"Hello World\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "Hello World");
    }

    @Test
    public void testStringNewline() {
        String source = "\"Hello\\nWorld\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "Hello\nWorld");
    }

    @Test
    public void testStringEscapeChar() {
        String source = "\"Hello\\\"World\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "Hello\"World");
    }
}