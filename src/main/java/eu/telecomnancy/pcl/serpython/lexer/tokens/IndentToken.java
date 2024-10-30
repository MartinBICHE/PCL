package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

/**
 * Represents an abstract token for indentation in the lexer.
 * This class serves as a base class for tokens that handle indentation levels.
 * It is intended to be extended by specific types of indentation tokens.
 */
public abstract class IndentToken extends Token {

    /**
     * Represents an indentation token in the lexer.
     * This token is used to signify an increase in indentation level.
     *
     * @param span The span of text that this token covers.
     */
    public IndentToken(Span span) {
        super(span);
    }
    /**
     * Represents the beginning of an indented block in the lexer.
     * This token is used to signify the start of a new indentation level.
     */
    public static class BeginToken extends IndentToken {
        public BeginToken(Span span) {
            super(span);
        }};
    /**
     * Represents the end fof an intended block in the lexer.
     * This token is used to signify the end of an existing indentation level.
     */
    public static class EndToken extends IndentToken {
        public EndToken(Span span) {
            super(span);
        }};

    public String toString() {
        return "IndentToken{" +
                "span=" + Span.formatSpan(span) +
                '}';
    }
};
