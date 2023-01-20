package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chatGUI extends JFrame{
    private JButton button1;
    private JTextArea chatArea;
    private JPanel chatPanel;
    private JPanel loginPanel;
    private JPanel loggedPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton msgSend;
    private JTextField msgArea;
    private JTextField toArea;

    public chatGUI(){
        setSize(300,500);
        this.setTitle("Chat Service");
        this.setResizable(false);
        loggedPanel.setVisible(false);
        add(chatPanel);
        Toolkit toolKit = getToolkit();
        Dimension size = toolKit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2-200, size.height/2 - getHeight()/2+40);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatArea.append("test\n");
            }
        });
        validate();
        this.setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loggedPanel.setVisible(true);
                loginPanel.setVisible(false);
            }
        });
    }
}
