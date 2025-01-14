package eu.telecomnancy.pcl.serpython.parser;

import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.NewlineToken;

public class ErrorHandlingStrategies {
    public static void skipUntilNewlineToken(Parser parser) throws ParserError {
        while (!(parser.peek() instanceof NewlineToken)) {
            parser.consume();
        }
    }

    public static void skipOneToken(Parser parser) throws ParserError {
        parser.consume();
    }

    public static void skipUntilColon(Parser parser) throws ParserError {
        while (!(parser.peek() instanceof OperatorToken.ColonToken)) {
            if(parser.peek() instanceof NewlineToken) {
                throw new ParserError(ParserErrorKind.MissingColon, parser.getPosition());
            }
            parser.consume();
        }
    }

    public static void skipUntilClosingParenthesis(Parser parser) throws ParserError {
        while (!(parser.peek() instanceof OperatorToken.ClosingParenthesisToken)) {
            if(parser.peek() instanceof NewlineToken) {
                throw new ParserError(ParserErrorKind.MissingColon, parser.getPosition());
            }
            parser.consume();
        }
    }
}