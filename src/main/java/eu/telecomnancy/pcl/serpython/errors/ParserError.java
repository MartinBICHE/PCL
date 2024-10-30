package eu.telecomnancy.pcl.serpython.errors;

import eu.telecomnancy.pcl.serpython.common.Span;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class ParserError extends Exception {
    private final ParserErrorKind kind;
    private final Span span;
    private final String context;

    public ParserError(ParserErrorKind kind) {
        super("ParserError: " + kind.getMessage());
        this.kind = kind;
        this.span = null;
        this.context = null;
    }

    public ParserError(ParserErrorKind kind, Span span) {
        super("ParserError: " + kind.getMessage() + formatSpan(span));
        this.kind = kind;
        this.span = span;
        this.context = null;
    }

    public ParserError(ParserErrorKind kind, Span span, String context) {
        super("ParserError: " + kind.getMessage() + formatSpan(span) + formatContext(context));
        this.kind = kind;
        this.span = span;
        this.context = context;
    }

    public ParserError(ParserErrorKind kind, Span span, Token context) {
        super("ParserError: " + kind.getMessage() + formatSpan(span) + formatContext(context));
        this.kind = kind;
        this.span = span;
        this.context = context.toString();
    }

    private static String formatSpan(Span span) {
        if (span == null) {
            return "";
        }
        return " at " + span;
    }

    private static String formatContext(String context) {
        if (context == null) {
            return "";
        }
        return " (" + context + ")";
    }

    private static String formatContext(Token context) {
        return " (got " +  (context != null ? context.toString() : "EOF") + ")";
    }

    public ParserErrorKind getKind() {
        return kind;
    }

    public Span getSpan() {
        return span;
    }

    public String getContext() {
        return context;
    }
}
