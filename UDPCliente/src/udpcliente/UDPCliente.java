/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpcliente;

import java.io.*;
import java.net.*;

/**
 *
 * @author innanplinio
 */

class UDPCliente
{
   public static void main(String args[]) throws Exception
   {
      System.out.println ("-------------------Cliente-------------------");
      System.out.print("Digite Nickname desejado: ");
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String sentence = inFromUser.readLine();
      sendData = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1995);
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      boolean a = true;
      while(a){
          
                try{
                    clientSocket.setSoTimeout(1000);
                    clientSocket.receive(receivePacket);
                    a=false;
                }catch(SocketTimeoutException ex){
                    System.out.println("timeout");
                    clientSocket.send(sendPacket);
                    clientSocket.setSoTimeout(1000);
                }
      }
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
   }
   
  
}
