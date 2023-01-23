package controllers;

import models.socket;
import views.nameServiceGUI;
import views.registerUserGUI;

import java.io.*;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class nameService extends Thread{
    private Hashtable<String, Integer> nameTable;
    private File fi;
    private Integer port;
    private socket sock;
    private nameServiceGUI GUI;

    public nameService(Integer port) throws SocketException {
        this.nameTable = new Hashtable<String, Integer>();
        readNamesFile("src/names.csv");
        this.port = port;
        this.sock=new socket(port);
    }
    public void setGUI(nameServiceGUI GUI) {
        this.GUI = GUI;
    }

    public void run(){
        while(true) {
            String res[] = sock.receiveDP().split("<sep>");
            String type = res[0];
            String name = "";
            String pin = "";
            String from = "";
            switch (type){
                case "v":
                    pin = res[1];
                    from = res[2];
                    if (isPinRegistered(Integer.parseInt(pin))){
                        try {
                            this.respondToChatService("valid",pin,from);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            this.respondToChatService("invalid",pin,from);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    pin = "";
                    from = "";
                    break;
                case "r":
                    String user[]= res[1].split(",");
                    pin = user[1];
                    try {
                        if (userExists(user[0])){
                            respondForRegister("NameExists",null,res[2]);
                        }else if(isPinRegistered(Integer.parseInt(pin))){
                            respondForRegister("PinExists",null,res[2]);
                        }else if(Integer.parseInt(pin)<8000 || 8010<Integer.parseInt(pin)){
                            respondForRegister("PinInvalid",null,res[2]);
                        }else {
                            respondForRegister("UserCreated",res[1],res[2]);
                            this.nameTable.put(user[0],Integer.parseInt(user[1]));
                            this.GUI.loadTable();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pin = "";
                    break;
                case "p":
                    name = res[1];
                    try {
                        if (!userExists(name)){
                            respondForRemember("UserDoesntExist",null,res[2]);
                        }else {
                            respondForRemember("PinRemembered",String.valueOf(getPin(name)),res[2]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    name="";
                    break;
                case "g":
                    name = res[1];
                    from = res[2];
                    try {
                        this.respondForPin(name,from);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    from = "";
                    name="";
                    break;
                default:
            }


        }
    }
    private void respondForRegister(String status, String user, String from) throws IOException {
        String msg = "r<sep>"+status+"<sep>"+user;
        int adr= Integer.parseInt(from);
        sock.sendDP(adr,msg);
    }
    private void respondForRemember(String status,  String pin, String from) throws IOException {
        String msg = "p<sep>"+status+"<sep>"+pin;
        int adr= Integer.parseInt(from);
        sock.sendDP(adr,msg);
    }
    private void respondForPin(String name, String from) throws IOException {
        String pin = String.valueOf(this.getPin(name));
        String user = name+","+pin;
        String msg = "g<sep>"+user;
        int adr= Integer.parseInt(from);
        sock.sendDP(adr,msg);
    }

    public void respondToChatService(String validation,String pin,String from) throws IOException {
        String name = this.getName(Integer.parseInt(pin));
        String user = name+","+pin;
        String msg = "v<sep>"+validation+"<sep>"+user;
        int adr= Integer.parseInt(from);
        sock.sendDP(adr,msg);
    }
    public Hashtable<String, Integer> getNameTable() {
        return nameTable;
    }


    public Integer getPin(String name){
        return this.nameTable.get(name);
    }
    public String getName(int pin){
        for (Map.Entry<String, Integer> entry : nameTable.entrySet()) {
            if (entry.getValue() == pin){
                return entry.getKey();
            }
        }
        return "";
    }
    private void readNamesFile(String path){
        fi = new File("src/names.csv");
        //Create file if the file doesnt exist
        try{
            if (!fi.exists()){
                fi.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read file and put contents in a hashmap
        try {
            Scanner myReader = new Scanner(fi);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String name = data.split(",")[0];
                Integer pin = Integer.parseInt(data.split(",")[1]);
                this.nameTable.put(name,pin);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Boolean userExists(String name){
        return this.nameTable.get(name) != null;
    }
    public Boolean isPinRegistered(Integer pin){
        return this.nameTable.containsValue(pin);
    }

    public void saveToCsv() throws IOException {
        FileWriter writer;
        writer = new FileWriter("src/names.csv", false);  //True = Append to file, false = Overwrite
        for (Map.Entry<String, Integer> entry : nameTable.entrySet()) {

            writer.write(entry.getKey());
            writer.write(",");
            writer.write(entry.getValue().toString());
            writer.write("\r\n");
        }

        System.out.println("Write success!");
        writer.close();
    }
}
