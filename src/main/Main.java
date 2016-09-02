import java.io.*;

import java_cup.runtime.*;  
public class Main {

  static public void main(String argv[]) {

    try {

      Parser p = new Parser(new Lexer(new FileReader(argv[0])));
      Object result = p.parse().value;      

    } catch (Exception e) {

      e.printStackTrace();
      
    }

  }
}