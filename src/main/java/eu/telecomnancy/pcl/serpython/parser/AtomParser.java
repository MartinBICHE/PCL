package eu.telecomnancy.pcl.serpython.parser;

import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IdentToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IntegerToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.NoneToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.StringToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class AtomParser {
    public static StringLitteral parseStringLitteral(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof StringToken) {
            parser.consume();
            return new StringLitteral(((StringToken) curToken).getValue());
        }
        throw new ParserError(ParserErrorKind.ExpectedString, parser.getPosition(), curToken);
    }

    public static NumberLitteral parseNumberLitteral(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof IntegerToken) {
            parser.consume();
            return new NumberLitteral(((IntegerToken) curToken).getValue());
        }
        throw new ParserError(ParserErrorKind.ExpectedNumber, parser.getPosition(), curToken);
    }

    public static BooleanLitteral parseBooleanLitteral(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof BooleanToken) {
            parser.consume();
            return new BooleanLitteral(((BooleanToken) curToken).getValue());
        }
        throw new ParserError(ParserErrorKind.ExpectedBoolean, parser.getPosition(), curToken);
    }

    public static NoneLitteral parseNoneLitteral(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof NoneToken) {
            parser.consume();
            return new NoneLitteral();
        }
        throw new ParserError(ParserErrorKind.ExpectedKeyword, parser.getPosition(), "expected: None, got " + (curToken != null ? curToken : "EOF"));
    }

    public static Identifier parseIdentifier(Parser parser) throws ParserError {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof IdentToken) {
            parser.consume();
            return new Identifier(((IdentToken) curToken).getName());
        }
        throw new ParserError(ParserErrorKind.ExpectedIdentifier, parser.getPosition(), curToken);
    }
}
