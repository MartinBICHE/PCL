package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

/**
 * The BooleanToken class is an abstract class representing a boolean token in the lexer.
 * It extends the LitteralToken class and provides specific boolean tokens as inner classes.
 */
public abstract class BooleanToken extends LitteralToken {
    /**
     * Constructs a new BooleanToken with the specified span.
     *
     * @param span the span of the token
     */
    public BooleanToken(Span span) {
        super(span);
    }

    /**
     * Returns the boolean value of the token.
     *
     * @return the boolean value of the token
     */
    public abstract boolean getValue();

    /**
     * The TrueToken class represents a 'true' boolean token.
     */
    public static class TrueToken extends BooleanToken {
        /**
         * Constructs a new TrueToken with the specified span.
         *
         * @param span the span of the token
         */
        public TrueToken(Span span) {
            super(span);
        }

        /**
         * Returns the boolean value of the token, which is true.
         *
         * @return true
         */
        @Override
        public boolean getValue() {
            return true;
        }
    }

    /**
     * The FalseToken class represents a 'false' boolean token.
     */
    public static class FalseToken extends BooleanToken {
        /**
         * Constructs a new FalseToken with the specified span.
         *
         * @param span the span of the token
         */
        public FalseToken(Span span) {
            super(span);
        }

        /**
         * Returns the boolean value of the token, which is false.
         *
         * @return false
         */
        @Override
        public boolean getValue() {
            return false;
        }
    }
}
