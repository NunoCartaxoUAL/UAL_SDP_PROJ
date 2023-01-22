package controllers;

import com.formdev.flatlaf.*;
import views.chatGUI;
import views.nameServiceGUI;
import views.registerUserGUI;

import javax.swing.*;
import java.io.IOException;

public class controller {
    public controller() throws IOException {

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }
        nameService nS = new nameService(8888);
        nameServiceGUI nsGUI = new nameServiceGUI(nS);
        nS.start();

        registerUser rU = new registerUser(7777); //TODO retirar o nS e por portas
        registerUserGUI rUGui = new registerUserGUI(rU);
        rU.setGUI(rUGui);
        rU.start();

        chatService c1 = new chatService(7000);//TODO retirar o nS e por portas
        chatService c2 = new chatService(7001);
        chatService c3 = new chatService(7002);
        chatGUI cg1 = new chatGUI(c1);
        c1.setGUI(cg1);
        chatGUI cg2 = new chatGUI(c2);
        c2.setGUI(cg2);
        chatGUI cg3 = new chatGUI(c3);
        c3.setGUI(cg3);


    }
}
