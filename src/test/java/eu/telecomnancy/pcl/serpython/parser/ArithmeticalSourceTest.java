package eu.telecomnancy.pcl.serpython.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.Lexer;

public class ArithmeticalSourceTest {

    @Test 
    public void parseAddSubExpr() throws LexerError, ParserError {
        String source = "49 + 62 - 1337";
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        Expression out = ExprParser.parseExpr(parser);

        Expression expected = new Expression.Subtraction(
            new Expression.Addition(
                new NumberLitteral(49),
                new NumberLitteral(62)
            ),
            new NumberLitteral(1337)
        );
        assertNotNull(out);
        assertEquals(out, expected);
    }   

    @Test
    public void parseMulDivRemExpr() throws LexerError, ParserError {
        String source = "49 * 62 // 1337";
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        Expression out = ExprParser.parseExpr(parser);

        Expression expected = new Expression.Division(
            new Expression.Multiplication(
                new NumberLitteral(49),
                new NumberLitteral(62)
            ),
            new NumberLitteral(1337)
        );
        assertNotNull(out);
        assertEquals(out, expected);
    }    

    @Test
    public void parseBooleanExpr() throws LexerError, ParserError {
        String source = "9 % 2 and 1 != (9-7)";
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        Expression out = ExprParser.parseExpr(parser);

        Expression expected = new Expression.And(
            new Expression.Reminder(
                new NumberLitteral(9),
                new NumberLitteral(2)
            ),
            new Expression.CompareNeq(
                new NumberLitteral(1),
                new Expression.Subtraction(
                    new NumberLitteral(9),
                    new NumberLitteral(7)
                )
            )
        );

        assertNotNull(out);
        assertEquals(out, expected);
    } 

    @Test
    public void parseStringExpr() throws LexerError, ParserError {
        String source = "\"Hello, \\\"world!\" > 45";
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer.tokenize());
        Expression out = ExprParser.parseExpr(parser);

        Expression expected = new Expression.CompareGt(
            new StringLitteral("Hello, \"world!"),
            new NumberLitteral(45)
        );

        assertNotNull(out);
        assertEquals(out, expected);
    } 
}
