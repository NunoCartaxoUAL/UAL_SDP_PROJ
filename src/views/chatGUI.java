package views;

import controllers.chatService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;

public class chatGUI extends JFrame{
    private JButton validateBTN;
    public JTextArea chatArea;
    private JPanel chatPanel;
    public JPanel loginPanel;
    public JPanel loggedPanel;
    private JTextField pinTextField;
    private JButton msgSend;
    private JTextField msgArea;
    private JTextField toArea;
    public JLabel validationLabel;
    private JLabel pinLabel;
    private chatService cS;
    public String userName;
    public String messageToSend; //temporarily stores the msg to send so i can clear the text field and not send "" for the next names on the list

    public chatGUI(chatService cS){
        this.cS = cS;
        cS.start();
        setSize(300,500);
        this.setTitle("Chat Service");
        this.setResizable(false);
        loggedPanel.setVisible(false);
        add(chatPanel);
        Toolkit toolKit = getToolkit();
        Dimension size = toolKit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2-200, size.height/2 - getHeight()/2+40);
        setupBtns();
        validate();
        this.setVisible(true);
    }
    public void setupBtns(){
        validateBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pin = pinTextField.getText();
                try {
                    Integer.parseInt(pin);
                    cS.validatePin(pin);
                }catch (Exception exception){
                    validationLabel.setText("Please insert a number in the pin text field");
                }
            }
        });
    }
    public void addMsg(String msg){
        chatArea.append(msg);
    }
    public void newChatService(Integer port) throws SocketException {
        this.cS = new chatService(port);
        this.cS.setGUI(this);
        this.cS.setUsername(this.userName);
        synchronized (this.cS){
            this.cS.start();

        }
        msgSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageToSend = msgArea.getText();
                if (!messageToSend.equals("")){
                    msgArea.setText("");
                    String recievers[] = toArea.getText().split(",");
                    for (String reciever: recievers) {
                        if (!reciever.equals("")){
                            try {
                                cS.getPin(reciever);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    toArea.setText("");
                }
            }
        });
    }
}
