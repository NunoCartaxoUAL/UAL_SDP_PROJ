package models;

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
