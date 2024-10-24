package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

public class NoneToken extends LitteralToken {

    public NoneToken(Span span) {
        super(span);
    }

    public String toString() {
        return "NoneToken{" +
                "span=" + span.toString() +
                '}';
    }
}
