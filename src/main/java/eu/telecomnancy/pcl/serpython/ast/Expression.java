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

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Negation other = (Negation) obj;
            return this.expression.equals(other.expression);
        }

        @Override
        public int hashCode() {
            return expression.hashCode() * 7 + 18;
        }

        @Override
        public String toString() {
            return "-(" + expression.toString() + ")";
        }
    }

    public static class Not extends Expression {
        private Expression expression;

        public Not(Expression expression) {
            this.expression = expression;
        }

        public Expression getInner() {
            return expression;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Not other = (Not) obj;
            return this.expression.equals(other.expression);
        }

        @Override
        public int hashCode() {
            return expression.hashCode() * 19 + 35;
        }

        @Override
        public String toString() {
            return "not(" + expression.toString() + ")";
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

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final BinaryOperation other = (BinaryOperation) obj;
            return this.left.equals(other.left) && this.right.equals(other.right);
        }

        public abstract String getOperator();

        @Override
        public int hashCode() {
            return left.hashCode() * 7 + right.hashCode() * 13 + 18 + getOperator().hashCode();
        }

        @Override
        public String toString() {
            return "(" + left.toString() +" "+ getOperator() +" "+ right.toString() + ")";
        }
    }

    public static class Multiplication extends BinaryOperation {
        public Multiplication(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "*";
        }
    }

    public static class Division extends BinaryOperation {
        public Division(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "//";
        }
    }

    public static class Reminder extends BinaryOperation {
        public Reminder(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "%";
        }
    }

    public static class Addition extends BinaryOperation {
        public Addition(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "+";
        }
    }

    public static class Subtraction extends BinaryOperation {
        public Subtraction(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "-";
        }
    }

    public static class CompareEq extends BinaryOperation {
        public CompareEq(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "==";
        }
    }

    public static class CompareNeq extends BinaryOperation {
        public CompareNeq(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "!=";
        }
    }

    public static class CompareLt extends BinaryOperation {
        public CompareLt(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "<";
        }
    }

    public static class CompareLte extends BinaryOperation {
        public CompareLte(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "<=";
        }
    }

    public static class CompareGt extends BinaryOperation {
        public CompareGt(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return ">";
        }
    }

    public static class CompareGte extends BinaryOperation {
        public CompareGte(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return ">=";
        }
    }

    public static class And extends BinaryOperation {
        public And(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "and";
        }
    }

    public static class Or extends BinaryOperation {
        public Or(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public String getOperator() {
            return "or";
        }
    }
}