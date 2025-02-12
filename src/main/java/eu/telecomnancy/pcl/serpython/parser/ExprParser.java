package eu.telecomnancy.pcl.serpython.parser;

import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.ast.ArrayExpression;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.FunctionCall;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IdentToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IntegerToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.NoneToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.StringToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

/**
 * The ExprParser class provides methods to parse expressions in a given parser.
 */
public class ExprParser {

    /**
     * Parses an expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseExpr(Parser parser) throws ParserError {
        return parseOrExpr(parser);
    }

    /**
     * Parses an or expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed or expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseOrExpr(Parser parser) throws ParserError {
        Expression left = parseAndExpr(parser);
        Token curToken = parser.peek();
        while (curToken instanceof OperatorToken.OrToken) {
            parser.consume();
            left = new Expression.Or(left, parseAndExpr(parser));
            curToken = parser.peek();
        }
        return left;
    }

    /**
     * Parses an and expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed and expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseAndExpr(Parser parser) throws ParserError {
        Expression left = parseNotExpr(parser);
        Token curToken = parser.peek();
        while (curToken instanceof OperatorToken.AndToken) {
            parser.consume();
            left = new Expression.And(left, parseNotExpr(parser));
            curToken = parser.peek();
        }
        return left;
    }

    /**
     * Parses a not expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed not expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseNotExpr(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        if (curToken instanceof OperatorToken.NotToken) {
            parser.consume();
            return new Expression.Not(parseCompareExpr(parser));
        } else {
            return parseCompareExpr(parser);
        }
    }

    /**
     * Parses a compare expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed compare expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseCompareExpr(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }

        Expression left = parseAddSubExpr(parser);
        curToken = parser.peek();
        while (curToken instanceof OperatorToken.LessToken || curToken instanceof OperatorToken.LessEqualToken
                || curToken instanceof OperatorToken.GreaterToken || curToken instanceof OperatorToken.GreaterEqualToken
                || curToken instanceof OperatorToken.EqualToken || curToken instanceof OperatorToken.NotEqualToken) {
            if (curToken instanceof OperatorToken.LessToken) {
                parser.consume();
                left = new Expression.CompareLt(left, parseAddSubExpr(parser));
            } else if (curToken instanceof OperatorToken.LessEqualToken) {
                parser.consume();
                left = new Expression.CompareLte(left, parseAddSubExpr(parser));
            } else if (curToken instanceof OperatorToken.GreaterToken) {
                parser.consume();
                left = new Expression.CompareGt(left, parseAddSubExpr(parser));
            } else if (curToken instanceof OperatorToken.GreaterEqualToken) {
                parser.consume();
                left = new Expression.CompareGte(left, parseAddSubExpr(parser));
            } else if (curToken instanceof OperatorToken.EqualToken) {
                parser.consume();
                left = new Expression.CompareEq(left, parseAddSubExpr(parser));
            } else if (curToken instanceof OperatorToken.NotEqualToken) {
                parser.consume();
                left = new Expression.CompareNeq(left, parseAddSubExpr(parser));
            }
            curToken = parser.peek();
        }
        return left;
    }

    /**
     * Parses an add/sub expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed add/sub expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseAddSubExpr(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        Expression left = parseMulDivRemExpr(parser);
        curToken = parser.peek();
        while (curToken instanceof OperatorToken.PlusToken || curToken instanceof OperatorToken.MinusToken) {
            if (curToken instanceof OperatorToken.PlusToken) {
                parser.consume();
                left = new Expression.Addition(left, parseMulDivRemExpr(parser));
            } else if (curToken instanceof OperatorToken.MinusToken) {
                parser.consume();
                left = new Expression.Subtraction(left, parseMulDivRemExpr(parser));
            }
            curToken = parser.peek();
        }
        return left;
    }

    /**
     * Parses a mul/div/rem expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed mul/div/rem expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseMulDivRemExpr(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        Expression left = parseNegExpr(parser);
        curToken = parser.peek();
        while (curToken instanceof OperatorToken.MultiplyToken || curToken instanceof OperatorToken.DivideToken
                || curToken instanceof OperatorToken.ModuloToken) {
            if (curToken instanceof OperatorToken.MultiplyToken) {
                parser.consume();
                left = new Expression.Multiplication(left, parseNegExpr(parser));
            } else if (curToken instanceof OperatorToken.DivideToken) {
                parser.consume();
                left = new Expression.Division(left, parseNegExpr(parser));
            } else if (curToken instanceof OperatorToken.ModuloToken) {
                parser.consume();
                left = new Expression.Reminder(left, parseNegExpr(parser));
            }
            curToken = parser.peek();
        }
        return left;
    }

    /**
     * Parses a negation expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed negation expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseNegExpr(Parser parser) throws ParserError {
        if (parser.peek() == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        if (parser.peek() instanceof OperatorToken.MinusToken) {
            parser.consume();
            return new Expression.Negation(parseBracketExpr(parser));
        } else {
            return parseBracketExpr(parser);
        }
    }

    /**
     * Parses a array access (bracket expr) expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed bracket expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseBracketExpr(Parser parser) throws ParserError {
        Expression left = parseParenthesisExpr(parser);
        Token curToken = parser.peek();
        if (curToken == null) {
            return left;
        }
        while (curToken instanceof OperatorToken.OpeningBracketToken) {
            parser.consume();
            Expression index = parseExpr(parser);
            if (parser.peek() instanceof OperatorToken.ClosingBracketToken) {
                parser.consume();
                left = new Expression.ArrayGet(left, index);
            } else {
                throw new ParserError(ParserErrorKind.ExpectedClosingBracket, parser.getPosition(), parser.peek());
            }
            curToken = parser.peek();
        }
        return left;
    }

    /**
     * Parses a parenthesis expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed parenthesis expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseParenthesisExpr(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        if (curToken instanceof OperatorToken.OpeningParenthesisToken) {
            parser.consume();
            Expression expr = parseExpr(parser);
            if (parser.peek() instanceof OperatorToken.ClosingParenthesisToken) {
                parser.consume();
                return expr;
            }
            throw new ParserError(ParserErrorKind.ExpectedClosingParenthesis, parser.getPosition(), parser.peek());
        } else {
            if (curToken instanceof StringToken) {
                return AtomParser.parseStringLitteral(parser);
            } else if (curToken instanceof IntegerToken) {
                return AtomParser.parseNumberLitteral(parser);
            } else if (curToken instanceof BooleanToken) {
                return AtomParser.parseBooleanLitteral(parser);
            } else if (curToken instanceof NoneToken) {
                return AtomParser.parseNoneLitteral(parser);
            } else if (curToken instanceof IdentToken) {
                Expression ident =  AtomParser.parseIdentifier(parser);
                if (parser.peek() instanceof OperatorToken.OpeningParenthesisToken) {
                    assert ident instanceof Identifier;
                    return parseFunctionArgs(parser, (Identifier) ident);
                } else {
                    return ident;
                }
            } else if (curToken instanceof OperatorToken.OpeningBracketToken) {
                return parseArrayExpr(parser);
            } else {
                throw new ParserError(ParserErrorKind.ExpectedExpression, parser.getPosition(), parser.peek());
            }
        }
    }

    /**
     * Parses an array expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @return The parsed array expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseArrayExpr(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        if (curToken instanceof OperatorToken.OpeningBracketToken) {
            parser.consume();
            ArrayList<Expression> elements = new ArrayList<>();
            while (!(parser.peek() instanceof OperatorToken.ClosingBracketToken)) {
                if (parser.peek() != null) {
                    elements.add(parseExpr(parser));
                } else {
                    throw new ParserError(ParserErrorKind.ExpectedExpression, parser.getPosition(), parser.peek());
                }
                if (parser.peek() instanceof OperatorToken.CommaToken) {
                    parser.consume();
                } else {
                    break;
                }
            }
            if (!(parser.peek() instanceof OperatorToken.ClosingBracketToken)) {
                throw new ParserError(ParserErrorKind.ExpectedClosingBracket, parser.getPosition(), parser.peek());
            }
            parser.consume();
            return new ArrayExpression(elements.toArray(new Expression[0]));
        } else {
            throw new ParserError(ParserErrorKind.ExpectedArray, parser.getPosition(), parser.peek());
        }
    }

    /**
     * Parses a function call expression using the given parser.
     *
     * @param parser The parser to use for parsing.
     * @param function_name The name of the function to call.
     * @return The parsed function call expression.
     * @throws ParserError If an error occurs during parsing.
     */
    public static Expression parseFunctionArgs(Parser parser, Identifier function_name) throws ParserError {
        Token curToken = parser.peek();
        if (curToken == null) {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, parser.getPosition());
        }
        if (!(curToken instanceof OperatorToken.OpeningParenthesisToken)) {
            throw new ParserError(ParserErrorKind.ExpectedOpeningParenthesis, parser.getPosition(), parser.peek());
        }
        parser.consume();
        ArrayList<Expression> args = new ArrayList<>();
        while (!(parser.peek() instanceof OperatorToken.ClosingParenthesisToken)) {
            if (parser.peek() != null) {
                args.add(parseExpr(parser));
            } else {
                throw new ParserError(ParserErrorKind.ExpectedExpression, parser.getPosition(), parser.peek());
            }
            if (parser.peek() instanceof OperatorToken.CommaToken) {
                parser.consume();
            } else {
                break;
            }
        }
        if (!(parser.peek() instanceof OperatorToken.ClosingParenthesisToken)) {
            throw new ParserError(ParserErrorKind.ExpectedClosingParenthesis, parser.getPosition(), parser.peek());
        }
        parser.consume();
        return new FunctionCall(function_name.getName(), args.toArray(new Expression[0]));
    }
}
