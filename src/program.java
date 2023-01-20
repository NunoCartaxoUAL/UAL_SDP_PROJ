import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import controllers.nameService;
import views.GUI;
import views.test;

import javax.swing.*;


public class program {
    public static void main(String[] args) {
        nameService nS = new nameService();
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }
        GUI gui = new GUI(nS);
    }
}