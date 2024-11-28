package eu.telecomnancy.pcl.serpython.common;

/**
 * The Span class represents a span of text in a document.
 * It contains information about the starting line, starting column, and the length of the span.
 */
public class Span {
    private final int line;
    private final int column;
    private final int length;

    /**
     * Constructs a new Span with the specified line, column, and length.
     *
     * @param line   the starting line of the span
     * @param column the starting column of the span
     * @param length the length of the span
     */
    public Span(int line, int column, int length) {
        this.line = line;
        this.column = column;
        this.length = length;
    }

    /**
     * Returns the starting line of the span.
     *
     * @return the starting line of the span
     */
    public int getLine() {
        return line;
    }

    /**
     * Returns the starting column of the span.
     *
     * @return the starting column of the span
     */
    public int getColumn() {
        return column;
    }

    public static Span merge(Span span1, Span span2) {
        if (span1 == null) {
            return span2;
        }
        if (span2 == null) {
            return span1;
        }
        return new Span(Math.min(span1.getLine(), span2.getLine()), Math.min(span1.getColumn(), span2.getColumn()), Math.max(span1.getLine(), span2.getLine()) - Math.min(span1.getLine(), span2.getLine()) + Math.max(span1.getColumn(), span2.getColumn()) - Math.min(span1.getColumn(), span2.getColumn()));
    }

    /**
     * Returns the length of the span.
     *
     * @return the length of the span
     */
    public int getLength() {
        return length;
    }

    public String toString() {
        return "<" +
                "line=" + line +
                ", column=" + column +
                ", length=" + length +
                ">";
    }

    /**
     * Formats a Span object as a string.
     *
     * @param span the Span object to format, if null, the return string is "<null>"
     * @return a string representation of the Span object
     */
    public static String formatSpan(Span span) {
        if (span == null) {
            return "<null>";
        }
        return span.toString();
    }
}
