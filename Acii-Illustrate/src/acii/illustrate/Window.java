package acii.illustrate;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to create and display the window
 * 
 */
public class Window 
{
    // create a new text canvas object as a class member so that
    // it can be used in class methods
    private static TextCanvasController drawingArea = new TextCanvasController();
    
    /**
     * This method creates and displays a JFrame and adds all of the GUI components
     */
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
        
        frame.add(buttonPanel, BorderLayout.NORTH);
        
        // arbitrary frame size
        frame.setSize(1000, 700); 
        frame.setVisible(true);
    }  
}
