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
public class Button 
{
    JPanel buttonPanel = new JPanel();
    public JPanel GetPanel()
    {
        return buttonPanel;
    }
     public void Button()
     {
     buttonPanel.setLayout(new GridLayout(3, 1)); 
     JButton colorSwitchButton = new JButton("Color");
     JButton charSwitchButton = new JButton("Char");
     JButton backgroundSwitchButton = new JButton("Background");
     buttonPanel.add(colorSwitchButton);
     buttonPanel.add(charSwitchButton);
     buttonPanel.add(backgroundSwitchButton);
     }
}
