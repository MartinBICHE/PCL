package eu.telecomnancy.pcl.serpython.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Function;
import eu.telecomnancy.pcl.serpython.ast.FunctionCall;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.Statement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ReturnStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.PrintStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.AssignmentStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ForStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IfStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IndexedAssignementStatement;

import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;

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
        tokens.add(new KeywordToken.NewlineToken(null));
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

    @Test
    public void testSimpleIfStatementNoElse() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.IfToken(null));
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.ColonToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.BeginToken(null));
        tokens.add(new KeywordToken.PrintToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new StringToken("Hello", null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.EndToken(null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseIfStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof IfStatement);
        IfStatement ifStmt = (IfStatement)out;
        assertTrue(ifStmt.getExpression() instanceof BooleanLitteral);
        assertEquals(((BooleanLitteral)ifStmt.getExpression()).getValue(), true);
        assertNotNull(ifStmt.getIfBlock());
        assertNull(ifStmt.getElseBlock());
    }

    @Test
    public void testSimpleIfStatementWithElse() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.IfToken(null));
        tokens.add(new BooleanToken.TrueToken(null));
        tokens.add(new OperatorToken.ColonToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.BeginToken(null));
        tokens.add(new KeywordToken.PrintToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new StringToken("Hello", null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.EndToken(null));
        tokens.add(new KeywordToken.ElseToken(null));
        tokens.add(new OperatorToken.ColonToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.BeginToken(null));
        tokens.add(new KeywordToken.PrintToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new StringToken("Bye", null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.EndToken(null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseIfStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof IfStatement);
        IfStatement ifStmt = (IfStatement)out;
        assertTrue(ifStmt.getExpression() instanceof BooleanLitteral);
        assertEquals(((BooleanLitteral)ifStmt.getExpression()).getValue(), true);
        assertNotNull(ifStmt.getIfBlock());
        assertNotNull(ifStmt.getElseBlock());
    }

    @Test
    public void testSimpleForStatement() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.ForToken(null));
        tokens.add(new IdentToken("i", null));
        tokens.add(new KeywordToken.InToken(null));
        tokens.add(new IdentToken("range", null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new OperatorToken.ColonToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.BeginToken(null));
        tokens.add(new KeywordToken.PrintToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IdentToken("i", null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.EndToken(null));
        Parser parser = new Parser(tokens);
        Statement out = StmtParser.parseForStatement(parser);
        assertNotNull(out);
        assertTrue(out instanceof ForStatement);
        ForStatement forStmt = (ForStatement)out;
        assertTrue(forStmt.getExpression() instanceof FunctionCall);
        assertEquals(forStmt.getIdent().getName(), "i");
        assertNotNull(forStmt.getBlock());
    }

    @Test
    public void testWrongForStatement() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.ForToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new KeywordToken.InToken(null));
        tokens.add(new IdentToken("range", null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IntegerToken(42, null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new OperatorToken.ColonToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.BeginToken(null));
        tokens.add(new KeywordToken.PrintToken(null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IdentToken("i", null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.EndToken(null));
        Parser parser = new Parser(tokens);
        assertThrows(ParserError.class, () -> {
            StmtParser.parseForStatement(parser);
        });
    }

    @Test
    public void testFunctionDefinition() throws ParserError {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new KeywordToken.DefToken(null));
        tokens.add(new IdentToken("hello", null));
        tokens.add(new OperatorToken.OpeningParenthesisToken(null));
        tokens.add(new IdentToken("name", null));
        tokens.add(new OperatorToken.ClosingParenthesisToken(null));
        tokens.add(new OperatorToken.ColonToken(null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.BeginToken(null));
        tokens.add(new KeywordToken.ReturnToken(null));
        tokens.add(new IdentToken("name", null));
        tokens.add(new KeywordToken.NewlineToken(null));
        tokens.add(new IndentToken.EndToken(null));
        Parser parser = new Parser(tokens);
        Function out = StmtParser.parseFunction(parser);
        assertNotNull(out);
        assertTrue(out instanceof Function);
        Function function = (Function)out;
        assertEquals(function.getName(), new Identifier("hello"));
        assertNotNull(function.getInstructions());
        assertNotNull(function.getInstructions());
    }
}
