package eu.telecomnancy.pcl.serpython.errors;

import eu.telecomnancy.pcl.serpython.lexer.Lexer;

public class LexerError extends Exception {
    public LexerError() {}

    // TODO: improve that
    public LexerError(String message) {
         super(message);
    }
}
