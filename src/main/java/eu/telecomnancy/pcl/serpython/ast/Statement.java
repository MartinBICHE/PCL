package eu.telecomnancy.pcl.serpython.ast;

import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.ast.Expression.ArrayGet;

public abstract class Statement {
    public static class ReturnStatement extends Statement {
        private Expression expression;

        public ReturnStatement(Expression expression) {
            this.expression = expression;
        }

        public Expression getExpression() {
            return expression;
        }

        @Override
        public String toString() {
            return "return " + expression.toString(); 
        }
    }

    public static class PrintStatement extends Statement {
        private Expression expression;

        public PrintStatement(Expression expression) {
            this.expression = expression;
        }

        public Expression getExpression() {
            return expression;
        }

        @Override
        public String toString() {
            return "print(" + expression.toString() + ")";
        }
    }

    public static class AssignmentStatement extends Statement {
        private Identifier identifier;
        private Expression expression;

        public AssignmentStatement(Identifier left, Expression expression) {
            this.expression = expression; 
            this.identifier = left;
        }

        public Identifier getIdentifier() {
            return identifier;
        }

        public Expression getExpression() {
            return expression;
        }

        @Override
        public String toString() {
            return identifier + "=" + expression.toString();
        }
    }

    public static class IndexedAssignementStatement extends Statement {
        private ArrayGet left;
        private Expression right;

        public IndexedAssignementStatement(ArrayGet left, Expression right) {
            this.left = left;
            this.right = right;
        }

        public ArrayGet getLeft() {
            return left;
        }

        public Expression getRight() {
            return right;
        }

        public String toString() {
            return left.toString() + "=" + right.toString();
        }
    }

    public static class ExpressionStatement extends Statement {
        private Expression expression;

        public ExpressionStatement(Expression expression) {
            this.expression = expression;
        }

        public Expression getExpression() {
            return expression;
        }
        
        public String toString() {
            return expression.toString();
        }
    }

}
