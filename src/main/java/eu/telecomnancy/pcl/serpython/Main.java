package eu.telecomnancy.pcl.serpython;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.Lexer;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;
import eu.telecomnancy.pcl.serpython.parser.Parser;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("no arg!");
        }

        String filePath = args[0]; 

        String fileContent = null;
        try {
            fileContent = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        
        ArrayList<Token> tokens = null;

        try {
            Lexer lexer = new Lexer(fileContent);
            tokens = lexer.tokenize();
            for (var token : tokens) {
                System.out.println(token.toString()+" ");
            }
        } catch (LexerError e) {
            System.out.println("Syntax error");
            e.printError();
        }

        try {
            Parser parser = new Parser(fileContent, tokens);
            parser.parse();
        } catch (ParserError e) {
            System.out.println("Parsing error");
        }
    }
}
