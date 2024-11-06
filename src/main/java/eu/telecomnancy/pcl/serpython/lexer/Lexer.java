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
     * Get the next char of the current index in the source file.
     * @return the next char of the current index in the source file.
     */
    public char getNext() {
        return source.charAt(index + 1);
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
            } else if (current == '"') {
                readString();
            } else if (current == ',' || current == '+' || current == '-' || current == '*' || current == '/' || current == '%' || current == '<' || current == '>' || current == '=' || current == '!' || current == '(' || current == ')' || current == '[' || current == ']' || current == ':') {
                readOperator();
            }
        }
        return this.tokens;
    }

    /**
     * Skip whitespaces.
     */
    public void skipWhitespace() {
        while (!isEOF() && (getCurrent() == ' ' || getCurrent() == '\t')) {
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

    public void readOperator() {
        char current = getCurrent();
        Span span = new Span(line, column, 1);
    
        if (current == '+') {
            emit(new OperatorToken.PlusToken(span));
        } else if(current == ',') {
            emit(new OperatorToken.CommaToken(span));
        } else if (current == '-') {
            emit(new OperatorToken.MinusToken(span));
        } else if (current == '*') {
            emit(new OperatorToken.MultiplyToken(span));
        } else if (current == '/' && getNext() == '/') {
            emit(new OperatorToken.DivideToken(span));
        } else if (current == '%') {
            emit(new OperatorToken.ModuloToken(span));
        } else if (current == '<') {
            emit(new OperatorToken.LessToken(span));
        } else if (current == '>') {
            emit(new OperatorToken.GreaterToken(span));
        } else if (current == '=') {
            emit(new OperatorToken.AssignToken(span));
        } else if (current == '!' && getNext() == '=') {
            emit(new OperatorToken.NotEqualToken(span));
        } else if (current == '<' && getNext() == '=') {
            emit(new OperatorToken.LessEqualToken(span));
        } else if (current == '>' && getNext() == '=') {    
            emit(new OperatorToken.GreaterEqualToken(span));
        } else if (current == '=' && getNext() == '=') {
            emit(new OperatorToken.EqualToken(span));
        } else if (current == '(') {
            emit(new OperatorToken.OpeningParenthesisToken(span));
        } else if (current == ')') {
            emit(new OperatorToken.ClosingParenthesisToken(span));
        } else if (current == '[') {
            emit(new OperatorToken.OpeningBracketToken(span));
        } else if (current == ']') {    
            emit(new OperatorToken.ClosingBracketToken(span));
        } else if (current == ':') {
            emit(new OperatorToken.ColonToken(span));
        }
        advance();
    }    

    public void readString() {
        StringBuilder string = new StringBuilder();
        boolean escapeMode = false;
        advance();
        while (hasNext() && (escapeMode || getCurrent() != '"')) {
            char current = getCurrent();
            if(current == '\\') {
                escapeMode = true;
            } else if(escapeMode) { // recognize escape characters
                switch (current) {
                    case 'n':
                        string.append('\n');
                        break;
                    case '"':
                        string.append('"');
                        break;
                    default:
                        break;
                }
                escapeMode = false;
            } else {
                string.append(current);
            }
            advance();
        }
        advance();
        Span span = new Span(line, column, string.length());
        Token token = new StringToken(string.toString(), span);
        emit(token);
    }

}
