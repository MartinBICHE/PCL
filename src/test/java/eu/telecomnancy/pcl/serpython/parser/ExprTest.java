package eu.telecomnancy.pcl.serpython.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import eu.telecomnancy.pcl.serpython.ast.ArrayExpression;
import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.FunctionCall;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import java.util.*;

public class ExprTest {

    @Test
    public void testNumberLitteral() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(29, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof NumberLitteral);
        assertEquals(((NumberLitteral) out).getValue(), 29);
    }

    @Test
    public void testStringLitteral() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new StringToken("Hello, world!".intern(), null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof StringLitteral);
        assertEquals(((StringLitteral) out).getValue(), "Hello, world!".intern());
    }

    @Test
    public void testBooleanLitteralTrue() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.TrueToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof BooleanLitteral);
        assertEquals(((BooleanLitteral) out).getValue(), true);
    }

    @Test
    public void testBooleanLitteralFalse() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.FalseToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof BooleanLitteral);
        assertEquals(((BooleanLitteral) out).getValue(), false);
    }

    @Test
    public void testNoneLitteral() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new NoneToken(null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);
        assertTrue(out instanceof NoneLitteral);
    }

    @Test
    public void testParenthesedLitteral() throws ParserError {
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
    public void testNegatedLitteral() throws ParserError {
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
    public void testSimpleAdditionExpr() throws ParserError {
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
    public void testAdditiveAdditionExpr() throws ParserError {
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
    public void testAdditiveSubstractionExpr() throws ParserError {
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
    public void testMixedAddSubExpr() throws ParserError {
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
                new Expression.Addition(new Expression.Subtraction(new NumberLitteral(42), new NumberLitteral(69)),
                        new NumberLitteral(1337)),
                new NumberLitteral(5147));

        System.err.println(out);
        System.err.println(expected);

        assertEquals(out, expected);
    }

    @Test
    public void testSimpleMultiplicationExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Multiplication(new NumberLitteral(42), new NumberLitteral(69));

        assertEquals(out, expected);
    }

    @Test
    public void testAdditiviteMultiplicationExpr() throws ParserError {
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
                new Expression.Multiplication(new NumberLitteral(42), new NumberLitteral(69)),
                new NumberLitteral(1337));

        assertEquals(out, expected);
    }

    @Test
    public void testMixedMulDivRemExpr() throws ParserError {
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
                new Expression.Division(new Expression.Multiplication(new NumberLitteral(42), new NumberLitteral(69)),
                        new NumberLitteral(1337)),
                new NumberLitteral(5147));

        assertEquals(out, expected);
    }

    @Test
    public void testAddMulExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.MultiplyToken(null));
        tokens.add(new IntegerToken(1337, null));
        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Addition(new NumberLitteral(42),
                new Expression.Multiplication(new NumberLitteral(69), new NumberLitteral(1337)));

        assertEquals(out, expected);
    }

    @Test
    public void testAddSubMulDivExpr() throws ParserError {
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
                new Expression.Addition(new NumberLitteral(42), new NumberLitteral(69)),
                new Expression.Division(
                        new Expression.Multiplication(new NumberLitteral(1337), new NumberLitteral(5147)),
                        new NumberLitteral(42)));

        assertEquals(out, expected);
    }

    @Test
    public void testEmptyParenthesis() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            ExprParser.parseExpr(parser);
        });
    }

    @Test
    public void testEmptyExpr() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            ExprParser.parseExpr(parser);
        });
    }

    @Test
    public void testEqualityExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.EqualToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.CompareEq(new NumberLitteral(42), new NumberLitteral(69));
        assertEquals(out, expected);
    }

    @Test
    public void testAssociativeEqualityExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.EqualToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.EqualToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.EqualToken(null));
        tokens.add(new IntegerToken(5147, null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.CompareEq(
            new Expression.CompareEq(
                new Expression.CompareEq(
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
    public void testBunchOfComparisonExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.LessToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.LessEqualToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.GreaterToken(null));
        tokens.add(new IntegerToken(5147, null));
        tokens.add(new OperatorToken.GreaterEqualToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.EqualToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.NotEqualToken(null));
        tokens.add(new IntegerToken(1337, null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.CompareNeq(
            new Expression.CompareEq(
                new Expression.CompareGte(
                    new Expression.CompareGt(
                        new Expression.CompareLte(
                            new Expression.CompareLt(
                                new NumberLitteral(42),
                                new NumberLitteral(69)
                            ),
                            new NumberLitteral(1337)
                        ),
                        new NumberLitteral(5147)
                    ),
                    new NumberLitteral(42)
                ),
                new NumberLitteral(69)
            ),
            new NumberLitteral(1337)
        );
        assertEquals(out, expected);
    }

    @Test 
    public void testComparePriorityExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.LessToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.NotEqualToken(null));
        tokens.add(new BooleanToken.TrueToken(null));

        Parser parser = new Parser(tokens);
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.CompareNeq(
            new Expression.CompareLt(
                new NumberLitteral(42),
                new Expression.Addition(
                    new NumberLitteral(69),
                    new NumberLitteral(1337)
                )
            ),
            new BooleanLitteral(true)
        );
        assertEquals(out, expected);
    }

    @Test
    public void testSimpleAndExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.AndToken(null));
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new BooleanToken.FalseToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.And(
            new BooleanLitteral(true),
            new Expression.Not(new BooleanLitteral(false))
        );
        assertEquals(out, expected);
    }

    @Test
    public void testSimpleOrExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.OrToken(null));
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new BooleanToken.FalseToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Or(
            new BooleanLitteral(true),
            new Expression.Not(new BooleanLitteral(false))
        );
        assertEquals(out, expected);
    }

    @Test
    public void testMixedAndOrNotExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.AndToken(null));
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new BooleanToken.FalseToken(null));
        tokens.add(new OperatorToken.OrToken(null));
        tokens.add(new BooleanToken.TrueToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Or(
            new Expression.And(
                new BooleanLitteral(true),
                new Expression.Not(new BooleanLitteral(false))
            ),
            new BooleanLitteral(true)
        );
        assertEquals(out, expected);
    }

    @Test
    public void testMixedNotAnd() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.AndToken(null));
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new BooleanToken.FalseToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.And(
            new Expression.Not(new BooleanLitteral(true)),
            new Expression.Not(new BooleanLitteral(false))
        );
        assertEquals(out, expected);
    }

    @Test
    public void testMixedNotAnd2() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.AndToken(null));
        tokens.add(new OperatorToken.NotToken(null));
        tokens.add(new BooleanToken.FalseToken(null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.Not(
            new Expression.And(
                new BooleanLitteral(true),
                new Expression.Not(new BooleanLitteral(false))
            )
        );
        assertEquals(out, expected);
    }

    @Test
    public void testSimpleArrayGet() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("array".intern(), null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.ArrayGet(
            new Identifier("array".intern()),
            new NumberLitteral(42)
        );
        assertEquals(out, expected);
    }

    @Test
    public void testMultiDimentionalArrayGet() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("array".intern(), null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.ArrayGet(
            new Expression.ArrayGet(
                new Identifier("array".intern()),
                new NumberLitteral(42)
            ),
            new NumberLitteral(69)
        );
        assertEquals(expected, out);
    }

    @Test
    public void testArrayInArrayIndex() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("array".intern(), null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IdentToken("index".intern(), null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new Expression.ArrayGet(
            new Identifier("array".intern()),
            new Expression.ArrayGet(
                new Identifier("index".intern()),
                new NumberLitteral(42)
            )
        );
        assertEquals(expected, out);
    }

    @Test 
    public void testSimpleArrayLitteralExpr1() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new ArrayExpression(new Expression[0]);
        assertEquals(expected, out);
    }

    @Test 
    public void testSimpleArrayLitteralExpr2() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        assertThrows(
            ParserError.class,
            () -> ExprParser.parseExpr(parser)
        );  
    }

    @Test
    public void testSimpleArrayLitteralExpr3() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new ArrayExpression(new Expression[] { new NumberLitteral(42) });
        assertEquals(expected, out);
    }

    @Test
    public void testSimpleArrayLitteralExpr4() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new IntegerToken(1337, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new ArrayExpression(
            new Expression[] { 
                new NumberLitteral(42),
                new NumberLitteral(69),
                new NumberLitteral(1337)
            }
        );
        assertEquals(expected, out);
    }

    @Test
    public void testArrayInArrayLitteralExpr() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new ArrayExpression(
            new Expression[] { 
                new ArrayExpression(new Expression[] { new NumberLitteral(42) }),
                new ArrayExpression(new Expression[] { new NumberLitteral(69) })
            }
        );
        assertEquals(expected, out);
    }

    @Test
    public void testArrayInArrayLitteralExpr2() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IdentToken("index".intern(), null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new ArrayExpression(
            new Expression[] { 
                new ArrayExpression(new Expression[] { new NumberLitteral(42) }),
                new ArrayExpression(new Expression[] { 
                    new Expression.ArrayGet(
                        new Identifier("index".intern()),
                        new NumberLitteral(69)
                    )
                })
            }
        );
        assertEquals(expected, out);
    }

    @Test 
    public void testSimpleFunctionCallExpr1() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("function".intern(), null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new FunctionCall("function".intern(), new Expression[0]);
        assertEquals(expected, out);
    }

    @Test 
    public void testSimpleFunctionCallExpr2() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("function".intern(), null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new FunctionCall("function".intern(), new Expression[] { new NumberLitteral(42) });
        assertEquals(expected, out);
    }

    @Test 
    public void testSimpleFunctionCallExpr3() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("function".intern(), null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new IntegerToken(69, null));
        tokens.add(new OperatorToken.CommaToken(null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        
        Expression out = ExprParser.parseExpr(parser);
        assertNotNull(out);

        Expression expected = new FunctionCall("function".intern(), new Expression[] { new NumberLitteral(42), new NumberLitteral(69) });
        assertEquals(expected, out);
    }
}