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
    : 'module' modName '(' 'input' (input ',')+ 'output' (output | ((output ',')+ output)) ')' ';'
    | 'module' modName '(' 'output' (output ',')+ 'input' (input | ((input ',')+ input)) ')' ';'
    | 'module' modName '(' (('finput' (finput ',')*)|('foutput' (foutput ',')*)|('cinput' (cinput ',')*)|('coutput' (coutput ',')*))* (('finput' (finput ',')* finput)|('foutput' (foutput ',')* foutput)|('cinput' (cinput ',')* cinput)|('coutput' (coutput ',')* coutput)) ')' ';'
    ;

stats
    : (stat)+
    ;

stat
    : assignStat
    | decl
    ;

decl
    : 'input' (input | ((input ',')+ input)) ';'
    | 'finput' (finput | ((finput ',')+ finput)) ';'
    | 'cinput' (cinput | ((cinput ',')+ cinput)) ';'
    | 'output' (output | ((output ',')+ output)) ';'
    | 'coutput' (coutput | ((coutput ',')+ coutput)) ';'
    | 'foutput' (foutput | ((foutput ',')+ foutput)) ';'
    | 'wire' (wire | ((wire ',')+ wire)) ';'
    | 'cchannel' (cchannel | ((cchannel ',')+ cchannel)) ';'
    | 'fchannel' (fchannel | ((fchannel ',')+ fchannel)) ';'
    ;

assignStat
    : 'assign' exp ';'
    ;

exp
    : lhs '=' rhs
    ;

lhs
    : (var)
    ;

rhs
    : (var) (op (var))+
    | (bufferVar)
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
    | '/'
    ;

bufferVar
    : var
    ;

var
    : ID
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

finput
    : ID
    ;

cinput 
    : ID
    ;

foutput 
    : ID
    ;

coutput
    : ID
    ;


wire
    : ID 
    ;

cchannel
    : ID
    ;

fchannel
    : ID
    ;

ID 
    : ('a'..'z' | 'A'..'Z'|'_')('a'..'z' | 'A'..'Z'|'0'..'9'|'_')*
    ;

WS : [ \t\r\n]+ -> skip ;