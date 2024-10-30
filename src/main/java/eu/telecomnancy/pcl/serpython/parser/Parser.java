package eu.telecomnancy.pcl.serpython.parser;

import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.common.Span;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class Parser {
    private ArrayList<Token> tokens;
    private int index;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    protected Token peek() {
        if (index < tokens.size()) {
            return tokens.get(index);
        } else {
            return null;
        }
    }

    protected Token peek(int offset) {
        if (index + offset < tokens.size()) {
            return tokens.get(index + offset);
        } else {
            return null;
        }
    }

    protected Token consume() throws ParserError {
        if (index < tokens.size()) {
            return tokens.get(index++);
        } else {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, getPosition());
        }
    }

    public void parse() {
        while (index < tokens.size()) {
            System.out.println(tokens.get(index));
            index++;
        }
    }

    /**
     * Returns the current position (as a Span) of the parser.
     * 
     * If the parser is at the end of the input, the position returned is the end of the last token,
     * with a length of 0. If the input is empty, the position returned is (0, 0, 0).
     * 
     * This method might return null if the last token has no span information.
     * 
     * @return Span object representing the current position of the parser
     * @see Span
     */
    public Span getPosition() {
        if (index < tokens.size()) {
            return tokens.get(index).getSpan();
        } else { 
            if (tokens.size() > 0) {
                Span span = tokens.get(tokens.size() - 1).getSpan();
                if (span != null) {
                    return new Span(span.getLine(), span.getColumn() + span.getLength(), 0);
                } else {
                    return new Span(0, 0, 0);
                }
            } else {
                return new Span(0, 0, 0);
            }
        }
    }
}
