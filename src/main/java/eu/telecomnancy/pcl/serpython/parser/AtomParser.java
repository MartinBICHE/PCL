package eu.telecomnancy.pcl.serpython.parser;

import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IdentToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IntegerToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.NoneToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.StringToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class AtomParser {
    public static StringLitteral parseStringLitteral(Parser parser) {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof StringToken) {
            parser.consume();
            return new StringLitteral(((StringToken) curToken).getValue());
        }
        return null;
    }

    public static NumberLitteral parseNumberLitteral(Parser parser) {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof IntegerToken) {
            parser.consume();
            return new NumberLitteral(((IntegerToken) curToken).getValue());
        }
        return null;
    }

    public static BooleanLitteral parseBooleanLitteral(Parser parser) {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof BooleanToken) {
            parser.consume();
            return new BooleanLitteral(((BooleanToken) curToken).getValue());
        }
        return null;
    }

    public static NoneLitteral parseNoneLitteral(Parser parser) {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof NoneToken) {
            parser.consume();
            return new NoneLitteral();
        }
        return null;
    }

    public static Identifier parseIdentifier(Parser parser) {
        Token curToken = parser.peek();
        if (curToken != null && curToken instanceof IdentToken) {
            parser.consume();
            return new Identifier(((IdentToken) curToken).getName());
        }
        return null;
    }
}
