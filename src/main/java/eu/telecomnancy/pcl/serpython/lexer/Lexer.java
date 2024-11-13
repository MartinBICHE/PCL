package eu.telecomnancy.pcl.serpython.lexer;

import java.util.ArrayList;
import eu.telecomnancy.pcl.serpython.common.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.OrToken;
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
    public ArrayList<Token> tokenize() throws LexerError{
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
            else if (Character.isLetter(current) || current == '_'){
              readIdentorKeyWordorNone();
            }
            else if (current == '\n'){
                emit(new NewlineToken(new Span(line, column, 1)));
                advance();
                readIndent();
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


    public void readIdentorKeyWordorNone(){
        String ident = "";
        
        ident += getCurrent();
        advance();
            
        while (hasNext() && Character.isLetterOrDigit(getCurrent()) || hasNext() && getCurrent()=='_') {
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
        }
        else{
            throw new LexerError();
        }

        
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
