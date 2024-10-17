package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

/**
 * Represents an abstract token in the lexer.
 * Each token has a span which indicates its position in the source code.
 */
public abstract class Token {
    final private Span span;

    /**
     * Constructs a Token with the specified span.
     *
     * @param span the span associated with this token
     */
    public Token(Span span) {
        this.span = span;
    }

    
    /**
     * Retrieves the span associated with this token.
     *
     * @return the span of this token.
     */
    public Span getSpan() {
        return span;
    }
}
