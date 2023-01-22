package models;

import java.io.*;
import java.net.*;
import java.awt.*;
public class socket{
    InetAddress ER,IPr;
    DatagramSocket DS;
    int port;
    byte bp[]=new byte[1024];
    public socket(int port) throws SocketException {
        this.port = port;
        DS=new DatagramSocket(port);
    }
    public String receiveDP(){
        try{
            DatagramPacket DP=new DatagramPacket(bp,1024);
            DS.receive(DP);
            IPr=DP.getAddress();
            byte Payload[]=DP.getData();
            int len=DP.getLength();
            String res=new String(Payload,0,0,len);
            String tmp=IPr.toString();
            String temp=tmp.substring(1);
            return res;
        }catch(IOException e){
            return "failed recieving DP - socket";
        }
    }
    public void sendDP(int Pr,String msg) throws IOException {
        int len=msg.length();
        byte b[]=new byte[len];
        msg.getBytes(0,len,b,0);
        ER=InetAddress.getByName("127.0.0.1");
        DatagramPacket DP=new DatagramPacket(b,len,ER,Pr);
        DS.send(DP);
    }
}
