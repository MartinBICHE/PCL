package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

public class IntegerToken extends LitteralToken {

    private final int value;

    public IntegerToken(int value, Span span) {
        super(span);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return "IntegerToken{" +
            "value=" + value +
            ", span=" + Span.formatSpan(span) +
            '}';
    }
}
