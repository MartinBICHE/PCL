package eu.telecomnancy.pcl.serpython.errors;

public enum ParserErrorKind {
    ExpectedString(1,"A string was expected"),
    ExpectedNumber(2,"A number was expected"),
    ExpectedIdentifier(3,"An identifier was expected"),
    ExpectedBoolean(4,"A boolean was expected"),
    ExpectedOperator(5,"An operator was expected"),
    ExpectedKeyword(6,"A keyword was expected"),
    UnexpectedEOF(7,"Unexpected end of file"),
    ExpectedClosingParenthesis(8,"Expected closing parenthesis"),
    ExpectedExpression(9,"Expected an expression");

    private final String message;
    private final int code;

    private ParserErrorKind(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}