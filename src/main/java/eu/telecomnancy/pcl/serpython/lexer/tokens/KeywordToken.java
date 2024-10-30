package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

/**
 * The KeywordToken class is an abstract class representing a keyword token in the lexer.
 * It extends the Token class and provides specific keyword tokens as inner classes.
 */
public abstract class KeywordToken extends Token {
    /**
     * Constructs a new KeywordToken with the specified span.
     *
     * @param span the span of the token
     */
    public KeywordToken(Span span) {
        super(span);
    }

    /**
     * The ForToken class represents a 'for' keyword token.
     */
    public static class ForToken extends KeywordToken {
        /**
         * Constructs a new ForToken with the specified span.
         *
         * @param span the span of the token
         */
        public ForToken(Span span) {
            super(span);
        }
    }

    /**
     * The IfToken class represents an 'if' keyword token.
     */
    public static class IfToken extends KeywordToken {
        /**
         * Constructs a new IfToken with the specified span.
         *
         * @param span the span of the token
         */
        public IfToken(Span span) {
            super(span);
        }
    }

    /**
     * The ElseToken class represents an 'else' keyword token.
     */
    public static class ElseToken extends KeywordToken {
        /**
         * Constructs a new ElseToken with the specified span.
         *
         * @param span the span of the token
         */
        public ElseToken(Span span) {
            super(span);
        }
    }

    /**
     * The DefToken class represents a 'def' keyword token.
     */
    public static class DefToken extends KeywordToken {
        /**
         * Constructs a new DefToken with the specified span.
         *
         * @param span the span of the token
         */
        public DefToken(Span span) {
            super(span);
        }
    }

    /**
     * The PrintToken class represents a 'print' keyword token.
     */
    public static class PrintToken extends KeywordToken {
        /**
         * Constructs a new PrintToken with the specified span.
         *
         * @param span the span of the token
         */
        public PrintToken(Span span) {
            super(span);
        }
    }

    /**
     * The InToken class represents an 'in' keyword token.
     */
    public static class InToken extends KeywordToken {
        /**
         * Constructs a new InToken with the specified span.
         *
         * @param span the span of the token
         */
        public InToken(Span span) {
            super(span);
        }
    }

    /**
     * The ReturnToken class represents a 'return' keyword token.
     */
    public static class ReturnToken extends KeywordToken {
        /**
         * Constructs a new ReturnToken with the specified span.
         *
         * @param span the span of the token
         */
        public ReturnToken(Span span) {
            super(span);
        }
    }

    /**
     * The EofToken class represents the End Of File.
     */
    public static class EofToken extends KeywordToken {

        /**
         * Constructs a new EofToken with the specified span.
         *
         * @param span the span of the token
         */
        public EofToken(Span span) {
            super(span);
        }
    }

    public String toString() {
        return "KeywordToken{" +
                "span=" + Span.formatSpan(span) +
                '}';
    }
}
