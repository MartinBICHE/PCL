package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

public class StringToken extends LitteralToken {

    private final String value;

    public StringToken(String value, Span span) {
        super(span);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
