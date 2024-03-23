/*


    THE Context Free Grammar (CFG) definition for the
    CODE programming language.


    The following is a BNF grammar for the CODE programming language.

    EXPRESSION ::= TERM | TERM BINARY_OPERATOR TERM
    TERM ::= UNARY_OPERATOR TERM | VALUE | VARIABLE | FUNCTION
    BINARY_OPERATOR ::= "+" | "-" | "*" | "/" | "%" | "AND" | "OR" | "==" | ">" | ">=" | "<" | "<="
    UNARY_OPERATOR ::= "++" | "--" | "NOT"

    





 */