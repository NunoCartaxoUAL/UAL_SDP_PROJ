package controllers;

import com.formdev.flatlaf.*;
import views.chatGUI;
import views.nameServiceGUI;
import views.registerUserGUI;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;

public class controller {
    public controller() throws IOException {

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }
        startNameService(8888);
        startRegisterUser(7777);
        startChatService(7000);
        startChatService(7001);
        startChatService(7002);


    }
    public void startNameService(int port) throws SocketException {
        nameService nS = new nameService(port);
        nameServiceGUI nsGUI = new nameServiceGUI(nS);
        nS.setGUI(nsGUI);
        nS.start();
    }
    public void startRegisterUser(int port) throws SocketException {
        registerUser rU = new registerUser(port);
        registerUserGUI rUGui = new registerUserGUI(rU);
        rU.setGUI(rUGui);
        rU.start();
    }
    public void startChatService(int port) throws SocketException {
        chatService c1 = new chatService(port);
        chatGUI cg1 = new chatGUI(c1);
        c1.setGUI(cg1);
    }
}
