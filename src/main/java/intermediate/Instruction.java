/*Enumerate class with instructions*/

package main.java.intermediate;

/**
 *
 * @author Ezequiel Arangue
 */
public enum Instruction {
    
    //arithmetical
    ADDFLOAT,
    ADDINT,

    SUBFLOAT,
    SUBINT,

    MULTFLOAT,
    MULTINT,

    DIVFLOAT,
    DIVINT,
    
    MOD,
    
    //conditional
    ANDAND,
    OROR,

    //relational
    LT,             //less than
    LTEQ,           //less than eq
    GT,             //great than
    GTEQ,           //great than eq

    //equal

    EQEQ,
    NOTEQ,

    //assigments
    ASSIGN,
    INC,
    DEC,
    ;
}
