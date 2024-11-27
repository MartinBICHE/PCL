package eu.telecomnancy.pcl.serpython.visualizer;

import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Expression.Addition;
import eu.telecomnancy.pcl.serpython.ast.Expression.Division;
import eu.telecomnancy.pcl.serpython.ast.Expression.Subtraction;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;

public class VisualizerTest {
    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
        Expression expression = new Addition(
            new Subtraction (
                new NumberLitteral(3),
                new NumberLitteral(2)
            ),
            new Division(
                new NumberLitteral(69),
                new NumberLitteral(1337)
            )
        );
        visualizer.visualiseExpression(expression);
        System.out.println(visualizer.getGraph());
    }
}
