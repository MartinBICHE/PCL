package eu.telecomnancy.pcl.serpython.parser;

import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IdentToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IntegerToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.NoneToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.StringToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class ExprParser {
    public static Expression parseExpr(Parser parser) {
        return parseAddSubExpr(parser);
    }

    public static Expression parseAddSubExpr(Parser parser) {
        if (parser.peek() == null) {
            return null;
        }
        Expression left = parseMulDivRemExpr(parser);
        if (left == null) {
            return null;
        }
        Token curToken = parser.peek();
        if (curToken instanceof OperatorToken.PlusToken) {
            parser.consume();
            Expression right = parseAddSubExpr(parser);
            if (right == null) {
                return null;
            }
            return new Expression.Addition(left, right);
        } else if (curToken instanceof OperatorToken.MinusToken) {
            parser.consume();
            Expression right = parseAddSubExpr(parser);
            if (right == null) {
                return null;
            }
            return new Expression.Subtraction(left, right);
        } else {
            return left;
        }
    }

    public static Expression parseMulDivRemExpr(Parser parser) {
        if (parser.peek() == null) {
            return null;
        }
        Expression left = parseNegExpr(parser);
        if (left == null) {
            return null;
        }
        Token curToken = parser.peek();
        if (curToken instanceof OperatorToken.MultiplyToken) {
            parser.consume();
            Expression right = parseMulDivRemExpr(parser);
            if (right == null) {
                return null;
            }
            return new Expression.Multiplication(left, right);
        } else if (curToken instanceof OperatorToken.DivideToken) {
            parser.consume();
            Expression right = parseMulDivRemExpr(parser);
            if (right == null) {
                return null;
            }
            return new Expression.Division(left, right);
        } else if (curToken instanceof OperatorToken.ModuloToken) {
            parser.consume();
            Expression right = parseMulDivRemExpr(parser);
            if (right == null) {
                return null;
            }
            return new Expression.Reminder(left, right);
        } else {
            return left;
        }
    }

    public static Expression parseNegExpr(Parser parser) {
        if (parser.peek() == null) {
            return null;
        }
        if (parser.peek() instanceof OperatorToken.MinusToken) {
            parser.consume();
            return new Expression.Negation(parseParenthesisExpr(parser));
        } else {
            return parseParenthesisExpr(parser);
        }
    }

    public static Expression parseParenthesisExpr(Parser parser) {
        Token curToken = parser.peek();
        if (curToken == null) {
            return null;
        }
        if (curToken instanceof OperatorToken.OpeningParenthesisToken) {
            parser.consume();
            Expression expr = parseExpr(parser);
            if (parser.peek() instanceof OperatorToken.ClosingParenthesisToken) {
                parser.consume();
                return expr;
            }
            return null;
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
                return AtomParser.parseIdentifier(parser);
            } else {
                return null;
            }
        }
    }
}
