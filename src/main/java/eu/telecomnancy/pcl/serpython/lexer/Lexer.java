package eu.telecomnancy.pcl.serpython.lexer;

import java.util.ArrayList;
import eu.telecomnancy.pcl.serpython.common.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;

public class Lexer {
    private final String source;
    private int index;
    private int line;
    private int column;
    private int indentLevel;
    private ArrayList<Token> tokens;

    public Lexer(String source) {
        this.source = source;
        this.index = 0;
        this.line = 1;
        this.column = 1;
        this.indentLevel = 0;
        this.tokens = new ArrayList<>();
    }

    /**
     * Get the char of the current index in the source file.
     * @return the char of the current index in the source file.
     */
    public char getCurrent() {
        return source.charAt(index);
    }

    /**
     * Indicates if there is a next token in the source file.
     * @return true if there is a next token in the source file, false otherwise.
     */
    public boolean hasNext() {
        return index < source.length();
    }

    /**
     * Indicate if the current index is the end of the source file.
     * @return true if the current index is the end of the source file, false otherwise.
     */
    public boolean isEOF() {
        return index >= source.length();
    }

    /**
     * Emit a token.
     * @param token
     */
    public void emit(Token token) {
        tokens.add(token);
    }

    /**
     * Go to the next character in the source file.
     */
    public void advance() {
        this.index += 1;
        this.column += 1;
        if (!isEOF() && getCurrent() == '\n') {
            this.line += 1;
            this.column = 1;
        }
    }

    /**
     * Tokenize the source file.
     * @return an array of tokens.
     */
    public ArrayList<Token> tokenize() {
        while(!isEOF()) {
            char current = getCurrent();
            if(current == ' ' || current == '\t') {
                skipWhitespace();
            } else if(Character.isDigit(current)) {
                readNumber();
            }
        }
        return this.tokens;
    }

    /**
     * Skip whitespaces.
     */
    public void skipWhitespace() {
        while(getCurrent() == ' ' || getCurrent() == '\t') {
            advance();
        }
    }

    public void readNumber() {
        String number = "";
        while (hasNext() && Character.isDigit(getCurrent())) {
            number += getCurrent();
            advance();
        }
        int parsedNumber = Integer.parseInt(number);
        Span span = new Span(line, column, number.length());
        Token token = new IntegerToken(parsedNumber, span);
        emit(token);
    }
}
