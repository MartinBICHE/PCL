package eu.telecomnancy.pcl.serpython.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import eu.telecomnancy.pcl.serpython.ast.ArrayExpression;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.Statement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ReturnStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.PrintStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.AssignmentStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IndexedAssignementStatement;

import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;

import java.lang.reflect.Array;
import java.util.*;

public class StmtTest {

    @Test 
    public void testReturnStatement() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.ReturnToken(null));
        tokens.add(new IntegerToken(42, null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseSimpleStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof ReturnStatement);
        Expression returnValue = ((ReturnStatement)out).getExpression();
        assertNotNull(returnValue);
        assertTrue(returnValue instanceof NumberLitteral);        
        assertEquals(((NumberLitteral)returnValue).getValue(), 42);
    }

    @Test
    public void testReturnStatementWithoutValue() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.ReturnToken(null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseSimpleStatement(parser);
        });
    }

    @Test
    public void testPrintStatement() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.PrintToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseSimpleStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof PrintStatement);
        Expression printValue = ((PrintStatement)out).getExpression();
        assertNotNull(printValue);
        assertTrue(printValue instanceof NumberLitteral);        
        assertEquals(((NumberLitteral)printValue).getValue(), 42);
    }

    @Test
    public void testPrintStatementWithoutValue() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.PrintToken(null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseSimpleStatement(parser);
        });
    }

    @Test
    public void testAssignStatement() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("x", null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new IntegerToken(42, null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseSimpleStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof AssignmentStatement);
        Expression left = ((AssignmentStatement)out).getIdentifier();
        assertNotNull(left);
        assertTrue(left instanceof Identifier);
        assertEquals(((Identifier)left).getName(), "x");
        Expression right = ((AssignmentStatement)out).getExpression();
        assertNotNull(right);
        assertTrue(right instanceof NumberLitteral);
        assertEquals(((NumberLitteral)right).getValue(), 42);
    }

    @Test
    public void testAssignStatementNumberToNumber() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseSimpleStatement(parser);
        });
    }

    @Test 
    public void testAssignStatementNumberToString() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new StringToken("Hello", null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseSimpleStatement(parser);
        });
    }

    @Test 
    public void testAssignStatementStringToNumber() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new StringToken("Hello", null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new IntegerToken(42, null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseSimpleStatement(parser);
        });
    }

    @Test
    public void testAssignStatementFunctionToNumber() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("function".intern(), null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new IntegerToken(42, null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseSimpleStatement(parser);
        });
    }

    @Test
    public void testIndexedAssignStatement() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("x", null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseSimpleStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof IndexedAssignementStatement);
        Expression left = ((IndexedAssignementStatement)out).getLeft();
        assertNotNull(left);
        assertTrue(left instanceof Expression.ArrayGet);
        Expression.ArrayGet arrayGet = (Expression.ArrayGet)left;
        assertEquals(arrayGet.getIndex().getClass(), NumberLitteral.class);
        assertEquals(((NumberLitteral)arrayGet.getIndex()).getValue(), 42);
        Expression right = ((IndexedAssignementStatement)out).getRight();
        assertNotNull(right);
        assertTrue(right instanceof NumberLitteral);
        assertEquals(((NumberLitteral)right).getValue(), 69);
    }

    @Test 
    public void testIndexedAssignStatementWithExpression() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new IdentToken("x", null));
        tokens.add(new OperatorToken.OpeningBracketToken(null));
        tokens.add(new IntegerToken(4, null));
        tokens.add(new OperatorToken.PlusToken(null));
        tokens.add(new IntegerToken(2, null));
        tokens.add(new OperatorToken.ClosingBracketToken(null));
        tokens.add(new OperatorToken.AssignToken(null));
        tokens.add(new IntegerToken(69, null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseSimpleStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof IndexedAssignementStatement);
        IndexedAssignementStatement arraySet = (IndexedAssignementStatement)out;
        Expression.ArrayGet left = (Expression.ArrayGet)arraySet.getLeft();
        assertNotNull(left);
        assertTrue(left instanceof Expression.ArrayGet);
        assertTrue(left.getIndex() instanceof Expression.Addition);
        Expression right = arraySet.getRight();
        assertNotNull(right);
        assertEquals(right.getClass(), NumberLitteral.class);
        Expression expected = new Expression.Addition(
            new NumberLitteral(4),
            new NumberLitteral(2)
        );
        assertEquals(left.getIndex(),expected);
        assertEquals(right, new NumberLitteral(69));
    }

}
