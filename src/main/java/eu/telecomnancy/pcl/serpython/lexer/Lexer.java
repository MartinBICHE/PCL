package eu.telecomnancy.pcl.serpython.lexer;

import java.util.ArrayList;
import eu.telecomnancy.pcl.serpython.common.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.*;
import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.BooleanToken.*;


public class Lexer {
    private final String source;
    private int index;
    private int line;
    private int column;
    private int lastLine;
    private int lastColumn;
    private int indentLevel;
    private ArrayList<Token> tokens;

    public Lexer(String source) {
        this.source = source;
        this.index = 0;
        this.line = 1;
        this.column = 1;
        this.lastLine = 1;
        this.lastColumn = 1;
        this.indentLevel = 0;
        this.tokens = new ArrayList<>();
    }

    /**
     * Get the current line.
     * @return the current line.
     */
    public int getLine() {
        return line;
    }

    /**
     * Get the current column.
     * @return the current column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Get the last line.
     * If there were no error, this method returns the current line.
     * @return the last line.
     */
    public int getLastLine() {
        return lastLine;
    }

    /**
     * Get the last column.
     * If there were no error, this method returns the current column.
     * @return the last column.
     */
    public int getLastColumn() {
        return lastColumn;
    }

    /**
     * Get the source code.
     * @return the source code.
     */
    public String getSource() {
        return source;
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
        return index + 1 < source.length();
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
        this.lastLine = this.line;
        this.lastColumn = this.column;
    }

    /**
     * Go to the next character in the source file.
     */
    public void advance() {
        if (!isEOF() && getCurrent() == '\n') {
            this.line += 1;
            this.column = 1;
        } else {
            this.column += 1;
        }
        this.index += 1;
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
    public ArrayList<Token> tokenize() throws LexerError{
        while(!isEOF()) {
            char current = getCurrent();
            if(current == '#') {
                advance();
                current = getCurrent();
                while(current != '\n') {
                    advance();
                    current = getCurrent();
                } 
            }else if(current == ' ' || current == '\t' || current == '\r') {
                skipWhitespace();
            } else if(Character.isDigit(current)) {
                readNumber();
            } else if (current == '"') {
                readString();
            } else if (current == ',' || current == '+' || current == '-' || current == '*' || current == '/' || current == '%' || current == '<' || current == '>' || current == '=' || current == '!' || current == '(' || current == ')' || current == '[' || current == ']' || current == ':') {
                readOperator();
            }
            else if (Character.isLetter(current) || current == '_'){
              readIdentorKeyWordorNone();
            } else if (current == '\n') {
                emit(new NewlineToken(new Span(line, column, 1)));
                advance();
                readIndent();
            } else {
                throw new LexerError(this, "Unexpected character '" + current + "'");
            }
        }
        return this.tokens;
    }

    /**
     * Skip whitespaces.
     */
    public void skipWhitespace() {
        while (!isEOF() && (getCurrent() == ' ' || getCurrent() == '\t' || getCurrent() == '\r')) {
            advance();
        }
    }

    /**
     * Skip comment.
     */
    public void skipComment() {
        advance();
        while(getCurrent() != '\n') {
            advance();
        }
    }

    public void readNumber() {
        String number = "";
        int column = this.column;
        while (!isEOF() && Character.isDigit(getCurrent())) {
            number += getCurrent();
            advance();
        }
        int parsedNumber = Integer.parseInt(number);
        Span span = new Span(line, column, number.length());
        Token token = new IntegerToken(parsedNumber, span);
        emit(token);
    }


    public void readIdentorKeyWordorNone(){
        String ident = "";
        int column = this.column;
        ident += getCurrent();
        advance();
            
        while (!isEOF() && Character.isLetterOrDigit(getCurrent()) || !isEOF() && getCurrent()=='_') {
            ident += getCurrent();
            advance();
        }
        Span span = new Span(line, column, ident.length());
        Token token;
        switch (ident){

            case "and":
                token= new AndToken(span);
                emit(token);
                break;

            case "if":
                token = new IfToken(span);
                emit(token);
                break;

            case "for":
                token = new ForToken(span);
                emit(token);
                break;

            case "None":
                token = new NoneToken(span);
                emit(token);
                break;
            
            case "else":
                token = new ElseToken(span);
                emit(token);
                break;

            case "def":
                token = new DefToken(span);
                emit(token);
                break;

            case "print":
                token = new PrintToken(span);
                emit(token);
                break;

            case "in":
                token = new InToken(span);
                emit(token);
                break;

            case "return":
                token = new ReturnToken(span);
                emit(token);
                break;
                
            case "or":
                token = new OrToken(span);
                emit(token);
                break;
            
            case "not":
                token = new NotToken(span);
                emit(token);
                break;
            
            case "True":
                token = new TrueToken(span);
                emit(token);
                break;

            case "False":
                token = new FalseToken(span);
                emit(token);
                break;

            default:
                token = new IdentToken(ident, span);
                emit(token);
        }
        
    }
    
    public void readIndent() throws LexerError{
        int count = 0;
        while(hasNext() && getCurrent() == ' '){
            count ++;
            advance();
        }   
        if (count % 4 ==0){
            Span span = new Span(line, column, count);
            while (this.indentLevel < count / 4){
                this.indentLevel += 1;
                Token token = new BeginToken(span);
                emit(token);
            }

            while (this.indentLevel > count / 4) {
                this.indentLevel -= 1;
                Token token = new EndToken(span);
                emit(token);
            }
        } else {
            throw new LexerError(this, "Indentation error (expected multiple of 4 spaces, got " + count + " spaces)");
        }

        
    }

    public void readOperator() throws LexerError {
        char current = getCurrent();
        Span spanDouble = new Span(line, column, 2);
        Span spanSimple = new Span(line, column, 1);
    
        if (current == '!' && hasNext() && getNext() == '=') {
            advance();
            emit(new OperatorToken.NotEqualToken(spanDouble));
        } else if (current == '/' && hasNext()&& getNext() == '/') {
            advance();
            emit(new OperatorToken.DivideToken(spanDouble));
        } else if (current == '%') {
            emit(new OperatorToken.ModuloToken(spanDouble));
        } else if (current == '<' && hasNext() && getNext() == '=') {
            advance();
            emit(new OperatorToken.LessEqualToken(spanDouble));
        } else if (current == '>' && hasNext() && getNext() == '=') {    
            advance();
            emit(new OperatorToken.GreaterEqualToken(spanDouble));
        } else if (current == '=' && hasNext() && getNext() == '=') {
            advance();
            emit(new OperatorToken.EqualToken(spanDouble)); 
        } else if (current == '+') {
            emit(new OperatorToken.PlusToken(spanSimple));
        } else if(current == ',') {
            emit(new OperatorToken.CommaToken(spanSimple));
        } else if (current == '-') {
            emit(new OperatorToken.MinusToken(spanSimple));
        } else if (current == '*') {
            emit(new OperatorToken.MultiplyToken(spanSimple));
        } else if (current == '<') {
            emit(new OperatorToken.LessToken(spanSimple));
        } else if (current == '>') {
            emit(new OperatorToken.GreaterToken(spanSimple));
        } else if (current == '=') {
            emit(new OperatorToken.AssignToken(spanSimple));
        } else if (current == '(') {
            emit(new OperatorToken.OpeningParenthesisToken(spanSimple));
        } else if (current == ')') {
            emit(new OperatorToken.ClosingParenthesisToken(spanSimple));
        } else if (current == '[') {
            emit(new OperatorToken.OpeningBracketToken(spanSimple));
        } else if (current == ']') {    
            emit(new OperatorToken.ClosingBracketToken(spanSimple));
        } else if (current == ':') {
            emit(new OperatorToken.ColonToken(spanSimple));
        } else {
            throw new LexerError(this, "Unknown syntax '" + current + "'");
        }
        advance();
    }    

    public void readString() throws LexerError {
        StringBuilder string = new StringBuilder();
        boolean escapeMode = false;
        int column = this.column;
        advance();
        while (escapeMode || getCurrent() != '"') {
            if(!hasNext()) {
                throw new LexerError(this, "Unexpected end of file");
            }
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
        Span span = new Span(line, column, string.length() + 2);
        Token token = new StringToken(string.toString(), span);
        emit(token);
    }
}
