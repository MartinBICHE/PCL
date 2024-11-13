package eu.telecomnancy.pcl.serpython.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import java.util.*;
import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.*;

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
        String source = "420";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertEquals(((IntegerToken) tokens.get(0)).getValue(),420);
    }

    @Test
    public void testSimpleFileWithWhitespaceAfter() throws LexerError {
        String source = "420 ";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testSimpleFileWithWhitespaceBefore() throws LexerError {
        String source = " 420";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testSimpleFileWithWhitespaceAfterAndBefore() throws LexerError {
        String source = " 420 ";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testSimpleFileString() throws LexerError {
        String source = "\"Hello\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(),"Hello");
    }

    @Test 
    public void testLessThan() throws LexerError {
        String source = "<";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.LessToken);
    }

    @Test 
    public void testCompareLessThan() throws LexerError {
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
    public void testComma() throws LexerError {
        String source = ",";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.CommaToken);
    }

    @Test 
    public void testSimpleArray() throws LexerError {
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
    public void testEmptyString() throws LexerError {
        String source = "\"\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "");
    }

    @Test
    public void testSimpleString() throws LexerError {
        String source = "\"Hello World\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "Hello World");
    }

    @Test
    public void testStringNewline() throws LexerError {
        String source = "\"Hello\\nWorld\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "Hello\nWorld");
    }

    @Test
    public void testStringEscapeChar() throws LexerError {
        String source = "\"Hello\\\"World\"";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof StringToken);
        assertEquals(((StringToken) tokens.get(0)).getValue(), "Hello\"World");
    }

    @Test
    public void testAnd() throws LexerError {
        String source = "and";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof AndToken);  
    }

    @Test
    public void testNone() throws LexerError {
        String source = "None";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof NoneToken);
    }

    @Test
    public void testTrue() throws LexerError {
        String source = "True";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof TrueToken);
    }

    @Test
    public void testIdent() throws LexerError {
        String source = "aNd_3";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof IdentToken);
        assertEquals(((IdentToken) tokens.get(0)).getName(), "aNd_3");
    }

    @Test
    public void testBeginAndEndIndent() throws LexerError {
        String source = "\n    \n";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(2,tokens.size());
        assertTrue(tokens.get(0) instanceof BeginToken);
        assertTrue(tokens.get(1) instanceof EndToken);
    }



}