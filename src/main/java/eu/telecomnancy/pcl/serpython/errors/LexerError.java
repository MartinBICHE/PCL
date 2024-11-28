package eu.telecomnancy.pcl.serpython.errors;

import eu.telecomnancy.pcl.serpython.lexer.Lexer;
import eu.telecomnancy.pcl.serpython.termcolor.ConsoleColors;

public class LexerError extends Exception {
    private Lexer lexer;
    private String message;

    public LexerError(Lexer lexer, String message) {
        super(message);
        this.message = message;
        this.lexer = lexer;
    }

    private String getErrorLine() {
        String source = this.lexer.getSource();
        int line = this.lexer.getLastLine();
        String[] lines = source.split("\n");
        return lines[line - 1];
    }

    private int getErrorSpan() {
        String errorLine = getErrorLine();
        int span = 0;
        if(this.lexer.getLastLine() == this.lexer.getLine()) {
            span = this.lexer.getColumn() - this.lexer.getLastColumn() - 1;
        } else {
            span = errorLine.length() - this.lexer.getLastColumn();
        }
        return span;
    }

    public void printError() {
        String errorLine = getErrorLine();
        int span = getErrorSpan();
        String line = Integer.toString(this.lexer.getLastLine());
        for(int i = 0 ; i < line.length() / 2 + 1 ; i += 1) { System.out.print(" "); }
        System.out.println("--> lexer error (line " + this.lexer.getLastLine() + ")");
        for(int i = 0 ; i < line.length() ; i += 1) { System.out.print(" "); }
        System.out.println(ConsoleColors.GREEN + " | " + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + line + " | " + ConsoleColors.RESET + ConsoleColors.WHITE_BOLD_BRIGHT + errorLine + ConsoleColors.RESET);
        for(int i = 0 ; i < line.length() ; i += 1) { System.out.print(" "); }
        System.out.print(ConsoleColors.GREEN + " | " + ConsoleColors.RESET);
        for(int i = 0 ; i < this.lexer.getLastColumn() + 1 ; i += 1) { System.out.print(" "); }
        for(int i = 0 ; i < span ; i += 1) { System.out.print(ConsoleColors.RED_BOLD + "^" + ConsoleColors.RESET); }
        System.out.println();
        for(int i = 0 ; i < line.length() ; i += 1) { System.out.print(" "); }
        System.out.print(ConsoleColors.GREEN + " | " + ConsoleColors.RESET);
        for(int i = 0 ; i < this.lexer.getLastColumn() + 1 - this.message.length() / 2 ; i += 1) { System.out.print(" "); }
        System.out.println(ConsoleColors.RED_BOLD + this.message + ConsoleColors.RESET);
    }
}
