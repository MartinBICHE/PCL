package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

/**
 * The IdentToken class is an abstract class representing a identifier token in the lexer.
 * It extends the LitteralToken class and provides a name field to store the identifier.
 */
public abstract class IdentToken extends LitteralToken {
    private String name;
    /**
     * Constructs a new IdentToken with the specified span.
     *
     * @param span the span of the token
     */
    public IdentToken(String name, Span span) {
        super(span);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "IdentToken{" +
                "name='" + name + '\'' +
                ", span=" + Span.formatSpan(span) +
                '}';
    }
}
