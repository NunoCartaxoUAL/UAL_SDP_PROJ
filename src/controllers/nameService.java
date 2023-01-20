package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class nameService {
    private Hashtable<String, Integer> nameTable;
    private File fi;
    public nameService() {
        this.nameTable = new Hashtable<String, Integer>();
        readNamesFile("src/names.csv");
    }

    public Hashtable<String, Integer> getNameTable() {
        return nameTable;
    }

    public String createUser(String name , Integer pin){
        if (userExists(name)){
            System.out.println("This name Already exists.");
            return "This name Already exists.";
        }else if (isPinRegistered(pin)){
            System.out.println("This pin is Already registered");
            return "This pin is Already registered";
        }else if(8000>pin || pin>8010){
            System.out.println("pin invalid, please choose between 8000-8010");
            return "pin invalid, please choose between 8000-8010";
        }else{
            System.out.println("User Created : "+name+" "+ pin);
            this.nameTable.put(name,pin);
            return "User Created : "+name+" "+ pin;
        }
    }


    public Integer getPin(String name){
        return this.nameTable.get("name");
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
                this.createUser(name,pin);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private Boolean userExists(String name){
        return this.nameTable.get(name) != null;
    }
    private Boolean isPinRegistered(Integer pin){
        return this.nameTable.containsValue(pin);
    }
}
