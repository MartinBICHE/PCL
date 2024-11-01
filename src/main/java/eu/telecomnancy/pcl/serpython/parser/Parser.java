package eu.telecomnancy.pcl.serpython.parser;

import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.common.Span;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class Parser {
    private ArrayList<Token> tokens;
    private int index;

    /**
     * Constructs a Parser object with the given list of tokens.
     * 
     * @param tokens the list of tokens to be parsed
     */
    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    /**
     * Returns the next token in the parser without consuming it.
     * 
     * @return the next token in the parser, or null if the end of the input has been reached
     */
    protected Token peek() {
        if (index < tokens.size()) {
            return tokens.get(index);
        } else {
            return null;
        }
    }

    /**
     * Returns the token at the specified offset from the current position without consuming it.
     * 
     * @param offset the offset from the current position
     * @return the token at the specified offset, or null if the end of the input has been reached
     */
    protected Token peek(int offset) {
        if (index + offset < tokens.size()) {
            return tokens.get(index + offset);
        } else {
            return null;
        }
    }

    /**
     * Consumes and returns the next token in the parser.
     * 
     * @return the consumed token
     * @throws ParserError if the end of the input has been reached
     */
    protected Token consume() throws ParserError {
        if (index < tokens.size()) {
            return tokens.get(index++);
        } else {
            throw new ParserError(ParserErrorKind.UnexpectedEOF, getPosition());
        }
    }

    /**
     * Parses the list of tokens.
     */
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
     * @return a Span object representing the current position of the parser
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
