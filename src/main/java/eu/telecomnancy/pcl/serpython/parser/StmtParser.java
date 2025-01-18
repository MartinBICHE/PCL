package eu.telecomnancy.pcl.serpython.parser;

import eu.telecomnancy.pcl.serpython.ast.Statement;
import eu.telecomnancy.pcl.serpython.ast.Statement.AssignmentStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ExpressionStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ForStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IfStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.IndexedAssignementStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.PrintStatement;
import eu.telecomnancy.pcl.serpython.ast.Statement.ReturnStatement;
import eu.telecomnancy.pcl.serpython.common.Span;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.errors.ParserErrorKind;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IdentToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.BeginToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.IndentToken.EndToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.ElseToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.ForToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.IfToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.InToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.NewlineToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.PrintToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.ReturnToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.AssignToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.ClosingParenthesisToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.ColonToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.CommaToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.OperatorToken.OpeningParenthesisToken;
import eu.telecomnancy.pcl.serpython.lexer.tokens.KeywordToken.DefToken;

import java.util.ArrayList;
import eu.telecomnancy.pcl.serpython.ast.Block;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Expression.ArrayGet;
import eu.telecomnancy.pcl.serpython.ast.Function;
import eu.telecomnancy.pcl.serpython.ast.Identifier;

public class StmtParser {
    
    public static Statement parseSimpleStatement(Parser parser) throws ParserError {
        Span lastPosition = parser.getPosition(); // get the last position for the error message
        // parse a statement
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
                Span errorSpan = Span.merge(lastPosition, parser.getPosition());
                throw new ParserError(ParserErrorKind.CannotAssignToExpression, errorSpan);
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
                throw new ParserError(ParserErrorKind.ExpectedNewLine, parser.getPosition(), parser.peek());
            }
            parser.consume();
            return new Block(statementList);
        }
        if (!(parser.peek() instanceof BeginToken)) {
            throw new ParserError(ParserErrorKind.ExpectedBegin, parser.getPosition(), parser.peek());
        }
        parser.consume();
        while (!(parser.peek() instanceof EndToken)) {
            parser.ignoreNewlines();
            try {
                statementList.add(parseStatement(parser));
            } catch (ParserError e) {
                parser.addError(e);
                ErrorHandlingStrategies.skipUntilNewlineToken(parser);
                parser.consume();
            }
        }
        parser.consume();
        Block block = new Block(statementList);
        return block;
    }

    public static Statement parseStatement(Parser parser) throws ParserError {
        if (parser.peek() instanceof ForToken){
            return parseForStatement(parser);
        }
        else if (parser.peek() instanceof IfToken) {
            return parseIfStatement(parser);
        }
        else{
            Statement state = parseSimpleStatement(parser);
            if (!(parser.peek() instanceof NewlineToken)){
                throw new ParserError(ParserErrorKind.ExpectedNewLine, parser.getPosition(), parser.peek());
            }
            parser.consume();
            return state;
        }
    }

    public static ForStatement parseForStatement(Parser parser) throws ParserError {
        if (parser.peek() instanceof ForToken) {
            parser.consume();
            Identifier ident = null;
            try {
                ident = AtomParser.parseIdentifier(parser);
            } catch (ParserError e) {
                parser.addError(e);
            }
            if (!(parser.peek() instanceof InToken)) {
                ParserError error = new ParserError(ParserErrorKind.ExpectedInToken, parser.getPosition(), parser.peek());
                parser.addError(error);
            } else {
                parser.consume();
            }
            Expression expre = null;
            try {
                expre = ExprParser.parseExpr(parser);
            } catch (ParserError e) {
                parser.addError(e);
                ErrorHandlingStrategies.skipUntilColon(parser);
            }
            if (!(parser.peek() instanceof ColonToken)){
                ParserError error = new ParserError(ParserErrorKind.ExpectedColonToken, parser.getPosition(), parser.peek());
                parser.addError(error);
                ErrorHandlingStrategies.skipUntilNewlineToken(parser);
            } else {
                parser.consume();
            }
            Block block = parseBlock(parser);
            return new ForStatement(expre, ident, block);
        }
        throw new ParserError(ParserErrorKind.ExpectedForToken, parser.getPosition(), parser.peek());
    }

    public static IfStatement parseIfStatement(Parser parser) throws ParserError {
        if (parser.peek() instanceof IfToken){
            parser.consume();
            Expression expre = null;
            try {
                expre = ExprParser.parseExpr(parser);
            } catch (ParserError e) {
                parser.addError(e);
                ErrorHandlingStrategies.skipUntilColon(parser);
            }
            if (!(parser.peek() instanceof ColonToken)) {
                ParserError error = new ParserError(ParserErrorKind.ExpectedColonToken, parser.getPosition(), parser.peek());
                parser.addError(error);
                ErrorHandlingStrategies.skipUntilColon(parser);
            } else {
                parser.consume();
            }
            Block ifblock = parseBlock(parser);
            if (parser.peek() instanceof ElseToken){
                parser.consume();
                if (!(parser.peek() instanceof ColonToken)) {
                    throw new ParserError(ParserErrorKind.ExpectedColonToken, parser.getPosition(), parser.peek());
                }
                parser.consume();
                Block elseblock = parseBlock(parser);
                return new IfStatement(expre, ifblock, elseblock);
            }
            return new IfStatement(expre, ifblock, null);
        }
        throw new ParserError(ParserErrorKind.ExpectedIfToken, parser.getPosition(), parser.peek());
    }



    public static ArrayList<Identifier> parseArguments(Parser parser) throws ParserError{
        if (!(parser.peek() instanceof IdentToken)){
            return new ArrayList<Identifier>();
        }

        ArrayList<Identifier> arguments = new ArrayList<Identifier>();
        try {
            arguments.add(AtomParser.parseIdentifier(parser));
        while (parser.peek() instanceof CommaToken){
            parser.consume();
            arguments.add(AtomParser.parseIdentifier(parser));
        }
        } catch (ParserError e) {
            parser.addError(e);
            ErrorHandlingStrategies.skipUntilClosingParenthesis(parser);
        }
        return arguments;

    }

    public static Function parseFunction(Parser parser) throws ParserError{
        if(!(parser.peek() instanceof DefToken)){
            throw new ParserError(ParserErrorKind.ExpectedDefToken, parser.getPosition(), parser.peek());
        }
        parser.consume();
        if(!(parser.peek() instanceof IdentToken)){
            throw new ParserError(ParserErrorKind.ExpectedIdentifier, parser.getPosition(), parser.peek());
        }
        Identifier name = AtomParser.parseIdentifier(parser);
        if(!(parser.peek() instanceof OpeningParenthesisToken)){
            throw new ParserError(ParserErrorKind.ExpectedOpeningParenthesis, parser.getPosition(), parser.peek());
        }
        parser.consume();
        ArrayList<Identifier> arguments = parseArguments(parser);
        if(!(parser.peek() instanceof ClosingParenthesisToken)){
            ParserError error = new ParserError(ParserErrorKind.ExpectedClosingParenthesis, parser.getPosition(), parser.peek());
            parser.addError(error);
            ErrorHandlingStrategies.skipUntilColon(parser);
        } else {
            parser.consume();
        }
        if(!(parser.peek() instanceof ColonToken)){
            ParserError error = new ParserError(ParserErrorKind.ExpectedColonToken, parser.getPosition(), parser.peek());
            parser.addError(error);
            ErrorHandlingStrategies.skipUntilNewlineToken(parser);
        } else {
            parser.consume();
        }
        Block block = parseBlock(parser);

        return new Function(name, arguments, block);
    }
}

