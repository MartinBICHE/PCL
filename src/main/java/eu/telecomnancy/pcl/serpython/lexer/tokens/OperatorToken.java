package eu.telecomnancy.pcl.serpython.lexer.tokens;

import eu.telecomnancy.pcl.serpython.common.Span;

/**
 * Represents an operator token.
 */
public class OperatorToken extends Token {

    /**
     * Creates an operator token.
     * 
     * @param span The location of the token in the source code.
     */
    public OperatorToken(Span span) {
        super(span);
    }

    public static class PlusToken extends OperatorToken {
        public PlusToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "PLUS," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class MinusToken extends OperatorToken {
        public MinusToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "MINUS," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class MultiplyToken extends OperatorToken {
        public MultiplyToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "MUL," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class DivideToken extends OperatorToken {
        public DivideToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "DIV," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class ModuloToken extends OperatorToken {
        public ModuloToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "MOD," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class CompareToken extends OperatorToken {
        public CompareToken(Span span) {
            super(span);
        }
    }

    public static class GreaterToken extends OperatorToken {
        public GreaterToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "GREATER," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class GreaterEqualToken extends OperatorToken {
        public GreaterEqualToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "GREATER_EQUAL," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class LessToken extends OperatorToken {
        public LessToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "LESS," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class LessEqualToken extends OperatorToken {
        public LessEqualToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "LESS_EQUAL," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class EqualToken extends OperatorToken {
        public EqualToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "EQUAL," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class NotEqualToken extends OperatorToken {
        public NotEqualToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "NOT_EQUAL," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class AndToken extends OperatorToken {
        public AndToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "AND," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class OrToken extends OperatorToken {
        public OrToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "OR," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class NotToken extends OperatorToken {
        public NotToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "NOT," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class AssignToken extends OperatorToken {
        public AssignToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "ASSIGN," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class OpeningParenthesisToken extends OperatorToken {
        public OpeningParenthesisToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "OPENING_PARENTHESIS," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class ClosingParenthesisToken extends OperatorToken {
        public ClosingParenthesisToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "CLOSING_PARENTHESIS," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class OpeningBracketToken extends OperatorToken {
        public OpeningBracketToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "OPENING_BRACKET," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class ClosingBracketToken extends OperatorToken {
        public ClosingBracketToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "CLOSING_BRACKET," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class ColonToken extends OperatorToken {
        public ColonToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "COLON," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    public static class CommaToken extends OperatorToken {
        public CommaToken(Span span) {
            super(span);
        }

        @Override
        public String toString() {
            return "OperatorToken{" +
                    "COMMA," +
                    "span=" + Span.formatSpan(span) + '}';
        }
    }

    @Override
    public String toString() {
        return "OperatorToken{" +
                "span=" + Span.formatSpan(span) + '}';
    }
}
