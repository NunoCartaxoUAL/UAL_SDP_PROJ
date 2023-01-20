package views;
import models.registerUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Map;

public class registerUserGUI extends JFrame{
    private JPanel registerUserPanel;
    private JTextField userNameTxt;
    private JTextField pinTxt;
    private JButton registerUserTxt;
    private JLabel msgTxt;
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
                    try{
                        int pin = rU.getPin(name);
                        msgTxt.setText("The pin to the user "+name+" is "+pin);
                    }catch (Exception exception){
                        msgTxt.setText("This name is not registered in the name Service");
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
            pin = Integer.parseInt(pintxt);
            if (name !="" && pinTxt.getText() != ""){
                String response = rU.createUser(name,pin);
                msgTxt.setText(response);
                if(response.contains("Created")){
                    userNameTxt.setText("");
                    pinTxt.setText("");
                }
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
