package eu.telecomnancy.pcl.serpython.parser;

import eu.telecomnancy.pcl.serpython.ast.Statement;
import eu.telecomnancy.pcl.serpython.ast.Statement.AssignmentStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ExpressionStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IndexedAssignementStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.PrintStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ReturnStatement;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.BeginToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.EndToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.NewlineToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.PrintToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.ReturnToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.AssignToken;
import eu.telecomnancy.pcl.serpython.parser.ExprParser;

import java.util.ArrayList;

import eu.telecomnancy.pcl.serpython.ast.Block;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Expression.ArrayGet;
import eu.telecomnancy.pcl.serpython.ast.Identifier;

public class StmtParser {
    
    public static Statement parseSimpleStatement(Parser parser) throws ParserError {
        if (parser.peek() instanceof ReturnToken) {
            return parseReturnStatement(parser);
        } else if (parser.peek() instanceof PrintToken) {
            return parsePrintStatement(parser);
        } 
        Expression left = ExprParser.parseExpr(parser);
        if (parser.peek() instanceof AssignToken) {
            parser.consume();
            Expression right = ExprParser.parseExpr(parser);
            if (left instanceof Identifier) {
                AssignmentStatement assignmentStatement = new AssignmentStatement((Identifier)left,right);
                return assignmentStatement;
            } else if (left instanceof ArrayGet) {
                IndexedAssignementStatement indexedAssignementStatement = new IndexedAssignementStatement((ArrayGet)left,right);
                return indexedAssignementStatement;
            } else {
                throw new ParserError(ParserErrorKind.CannotAssignToExpression);
            }
        } else {
            ExpressionStatement expressionStatement = new ExpressionStatement(left);
            return expressionStatement;
        }
    }

    public static ReturnStatement parseReturnStatement(Parser parser) throws ParserError {
        parser.consume();
        Expression expression = ExprParser.parseExpr(parser);
        ReturnStatement returnStatement = new ReturnStatement(expression);
        return returnStatement;
    }

    public static PrintStatement parsePrintStatement(Parser parser) throws ParserError {
        parser.consume();
        Expression expression = ExprParser.parseExpr(parser);
        PrintStatement printStatement = new PrintStatement(expression);
        return printStatement;
    }

    public static Block parseBlock(Parser parser) throws ParserError {
        ArrayList<Statement> statementList = new ArrayList<Statement>();
        if (parser.peek() instanceof NewlineToken) {
            parser.consume();
        } else {
            statementList.add(parseSimpleStatement(parser));
            if (!(parser.peek() instanceof NewlineToken)) {
                throw new ParserError(ParserErrorKind.ExpectedNewLine);
            }
            parser.consume();
        }
         if (!(parser.peek() instanceof BeginToken)) {
                throw new ParserError(ParserErrorKind.ExpectedBegin);
        }
        parser.consume();
        while (!(parser.peek() instanceof EndToken)) {
            statementList.add(parseStatement(parser));
        }
        parser.consume();
        Block block = new Block(statementList);
        return block;
    }

    public static Statement parseStatement(Parser parser) throws ParserError {
        return null;
    }
}
