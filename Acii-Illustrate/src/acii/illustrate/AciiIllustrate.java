package acii.illustrate;

import javax.swing.*;

/**
 * AciiIllustrate is the class in this application that is run to call the main function.
 */
public class AciiIllustrate 
{

    /**
     * this method instantiates the application
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    { 
         SwingUtilities.invokeLater(() -> {
            Window.Frame();
        });
    }
    
}
