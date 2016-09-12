package main.java;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import main.java.lexer.LexerStandalone;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adrian
 */
public class LexerStandaloneTest {
    
    public String readInput(String[] args){
        String res = null;
        // Backs up the original Standard output
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream(100);
            PrintStream capture = new PrintStream(os);
            // From this point on, everything printed to System.out will get captured
            System.setOut(capture);
            // Execute the Lexer
            LexerStandalone.main(args);
            capture.flush();

            res = os.toString();
        } finally {
            // Restore the original Standard output
            System.setOut(originalOut);
        }
//        System.out.println("Captured output: \n" + res);
        return res;
    }
    
    public String readOutputExpected(String args) throws IOException{
        String res = null;
        BufferedReader br = new BufferedReader(new FileReader(args));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            res = sb.toString();

        } finally {
            br.close();
        }
//        System.out.println("Captured output: " + res);
        return res;
    }
    
    /**
     * Test of Keywords
     * @throws java.lang.Exception
     */
    @Test
    public void testKeywords() throws Exception {
            System.out.println("Testing Keywords");
            String[] args1 = {"test/resource/lexer/keywords.ctds"};
            String args2 = "test/resource/lexer/expectedOutput/keywords.ctds";
            
            String input = readInput(args1);
            String output = readOutputExpected(args2);
            
            assertEquals(input, output);
    }
    
    /**
     * Test of Delimiters
     * @throws java.lang.Exception
     */
    @Test
    public void testDelimiters() throws Exception {
            System.out.println("Testing Delimiters");
            String[] args1 = {"test/resource/lexer/delimiters.ctds"};
            String args2 = "test/resource/lexer/expectedOutput/delimiters.ctds";
            
            String input = readInput(args1);
            String output = readOutputExpected(args2);
            
            assertEquals(input, output);
    }

    /**
     * Test of Identifiers
     * @throws java.lang.Exception
     */
    @Test
    public void testIdentifiers() throws Exception {
            System.out.println("Testing Identifiers");
            String[] args1 = {"test/resource/lexer/identifiers.ctds"};
            String args2 = "test/resource/lexer/expectedOutput/identifiers.ctds";
            
            String input = readInput(args1);
            String output = readOutputExpected(args2);
            
            assertEquals(input, output);
    }
    
    /**
     * Test of Literals
     * @throws java.lang.Exception
     */
    @Test
    public void testLiterals() throws Exception {
            System.out.println("Testing Literals");
            String[] args1 = {"test/resource/lexer/literals.ctds"};
            String args2 = "test/resource/lexer/expectedOutput/literals.ctds";
            
            String input = readInput(args1);
            String output = readOutputExpected(args2);
            
            assertEquals(input, output);
    }
    
    /**
     * Test of Operators
     * @throws java.lang.Exception
     */
    @Test
    public void testOperators() throws Exception {
            System.out.println("Testing Operators");
            String[] args1 = {"test/resource/lexer/operators.ctds"};
            String args2 = "test/resource/lexer/expectedOutput/operators.ctds";
            
            String input = readInput(args1);
            String output = readOutputExpected(args2);
            
            assertEquals(input, output);
    }
    
}
