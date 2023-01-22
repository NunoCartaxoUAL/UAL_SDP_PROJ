package views;
import controllers.registerUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class registerUserGUI extends JFrame{
    private JPanel registerUserPanel;
    private JTextField userNameTxt;
    private JTextField pinTxt;
    private JButton registerUserTxt;
    public JLabel msgTxt;
    private JPanel panel2;
    private JButton rememberPinBtn;
    private registerUser rU;

    public registerUserGUI(registerUser rU) {
        this.rU = rU;
        setSize(500,150);
        this.setTitle("Register User");
        this.setResizable(false);
        add(registerUserPanel);
        userNameTxt.setToolTipText("Username");
        pinTxt.setToolTipText("pin");
        validate();
        Toolkit toolKit = getToolkit();
        Dimension size = toolKit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2 -200, size.height/2 - getHeight()-200);
        this.setVisible(true);
        registerUserTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regUser();
            }
        });
        rememberPinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userNameTxt.getText();
                if (name == ""){
                    msgTxt.setText("Please insert username to remember the pin to");
                }else {
                    try {
                        rU.rememberPin(name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });
    }
    private void regUser(){
        String name = userNameTxt.getText();
        String pintxt= pinTxt.getText();
        int pin=0;
        try {
            if (name !="" && pinTxt.getText() != ""){
                rU.registerNamePin(name,pintxt);
            }else if (name !=""){
                msgTxt.setText("please fill in the username");
            }else{
                msgTxt.setText("please fill in the pin");
            }
        } catch (Exception exception) {
            msgTxt.setText("the pin must consist of 4 digits");
        }
    }
}
