package controllers;

import models.socket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class nameService extends Thread{
    private Hashtable<String, Integer> nameTable;
    private File fi;
    private Integer port;
    private socket sock;
    public nameService(Integer port) throws SocketException {
        this.nameTable = new Hashtable<String, Integer>();
        readNamesFile("src/names.csv");
        this.port = port;
        this.sock=new socket(port);
    }

    public void run(){
        while(true) {
            String res[] = sock.receiveDP().split("<sep>");
            String type = res[0];
            switch (type){
                case "v":
                    String pin = res[1];
                    String from = res[2];
                    try {
                        this.respondToChatService("valid",pin,from);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "r":
                    String user[]= res[1].split(",");
                    Integer pin1 = Integer.parseInt(user[1]);
                    try {
                        if (userExists(user[0])){
                            respondForRegister("NameExists",null,res[2]);
                        }else if(isPinRegistered(pin1)){
                            respondForRegister("PinExists",null,res[2]);
                        }else if(pin1<8000 || 8010<pin1){
                            respondForRegister("PinInvalid",null,res[2]);
                        }else {
                            respondForRegister("UserCreated",res[1],res[2]);
                            this.nameTable.put(user[0],Integer.parseInt(user[1]));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "p":
                    String name1 = res[1];
                    try {
                        if (!userExists(name1)){
                            respondForRemember("UserDoesntExist",null,res[2]);
                        }else {
                            respondForRemember("PinRemembered",String.valueOf(getPin(name1)),res[2]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "g":
                    String name = res[1];
                    String from2 = res[2];
                    try {
                        this.respondForPin(name,from2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("never works");
            }
            System.out.println(res[0]);
            System.out.println(res[1]);
            System.out.println(res[2]);

        }
    }
    private void respondForRegister(String status, String user, String from) throws IOException {
        String msg = "r<sep>"+status+"<sep>"+user;
        int adr= Integer.parseInt(from);
        sock.sendDP(adr,msg);
    }
    private void respondForRemember(String status,  String pin, String from) throws IOException {
        String msg = "p<sep>"+pin;
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
}
