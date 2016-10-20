/*Enumerate class with instructions*/

package main.java.intermediate;

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

    //jumps
    JUMPF,        //JUMP FALSE

    //assigments
    ASSIGNI,        //ASSIGN INTEGER
    ASSIGNF,        //ASSIGN FLOAT
    ASSIGNB,        //ASSIGN BOOL
    INCI,           //INC INTEGER
    INCF,           //INC FLOAT
    DECI,           //DEC INTEGER
    DECF,           //DEC FLOAT
    ;
}
