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

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        // Define options
        Options options = new Options();

        // Input modes
        options.addOption(Option.builder("f")
                .longOpt("file")
                .desc("Specify the input file")
                .hasArg()
                .argName("filename")
                .build());

        options.addOption(Option.builder("c")
                .longOpt("code")
                .desc("Specify the input code as a string")
                .hasArg()
                .argName("code")
                .build());

        // Operations
        options.addOption(Option.builder("t")
                .longOpt("tokenize")
                .desc("Tokenize the input and display tokens")
                .build());

        options.addOption(Option.builder("p")
                .longOpt("parse")
                .desc("Parse the input (default behavior)")
                .build());

        options.addOption(Option.builder("v")
                .longOpt("visualize")
                .desc("Visualize the parse tree as Mermaid code")
                .build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        String sourceCode = null;

        try {
            CommandLine cmd = parser.parse(options, args);

            // Ensure input mode is provided
            if (!cmd.hasOption("file") && !cmd.hasOption("code")) {
                System.err.println("Error: Either --file or --code must be specified.");
                formatter.printHelp("compiler", options);
                System.exit(1);
            }

            // Read input
            String input = null;
            if (cmd.hasOption("file")) {
                input = cmd.getOptionValue("file");
                System.out.println("Reading input from file: " + input);
                sourceCode = readFile(input);
            } else if (cmd.hasOption("code")) {
                input = cmd.getOptionValue("code");
                System.out.println("Using provided code...");
                sourceCode = input;
            }

            // Handle operations
            if (cmd.hasOption("tokenize")) {
                System.out.println("Tokenizing input...");
                doTokenizeOnly(sourceCode);
            } else if (cmd.hasOption("visualize")) {
                System.out.println("Visualizing parse tree...");
                doVisualizeOnly(sourceCode);
            } else {
                System.out.println("Parsing input...");
                doParse(sourceCode);
            }

        } catch (ParseException e) {
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
            formatter.printHelp("compiler", options);
        } catch (IOException e) {
            System.err.println("Error whild reading input file: " + e.getMessage());
        }
    }

    private static String readFile(String filePath) throws IOException {
        String fileContent = Files.readString(Paths.get(filePath));
        return fileContent;
    }

    private static void doTokenizeOnly(String sourceCode) {
        ArrayList<Token> tokens = null;
        try {
            Lexer lexer = new Lexer(sourceCode);
            tokens = lexer.tokenize();
            for (var token : tokens) {
                System.out.println(token.toString()+" ");
            }
        } catch (LexerError e) {
            e.printError();
        }
    }

    private static void doVisualizeOnly(String sourceCode) {
        ArrayList<Token> tokens = null;
        try {
            Lexer lexer = new Lexer(sourceCode);
            tokens = lexer.tokenize();
            Parser parser = new Parser(tokens);
            Program program = parser.parse();
            Visualizer visualizer = new Visualizer();
            visualizer.visualize(program);
            String mermaidTree = visualizer.getGraph();
            System.out.println(mermaidTree); 
        } catch (LexerError e) {
            e.printError();
        } catch (ParserError e) {
            e.printError(sourceCode);
        }
    }

    private static void doParse(String sourceCode) {
        ArrayList<Token> tokens = null;
        try {
            Lexer lexer = new Lexer(sourceCode);
            tokens = lexer.tokenize();
            Parser parser = new Parser(tokens);
            Program program = parser.parse();
        } catch (LexerError e) {
            e.printError();
        } catch (ParserError e) {
            e.printError(sourceCode);
        }
    }
}
