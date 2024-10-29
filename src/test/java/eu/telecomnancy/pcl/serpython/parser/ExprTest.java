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

    @Test
    public void testParenthesedLitteral() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof NumberLitteral);
        assertEquals(((NumberLitteral) out).getValue(), 42);
    }

    @Test
    public void testNegatedLitteral() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.MinusToken(null));
        tokens.add(new IntegerToken(42, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof Expression.Negation);
        assertEquals(((Expression.Negation) out).getInner().getClass(), NumberLitteral.class);
        assertEquals(((NumberLitteral) ((Expression.Negation) out).getInner()).getValue(), 42);
    }

    @Test
    public void testSimpleAdditionExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof Expression.Addition);
        assertEquals(((Expression.Addition) out).getLeft().getClass(), NumberLitteral.class);
        assertEquals(((Expression.Addition) out).getRight().getClass(), NumberLitteral.class);
        assertEquals(((NumberLitteral) ((Expression.Addition) out).getLeft()).getValue(), 42);
        assertEquals(((NumberLitteral) ((Expression.Addition) out).getRight()).getValue(), 69);
    }

    @Test
    public void testAdditiveAdditionExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(1337, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof Expression.Addition);
        assertEquals(((Expression.Addition) out).getLeft().getClass(), Expression.Addition.class);
        assertEquals(((Expression.Addition) out).getRight().getClass(), NumberLitteral.class);
    }

    @Test
    public void testAdditiveSubstractionExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.MinusToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.MinusToken(null));
        tokens.add(new IntegerToken(1337, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof Expression.Subtraction);
        assertEquals(((Expression.Subtraction) out).getLeft().getClass(), Expression.Subtraction.class);
        assertEquals(((Expression.Subtraction) out).getRight().getClass(), NumberLitteral.class);
    }

    @Test
    public void testMixedAddSubExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.MinusToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.MinusToken(null));
        tokens.add(new IntegerToken(5147, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Subtraction(
            new Expression.Addition(
                new Expression.Subtraction(
                    new NumberLitteral(42),
                    new NumberLitteral(69)
                ),
                new NumberLitteral(1337)
            ),
            new NumberLitteral(5147)
        );

        System.err.println(out);
        System.err.println(expected);

        assertEquals(out, expected);
    }

    @Test 
    public void testSimpleMultiplicationExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        
        Expression expected = new Expression.Multiplication(
            new NumberLitteral(42),
            new NumberLitteral(69)
        );

        assertEquals(out, expected);
    }

    @Test
    public void testAdditiviteMultiplicationExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(1337, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        
        Expression expected = new Expression.Multiplication(
            new Expression.Multiplication(
                new NumberLitteral(42),
                new NumberLitteral(69)
            ),
            new NumberLitteral(1337)
        );

        assertEquals(out, expected);
    }

    @Test
    public void testMixedMulDivRemExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.DivideToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.ModuloToken(null));
        tokens.add(new IntegerToken(5147, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Reminder(
            new Expression.Division(
                new Expression.Multiplication(
                    new NumberLitteral(42),
                    new NumberLitteral(69)
                ),
                new NumberLitteral(1337)
            ),
            new NumberLitteral(5147)
        );

        assertEquals(out, expected);
    }

    @Test
    public void testAddMulExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(1337, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Addition(
            new NumberLitteral(42),
            new Expression.Multiplication(
                new NumberLitteral(69),
                new NumberLitteral(1337)
            )
        );

        assertEquals(out, expected);
    }

    @Test
    public void testAddSubMulDivExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.MinusToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(5147, null));
        tokens.add(new OperatorToken.DivideToken(null));
        tokens.add(new IntegerToken(42, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Subtraction(
            new Expression.Addition(
                new NumberLitteral(42),
                new NumberLitteral(69)
            ),
            new Expression.Division(
                new Expression.Multiplication(
                    new NumberLitteral(1337),
                    new NumberLitteral(5147)
                ),
                new NumberLitteral(42)
            )
        );

        assertEquals(out, expected);
    }
}