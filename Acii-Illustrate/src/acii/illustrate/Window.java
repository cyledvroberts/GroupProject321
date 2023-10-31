/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;

/**
 *
 * @author Cyle
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Window 
{
   
    public static void Frame()
    {
     JFrame frame = new JFrame("ASCII-Illustrate");
        // makes sure clicking the 'x' on the window closes the app
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700); 
        frame.setVisible(true);
        TextCanvasView View = new TextCanvasView();
        frame.add(View.Textmodel,  BorderLayout.CENTER);
        View.Textmodel.setBackground(View.Textmodel.GetColor());
        Button Buttons = new Button();
        Buttons.Button();
        frame.add(Buttons.GetPanel(), BorderLayout.WEST);
        
    }
}
