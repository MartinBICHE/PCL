package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

public abstract class LitteralExprToken extends Token {

    /**
     * Represents a literal expression token in the lexer.
     * This is an abstract class that extends the {@link Token} class.
     * 
     * @param span The span of the token in the source code.
     */
    public LitteralExprToken(Span span) {
        super(span);
    }

}
