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

    /**
     * Returns the length of the span.
     *
     * @return the length of the span
     */
    public int getLength() {
        return length;
    }
}
