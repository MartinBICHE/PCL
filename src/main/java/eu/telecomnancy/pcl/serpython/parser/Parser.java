package eu.telecomnancy.pcl.serpython.parser;

import java.util.ArrayList;

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

    protected Token consume() {
        if (index < tokens.size()) {
            return tokens.get(index++);
        } else {
            return null;
        }
    }

    public void parse() {
        while (index < tokens.size()) {
            System.out.println(tokens.get(index));
            index++;
        }
    }
}
