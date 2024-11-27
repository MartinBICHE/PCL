package eu.telecomnancy.pcl.serpython.ast;

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
        
        @Override
        public String toString() {
            return expression.toString();
        }
    }

    public static class IfStatement extends Statement {
        private Expression expression;
        private Block ifBlock;
        private Block elseBlock;

        public IfStatement(Expression expression, Block ifBlock, Block elseBlock) {
            this.expression = expression;
            this.ifBlock = ifBlock;
            this.elseBlock = elseBlock;
        }

        public Expression getExpression() {
            return expression;
        }

        public Block getIfBlock() {
            return ifBlock;
        }

        public Block getElseBlock() {
            return elseBlock;
        }

        @Override
        public String toString() {
            if(elseBlock == null) {
                return "if" + expression.toString() + ":" + ifBlock.toString();
            } else {
                return "if" + expression.toString() + ":" + ifBlock.toString() + "else" + elseBlock.toString();
            }
        }


    }

    public static class ForStatement extends Statement {
        private Expression expression;
        private Identifier ident;
        private Block block;

        public ForStatement (Expression expression, Identifier ident, Block block) {
            this.expression = expression;
            this.ident = ident;
            this.block = block;
        }

        public Expression getExpression() {
            return expression;
        }

        public Identifier getIdent() {
            return ident;
        }

        public Block getBlock() {
            return block;
        }

        public Expression getExpression() {
            return expression;
        }

        public Identifier getIdent() {
            return ident;
        }

        public Block getBlock() {
            return block;
        }

        @Override
        public String toString(){
            return "for" +  ident.toString() + "in" + expression.toString() + ":" + block.toString();
        }
    }

}
