package acii.illustrate;
// https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/imageio/ImageIO.html
// https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JFileChooser.html
// https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Graphics2D.html
// resourses used for the converting to png.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * This class is the controller class for the application.
 * It creates all of the mouseListeners and actionListeners and 
 * controls functions that modify information.
 */
public class TextCanvasController extends JPanel
{
    /**
     * these variables are the size of the character and
     * color arrays and used to construct the TextCanvasModel
     */ 
    private static final int ROW = 200;
    private static final int COL = 200;
    
    /**
     * a canvas of type TextCanvasModel with size ROW x COL, used as the actual drawing surface
     */
    private static TextCanvasModel canvas = new TextCanvasModel(ROW, COL);
    
    /**
     * a file manager object which is used for all file functions
     */
    private static FileManager fileManager = new FileManager();
    
    /**
     * variable to store the name of a canvas
     * used when saving and deleting in order to reset the
     * current canvas on the screen if it gets deleted
     */
    private String currentCanvas;
    
    /**
     * variable that stores the eraser size: can be small, medium, or large
     */
    private String eraserSize;
    
    /**
     * variable that toggles the eraser on user's cursor
     */
    private boolean eraser = false;
    
    /**
     * previousX and previousY stores mouse cursor locations and are
     * used to determine whether or not the mouse position has changed
     */
    private int previousX = 0;
    private int previousY = 0;
    
    /**
     * constructor for the TextCanvasController class, which creates the mouseListener and mouseMotionListener
     */
    public TextCanvasController()
    {   
        
        /**
         * This adds a mouse listener that handles single mouse clicks
         */
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
                if (xPress >= 0 && xPress < ROW && yPress >= 0 && yPress < COL) {
                    
                    // if the eraser is active, run eraser operation
                    if (eraser == true){
                        canvas.characterEraser(xPress, yPress, eraserSize);
                    
                    // else, set the appropriate character and adjust index position
                    // of the character list
                    }else{
                        canvas.setCharacter(xPress, yPress);
                        canvas.nextCharacter();
                    }
                    
                    // set the character color and call repaint() to display the
                    // information in the jpanel
                    canvas.setCharacterColor(xPress, yPress);
                    repaint();
                }
                
                // update previous mouse position variables
                previousX = xPress;
                previousY = yPress;
            }
        });
        
        /**
         * This adds a mouse listener that handles dragging the mouse. It
         * functions similarly to the single click mouse listener
         */
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xDrag = e.getX() / canvas.getCellSize();
                int yDrag = e.getY() / canvas.getCellSize();
                if (xDrag >= 0 && xDrag < ROW && yDrag >= 0 && yDrag < COL) {
                    if (eraser == true){
                        canvas.characterEraser(xDrag, yDrag, eraserSize);
                    }else{
                        
                        // if the mouse position has changed, set the appropriate
                        // character and adjust the index position of the character list
                        if (xDrag != previousX || yDrag != previousY) {
       
                            canvas.setCharacter(xDrag, yDrag);
                            canvas.nextCharacter();
                        }
                        
                    }
                    canvas.setCharacterColor(xDrag, yDrag);
                    repaint();
                }
                previousX = xDrag;
                previousY = yDrag;
            }
        });
    }
    
    // functions to add action listeners for buttons****************************
    
    /**
     * function that adds actionListener to color button
     * @param colorSwitchButton Color JButton that is created in the Button class
     * @param frame JFrame that contains all GUI components
     */
    public void addColorActionListener(JButton colorSwitchButton, JFrame frame) {
        colorSwitchButton.addActionListener(e -> {
            eraser = false;
            Color input = JColorChooser.showDialog(frame, "Choose a Color", canvas.getCurrentColor());
            if (input != null) {
                canvas.setCurrentColor(input);
            }
        });
    }
    
    /**
     * function that adds actionListener to the save as .png button
     * @param pngButton JButton that is created in the Button class and used to save canvas as .png
     * @param drawingArea TextCanvasController object that is added as a component to the main JFrame
     * @param frame JFrame that contains all GUI components
     */
public void pngActionListener(JButton pngButton, TextCanvasController drawingArea, JFrame frame) {
    pngButton.addActionListener(actionEvent -> {
        int width = getWidth();
        int height = getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Render the panel content onto the image
        paintComponent(g2d);

        // Ask the user for a file path using a JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath;

            // User selected a file path
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();

            // Check if the file name ends with ".png", if not, append it
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            // Save the image as a PNG file
            try {
                ImageIO.write(image, "png", new File(filePath));
                JOptionPane.showMessageDialog(null, "Image saved as " + filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                g2d.dispose();
            }
        }
    });
}
    
    /**
     * function that adds actionListener to character button
     * @param charSwitchButton Character JButton that is created in the Button class
     * @param frame JFrame that contains all GUI components
     */
    public void addCharActionListener(JButton charSwitchButton, JFrame frame) {
        charSwitchButton.addActionListener(e -> {
            // turn off eraser
            eraser = false;
            // get character or string choice from listener and use it to set
            // the current character list
            String input = JOptionPane.showInputDialog(frame, "Enter character(s):");
            if (input != null && !input.isEmpty()) {
                canvas.setCurrentCharList(input.toCharArray());
            }
        });
    }
    
    /**
     * function that adds actionListener to Eraser menu items
     * @param eraserSmall menu item to toggle small eraser on cursor
     * @param eraserMedium menu item to toggle medium eraser on cursor
     * @param eraserLarge menu item to toggle large eraser on cursor
     */
    public void addEraserItemActionListener(JMenuItem eraserSmall, JMenuItem eraserMedium, JMenuItem eraserLarge){
        
        // turns on and sets size of eraser and sets current character to an
        // empty space
        eraserSmall.addActionListener (e -> {
           eraser = true;
           eraserSize = "Small";
           canvas.setCurrentCharacter(' ');
        });
        eraserMedium.addActionListener (e -> {
           eraser = true;
           eraserSize = "Medium";
           canvas.setCurrentCharacter(' ');
        });
        eraserLarge.addActionListener (e -> {
           eraser = true;
           eraserSize = "Large";
           canvas.setCurrentCharacter(' ');
        });
    }
    
    /**
     * function that adds actionListener to Eraser button. 
     * @param menu menu that contains eraser size options
     * @param eraserButton Eraser JButton that is created in the Button class
     */
    public void addEraserButtonActionListener(JPopupMenu menu, JButton eraserButton){
        eraserButton.addActionListener( e -> {
            menu.show(eraserButton, 10, eraserButton.getHeight());
        });
    }
    
    /**
     * function that adds actionListener to background button
     * @param backgroundSwitchButton Background JButton that is created in the Button class
     * @param frame JFrame that contains all GUI components
     * @param drawingArea the area that users are able to draw characters on
     */
    public void addBackgroundActionListener(JButton backgroundSwitchButton, JFrame frame, TextCanvasController drawingArea) {
        backgroundSwitchButton.addActionListener(e -> {
            eraser = false;
            Color input = JColorChooser.showDialog(frame, "Choose a Color", canvas.getBackgroundColor());
            if (input != null) {
                canvas.setBackgroundColor(input);
            }
            updateBackground();
        });
    }
    
    /**
     * function that adds actionListener to the Save menu option
     * @param saveItem menu item that allows the user to save their drawing
     * @param frame JFrame that contains all GUI components
     * @param drawingArea the area that users are able to draw characters on
     */
    public void addSaveMenuItemActionListener(JMenuItem saveItem, JFrame frame, TextCanvasController drawingArea) {
        saveItem.addActionListener (e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter the name of your canvas:");
            
            // if input is not null...
            if (input != null){
                
                // if input name is already in saves list...
                if (drawingArea.isInSavesList(input)){
                    
                    // choose whether to overwrite save or cancel
                    int choice = JOptionPane.showConfirmDialog(frame, "The canvas already exists. Would you like to overwrite?");
                    
                    // if choosing to overwrite, save canvas normally
                    if (choice == 0){
                    drawingArea.saveCanvas(input);
                    JOptionPane.showMessageDialog(frame, "Canvas has been saved");
                    setCurrentCanvas(input + ".txt"); 
                    }
                
                // if input name is not in saves list, save normally
                }else {
                drawingArea.saveCanvas(input);
                JOptionPane.showMessageDialog(frame, "Canvas has been saved");
                setCurrentCanvas(input + ".txt");
                }
            }
        });
    }
    
    /**
     * function that adds actionListener to the Save button
     * @param saveButton save JButton that is created in the Button class
     * @param saveMenu menu that allows users to choose to overwrite a previous save or create new save
     * @param frame JFrame that contains all GUI components
     * @param drawingArea the area that users are able to draw characters on
     */
    public void addSaveButtonActionListener(JButton saveButton, JPopupMenu saveMenu, TextCanvasController drawingArea, JFrame frame) {
        saveButton.addActionListener( e -> {
            saveMenu.removeAll();
            Button.updateSaveMenu(saveMenu, drawingArea, frame);
            
            saveMenu.show(saveButton, 10, saveButton.getHeight());
        });
    }
    
    /**
     * function that adds actionListener to the Load button
     * @param loadButton Load JButton that is created in the Button class
     * @param loadMenu menu that allows users to choose a saved file to load to the drawing area
     * @param frame JFrame that contains all GUI components
     * @param drawingArea the area that users are able to draw characters on
     */
    public void addLoadActionListener(JButton loadButton, JPopupMenu loadMenu, TextCanvasController drawingArea, JFrame frame) {
        loadButton.addActionListener( e -> {
            loadMenu.removeAll();
            Button.updateLoadMenu(loadMenu, drawingArea, frame);
            
            loadMenu.show(loadButton, 10, loadButton.getHeight());
        });
    }
    
    /**
     * function that adds actionListener to the Delete button
     * @param deleteButton Delete JButton that is created in the Button class
     * @param deleteMenu menu that allows users to choose a saved file to delete
     * @param frame JFrame that contains all GUI components
     * @param drawingArea the area that users are able to draw characters on
     */
    public void addDeleteActionListener(JButton deleteButton, JPopupMenu deleteMenu, TextCanvasController drawingArea, JFrame frame) {
        deleteButton.addActionListener( e -> {
            deleteMenu.removeAll();
            Button.updateDeleteMenu(deleteMenu, drawingArea, frame);
            
            deleteMenu.show(deleteButton, 10, deleteButton.getHeight());
        });
    }
    
    /**
     * function that adds actionListener to the Reset button
     * @param resetButton Reset JButton that is created in the Button class
     * @param frame JFrame that contains all GUI components
     * @param drawingArea the area that users are able to draw characters on
     */
    public void addResetActionListener(JButton resetButton, TextCanvasController drawingArea, JFrame frame){
        resetButton.addActionListener(e -> {
            eraser = false;
            canvas.resetTextCanvas(ROW, COL);
            setBackground(Color.white);
            repaint();
        });
    }
    
    /**
     * function to access saveFile() from FileManager
     * @param fileName name of the file to be accessed
     */
    public void saveCanvas(String fileName){
        fileManager.saveFile(fileName, canvas.getCharacters(), canvas.getCharacterColors(), canvas.getBackgroundColor());    
    }
    
    // Getters******************************************************************
    
    /**
     * function to get the saves list from fileManager
     * @return the list of saved files
     */
    public ArrayList<String> getSavesList(){
        fileManager.checkSaveFolder();
        return fileManager.getSaves();
    }
    
    /**
     * function to get the most recently opened canvas
     * @return the most recently opened canvas
     */
    public String getCurrentCanvas(){
        return currentCanvas;
    }
    
    /**
     * function to get the background color from TextCanvasModel
     * @return the background color from TextCanvasModel
     */
    public Color getBackgroundColor(){
        return canvas.getBackgroundColor();
    }
    
    /**
     * Check if the saves list contains a certain file name
     * @param name the file name to be checked for in the saves list
     * @return the truth value of if the specific file is in the saves list
     */
    public boolean isInSavesList(String name){
        String filename = name + ".txt";
        fileManager.checkSaveFolder();
        return fileManager.isInSaves(filename);
    }
    
    // Setters******************************************************************
    
    /**
     * function to set the most recently loaded save
     * @param name the current canvas
     */
    public void setCurrentCanvas(String name){
        currentCanvas = name;
    }
    
    /**
     * function to reset the text canvas using a TextCanvasController object
     */
    public void resetTextCanvas(){
        canvas.resetTextCanvas(ROW, COL);
    }
    
    /**
     * function to set the background color
     * @param color the background color to be set
     */
    public void setBackgroundColor(Color color){
        canvas.setBackgroundColor(color);
    }
    
    /**
     * function to update the background color
     */
    public void updateBackground(){
        setBackground(canvas.getBackgroundColor());
        repaint();
    }
    
    // Additional functions*****************************************************

    /**
     * function to load a file using a TextCanvasController object
     * <p>gets a copy of the required arrays from fileManager
     * and updates canvas appropriately</p>
     * @param fileName  the file name of the file to be loaded to the drawing area
     */
    public void loadCanvas(String fileName){
        fileManager.checkSaveFolder();
        fileManager.loadFile(fileName);
        canvas.loadCanvas(fileManager.getNewCharacterGrid(), fileManager.getNewColorGrid(), fileManager.getNewBackground());
        repaint();
    }
    
    /**
     * Function to delete a file using a TextCanvasController object
     * @param fileName name of file to be deleted from saves list
     */
    public void deleteCanvas(String fileName){
        fileManager.deleteFile(fileName);
    }
  
    /**
     * To my understanding, paintComponent() is a built-in, under-the-hood 
     * function that redraws a component "when it needs to".
     * I think the repaint() function utilizes it for certain things.
     * Here it is overrided to redraw the character array to the text canvas
     * with .drawString() as well as apply the correct color from the color 
     * array using .setColor() These are done simultaneously in nested for loops
     * that iterate through the entire 2d arrays
     * 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < canvas.getCharacters().length; x++) {
            for (int y = 0; y < canvas.getCharacters()[0].length; y++) {
                g.setColor(canvas.getCharacterColors()[x][y]);
                // in drawString() the second two parameters are the x and y
                // starting coordinates on the screen. Starting from x and y = 0
                // and iterating up draws the characters across the screen 
                // in a matrix-like fashion one ROW at a time corresponding with the gridCharacters
                // array. cellSize is used to scale these coordinates so we get
                // bigger cellSize = farther apart
                g.drawString(String.valueOf(canvas.getCharacters()[x][y]), x * canvas.getCellSize(), y * canvas.getCellSize() + 8);
            }
        }
    }
}
