package eu.telecomnancy.pcl.serpython;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.lexer.Lexer;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");

        if (args.length < 1) {
            System.out.println("no arg!");
        }

        String filePath = args[0]; 

        try {
            String fileContent = Files.readString(Paths.get(filePath));
            System.out.println("Entrée :");
            System.out.println(fileContent);

            Lexer lexer = new Lexer(fileContent);
            ArrayList<Token> tokens = lexer.tokenize();
            for (var token : tokens) {
                System.out.println(token.toString()+" ");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (LexerError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
