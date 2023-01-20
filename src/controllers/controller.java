package controllers;

import com.formdev.flatlaf.*;
import models.nameService;
import models.registerUser;
import views.chatGUI;
import views.nameServiceGUI;
import views.registerUserGUI;

import javax.swing.*;

public class controller {
    public controller(){
        nameService nS = new nameService();
        registerUser rU = new registerUser(nS);
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }
        nameServiceGUI nsGUI = new nameServiceGUI(nS);
        registerUserGUI registerUserGui = new registerUserGUI(rU);
        chatGUI chat1 = new chatGUI();
        chatGUI chat2 = new chatGUI();
        //chatGUI chat3 = new chatGUI();
    }
}
