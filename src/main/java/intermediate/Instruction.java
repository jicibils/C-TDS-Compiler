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

    //unary
    NOT,
    MINUSFLOAT,
    MINUSINT,
    
    //assigments
    ASSIGNI,        //ASSIGN INTEGER
    ASSIGNF,        //ASSIGN FLOAT
    ASSIGNB,        //ASSIGN BOOL
    INCI,           //INC INTEGER
    INCF,           //INC FLOAT
    DECI,           //DEC INTEGER
    DECF,           //DEC FLOAT
    
    //literal assignments
    ASSIGNLITFLOAT,
    ASSIGNLITINT,
    ASSIGNLITBOOL,
    
    //delimiters
    LABELBEGINCLASS,
    LABELBEGINMETHOD,
    LABELENDMETHOD,

    //push (use in methodCall for params)
    PUSHID,
    PUSHPARAM,

    //call (use in methodCall)
    CALL,

    //initialization
    INITINT,
    INITFLOAT,
    INITBOOL,
    INITARRAY,

    //jumps
    JF,             //JUMP FOR FALSE
    JMP,            //JUMP
    
    LABEL,          //Label
    LESS,          

    ;

}
