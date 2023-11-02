/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Window 
{
   
    public static void Frame()
    {
        // creates the window
        JFrame frame = new JFrame("ASCII-Illustrate");
        // makes sure clicking the 'x' on the window closes the app
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // creates the text canvas and adds it to the frame
        TextCanvasController drawingArea = new TextCanvasController();
        frame.add(drawingArea, BorderLayout.CENTER);

        // creates a button for changing the text colors using an action listener.
        // JColorChooser.showDialog() brings up a built-in window that lets the user 
        // select a color. This is stored in the currentColor variable
        JButton colorSwitchButton = new JButton("Color");
        colorSwitchButton.addActionListener(e -> {
            TextCanvasModel.currentColor = JColorChooser.showDialog(frame, "Choose a Color", TextCanvasModel.currentColor);
        });

        // creates a button for changing the character using an action listener.
        // JOptionPane.showInputDialog() brings up a built-in window that lets the 
        // user enter a character. right now it allows any string input but only
        // the first character is stored in the currentCharacter variable
        JButton charSwitchButton = new JButton("Char");
        charSwitchButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter a character:");
            if (input != null && !input.isEmpty()) {
                TextCanvasModel.currentCharacter = input.charAt(0);
            }
        });
        
        // creates a button for changing the background color of the text canvas object.
        // Uses JColorChooser.showDialog() like before to store it in the backgroundColor variable.
        // setBackground() internally changes the background color of the text canvas object
        // and repaint() redraws it so it changes for the user.
        JButton backgroundSwitchButton = new JButton("Background");
        backgroundSwitchButton.addActionListener(e -> {
            TextCanvasModel.backgroundColor = JColorChooser.showDialog(frame, "Choose a Background Color", TextCanvasModel.backgroundColor);
            drawingArea.setBackground(TextCanvasModel.backgroundColor);
            drawingArea.repaint();
        });
        
        // this stuff creates another jpanel for the buttons
        // and then adds it to the frame. 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1)); 
        buttonPanel.add(colorSwitchButton);
        buttonPanel.add(charSwitchButton);
        buttonPanel.add(backgroundSwitchButton);

        frame.add(buttonPanel, BorderLayout.WEST);
        
        // arbitrary frame size
        frame.setSize(1000, 700); 
        frame.setVisible(true);
    }
}
