package eu.telecomnancy.pcl.serpython.errors;

import eu.telecomnancy.pcl.serpython.common.Span;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

/**
 * Represents an error that occurs during parsing.
 */
public class ParserError extends Exception {
    private final ParserErrorKind kind;
    private final Span span;
    private final String context;

    /**
     * Constructs a new ParserError with the specified error kind.
     * 
     * @param kind The error kind.
     */
    public ParserError(ParserErrorKind kind) {
        super("ParserError: " + kind.getMessage());
        this.kind = kind;
        this.span = null;
        this.context = null;
    }

    /**
     * Constructs a new ParserError with the specified error kind and cause.
     * 
     * @param kind  The error kind.
     * @param cause The cause of the error.
     */
    public ParserError(ParserErrorKind kind, Throwable cause) {
        super("ParserError: " + kind.getMessage(), cause);
        this.kind = kind;
        this.span = null;
        this.context = null;
    }

    /**
     * Constructs a new ParserError with the specified error kind and span.
     * 
     * @param kind The error kind.
     * @param span The span where the error occurred.
     */
    public ParserError(ParserErrorKind kind, Span span) {
        super("ParserError: " + kind.getMessage() + formatSpan(span));
        this.kind = kind;
        this.span = span;
        this.context = null;
    }

    /**
     * Constructs a new ParserError with the specified error kind, span, and cause.
     * 
     * @param kind  The error kind.
     * @param span  The span where the error occurred.
     * @param cause The cause of the error.
     */
    public ParserError(ParserErrorKind kind, Span span, Throwable cause) {
        super("ParserError: " + kind.getMessage() + formatSpan(span), cause);
        this.kind = kind;
        this.span = span;
        this.context = null;
    }

/**
     * Constructs a new ParserError with the specified error kind, span and a context.
     * 
     * @param kind    The error kind.
     * @param span    The span where the error occurred.
     * @param context The context of the error.
     */
    public ParserError(ParserErrorKind kind, Span span, String context) {
        super("ParserError: " + kind.getMessage() + formatSpan(span) + formatContext(context));
        this.kind = kind;
        this.span = span;
        this.context = context;
    }

    /**
     * Constructs a new ParserError with the specified error kind, span, context, and cause.
     * 
     * @param kind    The error kind.
     * @param span    The span where the error occurred.
     * @param context The context of the error.
     * @param cause   The cause of the error.
     */
    public ParserError(ParserErrorKind kind, Span span, String context, Throwable cause) {
        super("ParserError: " + kind.getMessage() + formatSpan(span) + formatContext(context), cause);
        this.kind = kind;
        this.span = span;
        this.context = context;
    }

    /**
     * Constructs a new ParserError with the specified error kind, span, and context.
     * 
     * @param kind    The error kind.
     * @param span    The span where the error occurred.
     * @param context The context of the error.
     */
    public ParserError(ParserErrorKind kind, Span span, Token context) {
        super("ParserError: " + kind.getMessage() + formatSpan(span) + formatContext(context));
        this.kind = kind;
        this.span = span;
        this.context = context.toString();
    }

    /**
     * Constructs a new ParserError with the specified error kind, span, context, and cause.
     * 
     * @param kind    The error kind.
     * @param span    The span where the error occurred.
     * @param context The context of the error.
     * @param cause   The cause of the error.
     */
    public ParserError(ParserErrorKind kind, Span span, Token context, Throwable cause) {
        super("ParserError: " + kind.getMessage() + formatSpan(span) + formatContext(context), cause);
        this.kind = kind;
        this.span = span;
        this.context = context.toString();
    }

    /**
     * Gets the error kind.
     * 
     * @return The error kind.
     */
    public ParserErrorKind getKind() {
        return kind;
    }

    /**
     * Gets the span where the error occurred.
     * This span represents the location in the source code where the error occurred. It might 
     * be null if the source code is not available.
     * 
     * @return The span where the error occurred.
     * @see Span
     */
    public Span getSpan() {
        return span;
    }

    /**
     * Gets the context of the error.
     * This additionnal information can be used to provide more context about the error, for instance
     * when a particular token was expected but another one was found, it will contain the found token.
     * 
     * @return The context of the error.
     */
    public String getContext() {
        return context;
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
        return " (got " + (context != null ? context.toString() : "EOF") + ")";
    }
}