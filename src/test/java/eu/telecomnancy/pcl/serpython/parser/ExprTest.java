package eu.telecomnancy.pcl.serpython.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import java.util.*;

public class ExprTest {

    @Test
    public void testNumberLitteral() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(29, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof NumberLitteral);
        assertEquals(((NumberLitteral) out).getValue(), 29);
    }

    @Test
    public void testStringLitteral() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new StringToken("Hello, world!".intern(), null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof StringLitteral);
        assertEquals(((StringLitteral) out).getValue(), "Hello, world!".intern());
    }

    @Test
    public void testBooleanLitteralTrue() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.TrueToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof BooleanLitteral);
        assertEquals(((BooleanLitteral) out).getValue(), true);
    }

    @Test
    public void testBooleanLitteralFalse() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.FalseToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof BooleanLitteral);
        assertEquals(((BooleanLitteral) out).getValue(), false);
    }

    @Test
    public void testNoneLitteral() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new NoneToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof NoneLitteral);
    }
}