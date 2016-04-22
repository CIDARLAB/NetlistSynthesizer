grammar VerilogFluigi;
@lexer::header{
    package org.cellocad.BU.fluigi;
}
@parser::header{
    package org.cellocad.BU.fluigi;
}

root
    : modDec stats 'endmodule'
    ;

modDec
    : 'module' modName '(' 'input' (input ',')+ 'output' (output | ((output ',')+ output)) ')'
    | 'module' modName '(' 'output' (output ',')+ 'input' (input | ((input ',')+ input)) ')'
    ;

stats
    : (stat)+
    ;

stat
    : assignStat ';'
    | decl ';'
    ;

decl
    : 'input' (input | ((input ',')+ input)) ';'
    | 'output' (output | ((output ',')+ output)) ';'
    | 'wire' (wire | ((wire ',')+ wire)) ';' 
    ;

assignStat
    : 'assign' exp ';'
    ;

exp
    : lhs '=' rhs
    ;

lhs
    : (output|wire)
    ;

rhs
    : (output|wire|input) (op (output|wire|input))+
    ;

op
    : '~'
    | '!'
    | '@'
    | '#'
    | '$'
    | '%'
    | '^'
    | '&'
    | '*'
    | '?'
    | '+'
    | '-'
    | '|'
    ;

modName
    : ID
    ;

input
    : ID 
    ;

output
    : ID 
    ;

wire
    : ID 
    ;

ID 
    : ('a'..'z' | 'A'..'Z'|'_')('a'..'z' | 'A'..'Z'|'0'..'9'|'_')*
    ;

WS : [ \t\r\n]+ -> skip ;