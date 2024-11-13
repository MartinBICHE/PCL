package eu.telecomnancy.pcl.serpython.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import java.util.*;
import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.*;

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
    public void testAnd() throws LexerError {
        String source = "and";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof AndToken);  
    }

    @Test
    public void testFor() throws LexerError {
        String source = "for";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof ForToken);
    }

    @Test
    public void testIf() throws LexerError {
        String source = "if";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof IfToken);
    }

    @Test
    public void testElse() throws LexerError {
        String source = "else";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof ElseToken);
    }

    @Test
    public void testDef() throws LexerError {
        String source = "def";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof DefToken);
    }

    @Test
    public void testPrint() throws LexerError {
        String source = "print";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof PrintToken);
    }

    @Test
    public void testIn() throws LexerError {
        String source = "in";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof InToken);
    }

    @Test
    public void testOr() throws LexerError {
        String source = "or";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof OrToken);
    }

    @Test
    public void testReturn() throws LexerError {
        String source = "return";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof ReturnToken);
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
    public void testFalse() throws LexerError {
        String source = "False";
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = lexer.tokenize();
        assertEquals(1,tokens.size());
        assertTrue(tokens.get(0) instanceof FalseToken);
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
        assertEquals(4,tokens.size());
        assertTrue(tokens.get(1) instanceof BeginToken);
        assertTrue(tokens.get(3) instanceof EndToken);
    }

    @Test
    public void testIndentError() throws LexerError {
        String source = "\n   \n";
        Lexer lexer = new Lexer(source);
        assertThrows(LexerError.class,  lexer::tokenize);
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