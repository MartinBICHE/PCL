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
    public void testPlus() throws LexerError {
        String source = "+";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.PlusToken);
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
    public void testMinus() throws LexerError {
        String source = "-";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.MinusToken);
    }

    @Test 
    public void testMultiply() throws LexerError {
        String source = "*";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.MultiplyToken);
    }

    @Test 
    public void testDivide() throws LexerError {
        String source = "//";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.DivideToken);
    }

    @Test 
    public void testModulo() throws LexerError {
        String source = "%";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.ModuloToken);
    }

    @Test 
    public void testLess() throws LexerError {
        String source = "<";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.LessToken);
    }

    @Test 
    public void testGreater() throws LexerError {
        String source = ">";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.GreaterToken);
    }

    @Test 
    public void testAssign() throws LexerError {
        String source = "=";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.AssignToken);
    }

    @Test 
    public void testNotEqual() throws LexerError {
        String source = "!=";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.NotEqualToken);
    }

    @Test 
    public void testLessEqual() throws LexerError {
        String source = "<=";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.LessEqualToken);
    }

    @Test 
    public void testGreaterEqual() throws LexerError {
        String source = ">=";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.GreaterEqualToken);
    }

    @Test
    public void testEqual() throws LexerError {
        String source = "==";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.EqualToken);
    }

    @Test
    public void testOpeningParenthesis() throws LexerError {
        String source = "(";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.OpeningParenthesisToken);
    }

    @Test
    public void testClosingParenthesis() throws LexerError {
        String source = ")";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.ClosingParenthesisToken);
    }

    @Test 
    public void testOpeningBracket() throws LexerError {
        String source = "[";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.OpeningBracketToken);
    }

    @Test 
    public void testClosingBracket() throws LexerError {
        String source = "]";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.ClosingBracketToken);
    }

    @Test 
    public void testColon() throws LexerError {
        String source = ":";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1, tokens.size());
        assertTrue(tokens.get(0) instanceof OperatorToken.ColonToken);
    }
}