package eu.telecomnancy.pcl.serpython.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LexerTest {

    @Test
    public void testLexerExists() {
        try {
            Class.forName("eu.telecomnancy.pcl.serpython.lexer.Lexer");
        } catch (ClassNotFoundException e) {
            fail("Lexer class does not exist");
        }
    }
}