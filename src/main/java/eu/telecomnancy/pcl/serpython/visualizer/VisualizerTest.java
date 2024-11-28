package eu.telecomnancy.pcl.serpython.visualizer;

import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.ast.Block;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Expression.Addition;
import eu.telecomnancy.pcl.serpython.ast.Expression.Division;
import eu.telecomnancy.pcl.serpython.ast.Expression.Multiplication;
import eu.telecomnancy.pcl.serpython.ast.Expression.Reminder;
import eu.telecomnancy.pcl.serpython.ast.Expression.Subtraction;
import eu.telecomnancy.pcl.serpython.ast.Function;
import eu.telecomnancy.pcl.serpython.ast.FunctionCall;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.Program;
import eu.telecomnancy.pcl.serpython.ast.Statement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ForStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.PrintStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ReturnStatement;

public class VisualizerTest {
    public static void main(String[] useless) {
        Visualizer visualizer = new Visualizer();
        Expression expr1 = new Addition(
            new Subtraction (
                new NumberLitteral(3),
                new NumberLitteral(2)
            ),
            new Division(
                new NumberLitteral(69),
                new NumberLitteral(1337)
            )
        );
        Expression expr2 = new Multiplication(
            new Reminder(
                new NumberLitteral(42),
                new NumberLitteral(69)
            ),
            new Addition(
                new NumberLitteral(1337),
                new NumberLitteral(5147)
            )
        );
        PrintStatement printStatement = new PrintStatement(expr1);
        ReturnStatement returnStatement = new ReturnStatement(expr2);
        ArrayList<Statement> statements = new ArrayList<>();
        statements.add(printStatement);
        statements.add(returnStatement);
        Expression rangeArgument = new NumberLitteral(42);
        Expression args[] = new Expression[1];
        args[0] = rangeArgument;
        ForStatement forStatement = new ForStatement(
            new FunctionCall("range", args),
            new Identifier("i"),
            new Block(statements)
        );
        ArrayList<Statement> functionStatements = new ArrayList<>();
        functionStatements.add(forStatement);
        Function function = new Function(
            new Identifier("f"),
            new ArrayList<>(),
            new Block(functionStatements)
        );
        ArrayList<Function> functions = new ArrayList<>();
        functions.add(function);
        Program program = new Program(functions, new Block(new ArrayList<>()));
        visualizer.visualize(program);
        System.out.println(visualizer.getGraph());
    }
}
