package eu.telecomnancy.pcl.serpython.visualizer;

import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression.BinaryOperation;

public class Visualizer {
    private int id;
    private StringBuilder builder;

    public Visualizer() {
        this.id = 0;
        this.builder = new StringBuilder();
        this.builder.append("graph\n");
    }

    public void emit(String str) {
        this.builder.append(str);
    }

    public String getNewName() {
        this.id += 1;
        return "Visualizer" + this.id;
    }

    public Pair<String, String> visualiseExpression(Expression expression) {
        if(expression instanceof BooleanLitteral) {
            return new Pair<>(getNewName(), expression.toString());
        } else if(expression instanceof NumberLitteral) {
            return new Pair<>(getNewName(), expression.toString());
        } else if(expression instanceof StringLitteral) {
            return new Pair<>(getNewName(), expression.toString());
        } else if(expression instanceof NoneLitteral) {
            return new Pair<>(getNewName(), expression.toString());
        } else if(expression instanceof Identifier) {
            return new Pair<>(getNewName(), expression.toString());
        } else if(expression instanceof BinaryOperation) {
            return visualiseBinaryOperator((BinaryOperation) expression);
        } else  {
            return null;
        }
    }

    public Pair<String, String> visualiseBinaryOperator(BinaryOperation expression) {
        String nodeName = getNewName();
        String nodeDescription = " \\" + expression.getOperator() + " ";
        Pair<String, String> left = visualiseExpression(expression.getLeft());
        Pair<String, String> right = visualiseExpression(expression.getRight());
        String leftName = left.getFirst();
        String rightName = right.getFirst();
        String leftDescription = left.getSecond();
        String rightDescription = right.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + leftName + "[" + leftDescription + "];\n");
        emit(nodeName + "[" + nodeDescription + "] --> " + rightName + "[" + rightDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public String getGraph() {
        return this.builder.toString();
    }
}
