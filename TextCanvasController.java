/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package acii.illustrate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
public class TextCanvasController extends JPanel
{
    // these variables are the size of the character and
    // color arrays and used to construct the TextCanvasModel
    private static int row = 300;
    private static int col = 300;
    TextCanvasModel canvas = new TextCanvasModel(row, col);
    
    public TextCanvasController()
    {   
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // xPress and yPress stores the x and y coordinates of the mouse
                // gotten from .getX() and .getY(). Dividing these by cellSize
                // here keeps the mouse position accurate to where the character
                // is drawn because the location that the character is drawn
                // is also scaled by cellSize later.
                int xPress = e.getX() / canvas.getCellSize();
                int yPress = e.getY() / canvas.getCellSize();
                // if the x and y coordinates are greater than 0 but less than
                // the size of the text canvas,
                // the current character and color are copied to the appropriate locations
                // in the arrays.
                // repaint() is called to instantly redraw the text canvas for
                // the user.
                if (xPress >= 0 && xPress < row && yPress >= 0 && yPress < col) {
                    canvas.setCharacter(xPress, yPress);
                    canvas.setCharacterColor(xPress, yPress);
                    repaint();
                }
            }
        });
        
        // This function handles dragging the mouse while pressed and works
        // the exact same way as the previous function.
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xDrag = e.getX() / canvas.getCellSize();
                int yDrag = e.getY() / canvas.getCellSize();
                if (xDrag >= 0 && xDrag < row && yDrag >= 0 && yDrag < col) {
                    canvas.setCharacter(xDrag, yDrag);
                    canvas.setCharacterColor(xDrag, yDrag);
                    repaint();
                }
            }
        });
    }
    
    // To my understanding, paintComponent() is a built-in, under-the-hood 
    // function that redraws a component "when it needs to".
    // I think the repaint() function utilizes it for certain things.
    // Here it is overrided to redraw the character array to the text canvas
    // with .drawString() as well as apply the correct color from the color 
    // array using .setColor() These are done simultaneously in nested for loops
    // that iterate through the entire 2d arrays
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < canvas.getCharacters().length; x++) {
            for (int y = 0; y < canvas.getCharacters()[0].length; y++) {
                g.setColor(canvas.getCharacterColors()[x][y]);
                // in drawString() the second two parameters are the x and y
                // starting coordinates on the screen. Starting from x and y = 0
                // and iterating up draws the characters across the screen 
                // in a matrix-like fashion one row at a time corresponding with the gridCharacters
                // array. cellSize is used to scale these coordinates so we get
                // bigger cellSize = farther apart
                g.drawString(String.valueOf(canvas.getCharacters()[x][y]), x * canvas.getCellSize(), y * canvas.getCellSize() + 8);
            }
        }
    }
}
