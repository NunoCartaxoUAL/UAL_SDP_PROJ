package controllers;

import models.socket;
import views.chatGUI;

import java.io.*;
import java.net.SocketException;

public class chatService extends Thread{
    private socket sock;
    private chatGUI GUI;
    private int port;
    private String username;
    public chatService(int port) throws SocketException {
        this.port = port;
        this.sock = new socket(port);
        //this.StartSocket();
    }

    public void setGUI(chatGUI GUI) {
        this.GUI = GUI;
    }

    public void run(){
        while(true) {
            String res[] = sock.receiveDP().split("<sep>");
            String type = res[0];
            switch (type){
                case "c":
                    String msg = res[1];
                    this.GUI.addMsg(msg);
                    break;
                case "v":
                    String valid = res[1];
                    if(valid.equals("valid")){
                        this.pinIsValid(res[2]);
                    }else {
                        this.GUI.validationLabel.setText("Pin validation failed");
                    }
                    break;
                case "g":
                    String text = this.GUI.messageToSend;
                    String info[] = res[1].split(",");
                    int port = Integer.parseInt(info[1]);
                    String name = info[0];
                    try {
                        this.sendMessage(this.username+" -> "+ name+" :"+text+"\n",port);
                        this.GUI.chatArea.append(this.username +" -> "+name+" :"+text+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }
    }

    private void pinIsValid(String info) {
        String user[] = info.split(",");
        String name = user[0] ;
        //create new chat
        int pin = Integer.parseInt(user[1]);
        this.GUI.setTitle(name);
        this.GUI.userName = name;
        this.GUI.loggedPanel.setVisible(true);
        this.GUI.loginPanel.setVisible(false);
        try {
            this.GUI.newChatService(pin);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.sock = null;
        this.stop();
    }

    public void sendMessage(String text , Integer addr) throws IOException {
        String msg="c<sep>"+text;
        int adr= addr;
        sock.sendDP(adr,msg);
    }
    public void validatePin(String pin) throws IOException {
        String from= String.valueOf(this.port);
        String msg="v<sep>"+pin+"<sep>"+from;
        int adr= 8888;
        sock.sendDP(adr,msg);
    }

    public void getPin(String reciever) throws IOException {
        String from= String.valueOf(this.port);
        String msg="g<sep>"+reciever+"<sep>"+from;
        int adr= 8888;
        sock.sendDP(adr,msg);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
