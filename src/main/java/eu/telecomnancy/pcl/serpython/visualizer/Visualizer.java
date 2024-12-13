package eu.telecomnancy.pcl.serpython.visualizer;

import eu.telecomnancy.pcl.serpython.ast.ArrayExpression;
import eu.telecomnancy.pcl.serpython.ast.Block;
import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.Program;
import eu.telecomnancy.pcl.serpython.ast.Statement;
import eu.telecomnancy.pcl.serpython.ast.Statement.AssignmentStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ExpressionStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ForStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IfStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IndexedAssignementStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.PrintStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ReturnStatement;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression.ArrayGet;
import eu.telecomnancy.pcl.serpython.ast.Expression.BinaryOperation;
import eu.telecomnancy.pcl.serpython.ast.Expression.Negation;
import eu.telecomnancy.pcl.serpython.ast.Expression.Not;
import eu.telecomnancy.pcl.serpython.ast.Function;
import eu.telecomnancy.pcl.serpython.ast.FunctionCall;

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

    public void visualize(Program program) {
        String nodeName = getNewName();
        String nodeDescription = " ROOT ";
        for(Function function : program.getFunctions()) {
            Pair<String, String> functionPair = visualiseFunction(function);
            String functionName = functionPair.getFirst();
            String functionDescription = functionPair.getSecond();
            emit(nodeName + "[" + nodeDescription + "] --> " + functionName + "[" + functionDescription + "];\n");
        }
        Pair<String, String> block = visualiseBlock(program.getBlock());
        String blockName = block.getFirst();
        String blockDescription = block.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + blockName + "[" + blockDescription + "];\n");
    }

    public Pair<String, String> visualiseStatement(Statement statement) {
        if(statement instanceof ReturnStatement) {
            return visualiseReturnStatement((ReturnStatement) statement);
        } else if(statement instanceof PrintStatement) {
            return visualizePrintStatement((PrintStatement) statement);
        } else if(statement instanceof ExpressionStatement) {
            return visualiseExpression(((ExpressionStatement) statement).getExpression());
        } else if(statement instanceof IfStatement) {
            return visualiseIfStatement((IfStatement) statement);
        } else if(statement instanceof ForStatement) {
            return visualiseForStatement((ForStatement) statement);
        } else if(statement instanceof AssignmentStatement) {
            return visualiseAssignmentStatement((AssignmentStatement) statement);
        } else if(statement instanceof IndexedAssignementStatement) {
            return visualiseIndexedAssignementStatement((IndexedAssignementStatement) statement);
        }
        else {
            return null;
        }
    }

    public Pair<String, String> visualiseIndexedAssignementStatement(IndexedAssignementStatement statement) {
        String nodeName = getNewName();
        String nodeDescription = " indexedAssignement ";
        Pair<String, String> left = visualiseExpression(statement.getLeft());
        String leftName = left.getFirst();
        String leftDescription = left.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + leftName + "[" + leftDescription + "];\n");
        Pair<String, String> right = visualiseExpression(statement.getRight());
        String rightName = right.getFirst();
        String rightDescription = right.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + rightName + "[" + rightDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseAssignmentStatement(AssignmentStatement statement) {
        String nodeName = getNewName();
        String nodeDescription = " assignment ";
        Pair<String, String> identifier = visualiseExpression(statement.getIdentifier());
        String identifierName = identifier.getFirst();
        String identifierDescription = identifier.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + identifierName + "[" + identifierDescription + "];\n");
        Pair<String, String> expression = visualiseExpression(statement.getExpression());
        String expressionName = expression.getFirst();
        String expressionDescription = expression.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + expressionName + "[" + expressionDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseFunction(Function function) {
        String nodeName = getNewName();
        String nodeDescription = " function ";
        Pair<String, String> identifier = visualiseExpression(function.getName());
        String identifierName = identifier.getFirst();
        String identifierDescription = identifier.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + identifierName + "[" + identifierDescription + "];\n");
        Pair<String, String> block = visualiseBlock(function.getInstructions());
        String blockName = block.getFirst();
        String blockDescription = block.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + blockName + "[" + blockDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseReturnStatement(ReturnStatement statement) {
        String nodeName = getNewName();
        String nodeDescription = " return ";
        Pair<String, String> expression = visualiseExpression(statement.getExpression());
        String expressionName = expression.getFirst();
        String expressionDescription = expression.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + expressionName + "[" + expressionDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualizePrintStatement(PrintStatement statement) {
        String nodeName = getNewName();
        String nodeDescription = " print ";
        Pair<String, String> expression = visualiseExpression(statement.getExpression());
        String expressionName = expression.getFirst();
        String expressionDescription = expression.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + expressionName + "[" + expressionDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseIfStatement(IfStatement statement) {
        String nodeName = getNewName();
        String nodeDescription = " if ";
        Pair<String, String> expression = visualiseExpression(statement.getExpression());
        String expressionName = expression.getFirst();
        String expressionDescription = expression.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + expressionName + "[" + expressionDescription + "];\n");
        Pair<String, String> ifBlock = visualiseBlock(statement.getIfBlock());
        String ifBlockName = ifBlock.getFirst();
        String ifBlockDescription = ifBlock.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + ifBlockName + "[" + ifBlockDescription + "];\n");
        if(statement.getElseBlock() != null) {
            Pair<String, String> elseBlock = visualiseBlock(statement.getElseBlock());
            String elseBlockName = elseBlock.getFirst();
            String elseBlockDescription = elseBlock.getSecond();
            emit(nodeName + "[" + nodeDescription + "] --> " + elseBlockName + "[" + elseBlockDescription + "];\n");
        }
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseForStatement(ForStatement statement) {
        String nodeName = getNewName();
        String nodeDescription = " for ";
        Pair<String, String> identifier = visualiseExpression(statement.getIdent());
        String identifierName = identifier.getFirst();
        String identifierDescription = identifier.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + identifierName + "[" + identifierDescription + "];\n");
        Pair<String, String> expression = visualiseExpression(statement.getExpression());
        String expressionName = expression.getFirst();
        String expressionDescription = expression.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + expressionName + "[" + expressionDescription + "];\n");
        Pair<String, String> block = visualiseBlock(statement.getBlock());
        String blockName = block.getFirst();
        String blockDescription = block.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + blockName + "[" + blockDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseBlock(Block block) {
        String nodeName = getNewName();
        String nodeDescription = " block ";
        for(Statement statement : block.get()) {
            Pair<String, String> statementPair = visualiseStatement(statement);
            String statementName = statementPair.getFirst();
            String statementDescription = statementPair.getSecond();
            emit(nodeName + "[" + nodeDescription + "] --> " + statementName + "[" + statementDescription + "];\n");
        }
        return new Pair<>(nodeName, nodeDescription);
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
        } else if(expression instanceof FunctionCall) {
            return visualiseFunctionCall((FunctionCall) expression);
        } else if(expression instanceof BinaryOperation) {
            return visualiseBinaryOperator((BinaryOperation) expression);
        } else if(expression instanceof Negation) {
            return visualiseNegation((Negation) expression);
        } else if(expression instanceof Not) {
            return visualiseNot((Not) expression);
        } else if(expression instanceof ArrayExpression) {
            return visualiseArrayExpression((ArrayExpression) expression);
        } else if(expression instanceof ArrayGet) {
            return visualiseArrayGet((ArrayGet) expression);
        }
        else {
            return null;
        }
    }

    public Pair<String, String> visualiseArrayGet(ArrayGet expression) {
        String nodeName = getNewName();
        String nodeDescription = " arrayGet ";
        Pair<String, String> array = visualiseExpression(expression.getArray());
        String arrayName = array.getFirst();
        String arrayDescription = array.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + arrayName + "[" + arrayDescription + "];\n");
        Pair<String, String> index = visualiseExpression(expression.getIndex());
        String indexName = index.getFirst();
        String indexDescription = index.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + indexName + "[" + indexDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseArrayExpression(ArrayExpression expression) {
        String nodeName = getNewName();
        String nodeDescription = " array ";
        for(Expression element : expression.getElements()) {
            Pair<String, String> elementPair = visualiseExpression(element);
            String elementName = elementPair.getFirst();
            String elementDescription = elementPair.getSecond();
            emit(nodeName + "[" + nodeDescription + "] --> " + elementName + "[" + elementDescription + "];\n");
        }
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseFunctionCall(FunctionCall expression) {
        String nodeName = getNewName();
        String nodeDescription = " functionCall ";
        String functionName = getNewName();
        String functionDescription = expression.getName();
        emit(nodeName + "[" + nodeDescription + "] --> " + functionName + "[" + functionDescription + "];\n");
        for(Expression argument : expression.getArguments()) {
            Pair<String, String> argumentPair = visualiseExpression(argument);
            String argumentName = argumentPair.getFirst();
            String argumentDescription = argumentPair.getSecond();
            emit(nodeName + "[" + nodeDescription + "] --> " + argumentName + "[" + argumentDescription + "];\n");
        }
        return new Pair<>(nodeName, nodeDescription);
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

    public Pair<String, String> visualiseNot(Not expression) {
        String nodeName = getNewName();
        String nodeDescription = " \\not ";
        Pair<String, String> inner = visualiseExpression(expression.getInner());
        String innerName = inner.getFirst();
        String innerDescription = inner.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + innerName + "[" + innerDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public Pair<String, String> visualiseNegation(Negation expression) {
        String nodeName = getNewName();
        String nodeDescription = " \\- ";
        Pair<String, String> inner = visualiseExpression(expression.getInner());
        String innerName = inner.getFirst();
        String innerDescription = inner.getSecond();
        emit(nodeName + "[" + nodeDescription + "] --> " + innerName + "[" + innerDescription + "];\n");
        return new Pair<>(nodeName, nodeDescription);
    }

    public String getGraph() {
        return this.builder.toString();
    }
}
