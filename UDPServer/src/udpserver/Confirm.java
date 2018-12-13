/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author innanplinio
 */
class Confirm extends Thread {

    DatagramSocket clientSocket;
    InetAddress IPAddress;
    String name;
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];

    Confirm(String name, InetAddress ip) throws SocketException {
        this.IPAddress = ip;
        this.name = name;
    }

    @Override
    public void run() {
        super.run();          
        try {
            clientSocket = new DatagramSocket(3000);
            sendData = name.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3000);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            while (true) {

                try {
                    clientSocket.setSoTimeout(1000);
                    clientSocket.receive(receivePacket);
                    String modifiedSentence = new String(receivePacket.getData());
                    if (modifiedSentence.equals(name)) {
                        System.out.println("Cliente " + name + " está ativo!");
                    }
                } catch (SocketTimeoutException ex) {
                    System.out.println("Cliente " + name + " perdeu conexão!");
                    clientSocket.send(sendPacket);
                    clientSocket.setSoTimeout(1000);
                }
            }
        } catch (SocketException ex) {
            clientSocket.close();
            Logger.getLogger(Confirm.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Algum erro ocorreu com o cliente " + name);
        } catch (IOException ex) {
            clientSocket.close();
            Logger.getLogger(Confirm.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Algum erro ocorreu com o cliente " + name);
        }

    }
}
