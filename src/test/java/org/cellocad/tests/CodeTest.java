/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.tests;

import org.cellocad.BU.netsynth.Utilities;

/**
 *
 * @author prash
 */
public class CodeTest {
    
    public static void main(String[] args) {
        String filepath = Utilities.getFilepath();
        System.out.println("File path ::" +filepath);
        String directoryName = "/results";
        String resultsDirectory = filepath + directoryName;
        System.out.println(Utilities.validFilepath(resultsDirectory));
        
        if(Utilities.makeDirectory(filepath + directoryName)){
            System.out.println("Directory created..");
        }
    }
    
}
