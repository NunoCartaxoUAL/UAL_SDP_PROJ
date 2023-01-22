package controllers;

import controllers.nameService;
import models.socket;
import views.registerUserGUI;

import java.io.IOException;
import java.net.SocketException;
import java.util.Hashtable;

public class registerUser extends Thread{
    private Integer port;
    private socket sock;
    public registerUserGUI GUI;
    public registerUser(Integer port) throws SocketException {
        this.port = port;
        this.sock=new socket(port);
    }

    public void setGUI(registerUserGUI GUI) {
        this.GUI = GUI;
    }

    public void run(){
        while(true) {
            String res[] = sock.receiveDP().split("<sep>");
            System.out.println(res[0]);
            System.out.println(res[1]);
            String type = res[0];
            switch (type){
                case "r":
                    String msg = res[1];
                    if (msg.matches("NameExists")){
                        this.GUI.msgTxt.setText("This name Already exists.");
                    }else if (msg.matches("PinExists")){
                        this.GUI.msgTxt.setText("This pin is Already registered");
                    }else if(msg.matches("PinInvalid")){
                        this.GUI.msgTxt.setText("pin invalid, please choose between 8000-8010");
                    }else if(msg.matches("UserCreated")){
                        String user1[] = res[2].split(",");
                        this.GUI.msgTxt.setText("User Created : "+user1[0]+" "+ user1[1]);
                    }
                    break;
                case "p":
                    String msg1 = res[1];
                    if (msg1.matches("UserDoesntExist")){
                        this.GUI.msgTxt.setText("User Doesnt Exist");
                    }else if (msg1.matches("PinRemembered")){
                        String pin = res[2];
                        this.GUI.msgTxt.setText("The pin for this user is : "+pin);
                    }
                    break;
                default:
            }
        }
    }

    public void rememberPin(String name) throws IOException {
        String from= String.valueOf(this.port);
        String msg="p<sep>"+name+"<sep>"+from;
        int adr= 8888;
        sock.sendDP(adr,msg);
    }
    public void registerNamePin(String name,String pin) throws IOException {
        String from= String.valueOf(this.port);
        String msg="r<sep>"+name+","+pin+"<sep>"+from;
        int adr= 8888;
        sock.sendDP(adr,msg);
    }
}