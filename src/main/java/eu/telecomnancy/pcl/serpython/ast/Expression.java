package eu.telecomnancy.pcl.serpython.ast;

public abstract class Expression extends AbstractNode {
    public static class Negation extends Expression {
        private Expression expression;

        public Negation(Expression expression) {
            this.expression = expression;
        }

        public Expression getInner() {
            return expression;
        }
    }

    public static abstract class BinaryOperation extends Expression {
        private Expression left;
        private Expression right;

        public BinaryOperation(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        public Expression getLeft() {
            return left;
        }

        public Expression getRight() {
            return right;
        }
    }

    public static class Multiplication extends BinaryOperation {
        public Multiplication(Expression left, Expression right) {
            super(left, right);
        }
    }

    public static class Division extends BinaryOperation {
        public Division(Expression left, Expression right) {
            super(left, right);
        }
    }

    public static class Reminder extends BinaryOperation {
        public Reminder(Expression left, Expression right) {
            super(left, right);
        }
    }

    public static class Addition extends BinaryOperation {
        public Addition(Expression left, Expression right) {
            super(left, right);
        }
    }

    public static class Subtraction extends BinaryOperation {
        public Subtraction(Expression left, Expression right) {
            super(left, right);
        }
    }
}