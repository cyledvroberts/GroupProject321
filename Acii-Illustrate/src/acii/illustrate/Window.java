/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Window 
{
    // create a new text canvas object as a class member so that
    // it can be used in class methods
    private static TextCanvasController drawingArea = new TextCanvasController();
    
    public static void Frame()
    {
        // creates the window
        JFrame frame = new JFrame("ASCII-Illustrate");
        // makes sure clicking the 'x' on the window closes the app
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add the text canvas to the frame and set initial background color
        frame.add(drawingArea, BorderLayout.CENTER);
        drawingArea.updateBackground();
        
        JPanel buttonPanel = Button.createButtons(frame, drawingArea);
        
        frame.add(buttonPanel, BorderLayout.WEST);
        
        // arbitrary frame size
        frame.setSize(1000, 700); 
        frame.setVisible(true);
    }  
}
