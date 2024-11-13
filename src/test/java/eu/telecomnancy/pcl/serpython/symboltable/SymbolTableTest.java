package eu.telecomnancy.pcl.serpython.symboltable;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import eu.telecomnancy.pcl.serpython.ast.ArrayExpression;
import eu.telecomnancy.pcl.serpython.ast.BooleanLitteral;
import eu.telecomnancy.pcl.serpython.ast.Expression;
import eu.telecomnancy.pcl.serpython.ast.Expression.ArrayGet;
import eu.telecomnancy.pcl.serpython.ast.FunctionCall;
import eu.telecomnancy.pcl.serpython.ast.Identifier;
import eu.telecomnancy.pcl.serpython.ast.NoneLitteral;
import eu.telecomnancy.pcl.serpython.ast.NumberLitteral;
import eu.telecomnancy.pcl.serpython.ast.StringLitteral;
import eu.telecomnancy.pcl.serpython.errors.ParserError;
import eu.telecomnancy.pcl.serpython.lexer.tokens.*;
import eu.telecomnancy.pcl.serpython.ast.*;
import java.util.*;

public class SymbolTableTest {
    @Test
    public void testSymbolTableCreation() {
        SymbolTable symbolTable = new SymbolTable();
    }
}
