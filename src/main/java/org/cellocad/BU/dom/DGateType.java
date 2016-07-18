/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.BU.dom;

/**
 *
 * @author prashantvaidyanathan
 */
public enum DGateType {

    //Digital Gates
    NOT,
    BUF,
    NOR,
    NAND,
    AND,
    OR,
    OUTPUT_OR,
    XOR,
    XNOR,
    
    //uF Gates
    uF,
    uF_IN,
    uF_OUT,
    
    //I/O Gate
    INPUT,
    OUTPUT;
}
