package eu.telecomnancy.pcl.serpython;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.ast.Program;
import eu.telecomnancy.pcl.serpython.errors.LexerError;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.Lexer;
import eu.telecomnancy.pcl.serpython.lexer.tokens.Token;
import eu.telecomnancy.pcl.serpython.parser.Parser;
import eu.telecomnancy.pcl.serpython.visualizer.Visualizer;
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

            System.out.println("\n\n");

            Parser parser = new Parser(tokens);
            Program program = parser.parse();
            Visualizer visualizer = new Visualizer();
            visualizer.visualize(program);
            String mermaidTree = visualizer.getGraph();
            System.out.println(mermaidTree);
            
        } catch (LexerError e) {
            e.printError();
        } catch (ParserError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
