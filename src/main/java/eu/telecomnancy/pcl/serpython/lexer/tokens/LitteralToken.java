package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

public abstract class LitteralToken extends LitteralExprToken {

    public LitteralToken(Span span) {
        super(span);
    }
}
