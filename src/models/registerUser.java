package models;

import models.nameService;

public class registerUser {
    public nameService ns;
    public registerUser(nameService ns){
        this.ns = ns;
    }
    public String createUser(String name , Integer pin){
        if (ns.userExists(name)){
            System.out.println("This name Already exists.");
            return "This name Already exists.";
        }else if (ns.isPinRegistered(pin)){
            System.out.println("This pin is Already registered");
            return "This pin is Already registered";
        }else if(8000>pin || pin>8010){
            System.out.println("pin invalid, please choose between 8000-8010");
            return "pin invalid, please choose between 8000-8010";
        }else{
            System.out.println("User Created : "+name+" "+ pin);
            ns.getNameTable().put(name,pin);
            return "User Created : "+name+" "+ pin;
        }
    }


    public Integer getPin(String name){

        return ns.getNameTable().get(name);
    }
}