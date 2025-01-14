package eu.telecomnancy.pcl.serpython.errors;

/**
 * Enum representing different kinds of parser errors.
 */
public enum ParserErrorKind {
    ExpectedString(1,"A string was expected"),
    ExpectedNumber(2,"A number was expected"),
    ExpectedIdentifier(3,"An identifier was expected"),
    ExpectedBoolean(4,"A boolean was expected"),
    ExpectedOperator(5,"An operator was expected"),
    ExpectedKeyword(6,"A keyword was expected"),
    UnexpectedEOF(7,"Unexpected end of file"),
    ExpectedClosingParenthesis(8,"Expected closing parenthesis"),
    ExpectedExpression(9,"Expected an expression"),
    ExpectedClosingBracket(10,"Expected closing bracket"),
    ExpectedArray(11,"Expected an array"),
    ExpectedOpeningParenthesis(12,"Expected opening parenthesis"),
    CannotAssignToExpression(13,"Cannot assign to left value expression"),
    ExpectedBegin(14,"Expected begin token"),
    ExpectedNewLine(15,"Expected newline token"),
    ExpectedInToken(16,"Expected in token"),
    ExpectedColonToken(17,"Expected colon token"),
    ExpectedForToken(18,"Expected for token"),
    ExpectedIfToken(19,"Expected if token"),
    ExpectedDefToken(20,"Expected def token"),
    MissingColon(21, "Missing colon ':'");

    private final String message;
    private final int code;

    /**
     * Constructor for ParserErrorKind enum.
     * @param code The error code.
     * @param message The error message.
     */
    private ParserErrorKind(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get the error message.
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the error code.
     * @return The error code.
     */
    public int getCode() {
        return code;
    }
}