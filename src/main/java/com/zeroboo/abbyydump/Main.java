/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeroboo.abbyydump;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author pthung
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Usage: ABBYYTextDump <InputABBYYFile> <OutputFolder>");
        if (args.length >= 2) {

            String inputABBYYFile = args[0];
            String outputFolder = args[1];

            System.out.println("Input: " + inputABBYYFile);
            System.out.println("Output: " + outputFolder);
            ABBYYTextDump printer = new ABBYYTextDump(inputABBYYFile, outputFolder);
            try {
                printer.print();
            } catch (IOException | JAXBException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
