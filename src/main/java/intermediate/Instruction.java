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
    
    //return stmts
    RETURN,
    RETURNINT,
    RETURNFLOAT,
    RETURNBOOL,
    ;

    @Override
    public String toString() {
        switch(this) {
            case ADDFLOAT:
                return "ADDFLOAT";
            case ADDINT:
                return "ADDINT";
            case SUBFLOAT:
                return "SUBFLOAT";
            case SUBINT:
                return "SUBINT";
            case MULTFLOAT:
                return "MULTFLOAT";
            case MULTINT:
                return "MULTINT";
            case DIVFLOAT:
                return "DIVFLOAT";
            case DIVINT:
                return "DIVINT";
            case MOD:
                return "MOD";
            case ANDAND:
                return "ANDAND";
            case OROR:
                return "OROR";
            case LT:
                return "LT";
            case LTEQ:
                return "LTEQ";
            case GT:
                return "GT";
            case GTEQ:
                return "GTEQ";
            case EQEQ:
                return "EQEQ";
            case NOTEQ:
                return "NOTEQ";
            case NOT:
                return "NOT";
            case MINUSFLOAT:
                return "MINUSFLOAT";
            case MINUSINT:
                return "MINUSINT";
            case ASSIGNI:
                return "ASSIGNI";
            case ASSIGNF:
                return "ASSIGNF";
            case ASSIGNB:
                return "ASSIGNB";
            case INCI:
                return "INCI";
            case INCF:
                return "INCF";
            case DECI:
                return "DECI";
            case DECF:
                return "DECF";
            case ASSIGNLITFLOAT:
                return "ASSIGNLITFLOAT";
            case ASSIGNLITINT:
                return "ASSIGNLITINT";
            case ASSIGNLITBOOL:
                return "ASSIGNLITBOOL";
            case LABELBEGINCLASS:
                return "LABELBEGINCLASS";
            case LABELBEGINMETHOD:
                return "LABELENDMETHOD";
            case LABELENDMETHOD:
                return "LABELENDMETHOD";
            case PUSHID:
                return "PUSHID";
            case PUSHPARAM:
                return "PUSHPARAM";
            case CALL:
                return "CALL";
            case INITINT:
                return "INITINT";
            case INITFLOAT:
                return "INITFLOAT";
            case INITBOOL:
                return "INITBOOL";
            case INITARRAY:
                return "INITARRAY";
            case JF:
                return "JF";
            case JMP:
                return "JMP";
            case LABEL:
                return "LABEL";
            case LESS:
                return "LESS";
            case RETURN:
                return "RETURN";
            case RETURNINT:
                return "RETURNINT";
            case RETURNFLOAT:
                return "RETURNFLOAT";
            case RETURNBOOL:
                return "RETURNBOOL";
        }
        return null;        
    }
}
