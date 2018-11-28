/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author innanplinio
 */
class UDPServer{
    public static void main(String args[]) throws Exception{
        System.out.println ("-------------------Servidor-------------------");
         DatagramSocket serverSocket = new DatagramSocket(1995);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            ArrayList<Jogador> Jogadores = new ArrayList();
            
            
            while(true){
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String( receivePacket.getData());
                  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();
                  Jogador j = new Jogador(sentence, IPAddress);
                  Jogadores.add(j);
                  j.print();
                  sendData = "Pacote Recebido".getBytes();
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  serverSocket.send(sendPacket);
               }
     
      }

}

class Jogador{
    String name;
    InetAddress ip;

    public Jogador(String name, InetAddress ip) {
        this.name = name;
        this.ip = ip;
    }
    void print(){
        System.out.println("Nickname: " + name + "\nIP: " + ip);
    }
}

