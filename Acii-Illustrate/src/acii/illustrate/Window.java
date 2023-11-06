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
        
        // Add the text canvas to the frame
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
            TextCanvasModel.backgroundColor = (Color) JColorChooser.showDialog(frame, "Choose a Background Color", TextCanvasModel.backgroundColor);
            drawingArea.setBackground(TextCanvasModel.backgroundColor);
            drawingArea.repaint();
        });
        
        // create save button
        // checks current saves incase the save name already exists and
        // asks the user if they want to overwrite
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener (e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter the name of your canvas:");
            if (drawingArea.isInSavesList(input)){
                int choice = JOptionPane.showConfirmDialog(frame, "The canvas already exists. Would you like to overwrite?");
                if (choice == 0){
                    drawingArea.saveCanvas(input);
                    JOptionPane.showMessageDialog(frame, "Canvas has been saved");
                }
            } else{
                drawingArea.saveCanvas(input);
                JOptionPane.showMessageDialog(frame, "Canvas has been saved");
            }
        });
        
        
        // create load button with pop up menu of more buttons
        // for selecting the canvas to load.
        JButton loadButton = new JButton("Load");
        JPopupMenu loadMenu = new JPopupMenu();
        
        // action listener for loadButton
        loadButton.addActionListener((ActionEvent e) -> {
            loadMenu.removeAll();
            updateLoadMenu(loadMenu);
            
            loadMenu.show(loadButton, 0, loadButton.getHeight());
        });
        
        // create delete button with pop up menu of more buttons
        // for selecting the canvas to delete
        JButton deleteButton = new JButton("Delete");
        JPopupMenu deleteMenu = new JPopupMenu();
        
        // action listener for deleteButton
        deleteButton.addActionListener((ActionEvent e) -> {
            deleteMenu.removeAll();
            updateDeleteMenu(deleteMenu);
            
            deleteMenu.show(deleteButton, 0, deleteButton.getHeight());
        });
        
        // this stuff creates a jpanel for the buttons
        // and then adds it to the frame. 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        buttonPanel.add(charSwitchButton);
        buttonPanel.add(colorSwitchButton); 
        buttonPanel.add(backgroundSwitchButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        
        frame.add(buttonPanel, BorderLayout.WEST);
        
        // arbitrary frame size
        frame.setSize(1000, 700); 
        frame.setVisible(true);
    }  
    
    // Function used to update the popup menu for loading a
    // saved canvas. It uses the ArrayList of saved file names
    // to create menu buttons with those names and adds them to
    // the popup menu linked to the load button
    private static void updateLoadMenu(JPopupMenu menu){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames){
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener((ActionEvent e) -> {  
                    
                    // need to figure out how to set parent component to the frame here so that
                    // the dialog box is in the middle of the window
                    JOptionPane.showMessageDialog(null, "Loading " + name.substring(0, name.length() - 4));
                    drawingArea.loadCanvas(name);
                    drawingArea.setBackground(TextCanvasModel.backgroundColor);
                });
                menu.add(menuItem);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No Saved Canvas");
        }
    }
    
    // Function used to update the delete popup menu
    // Creates menu items based on the saved file names similar
    // to updateLoadMenu
    private static void updateDeleteMenu(JPopupMenu menu){
        ArrayList<String> savedNames = drawingArea.getSavesList();
        if (!savedNames.isEmpty()){
            for (String name : savedNames) {
                JMenuItem menuItem = new JMenuItem(name.substring(0, name.length() - 4));
                menuItem.addActionListener ((ActionEvent e) -> {
                    JOptionPane.showMessageDialog(null, "Deleting " + name.substring(0, name.length() - 4));
                    drawingArea.deleteCanvas(name);
                });
                menu.add(menuItem);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No Saved Canvas");
        }
    }
}
