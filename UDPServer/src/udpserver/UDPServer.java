/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author innanplinio
 */
class UDPServer {

    public static void main(String args[]) throws Exception {
        System.out.println("-------------------Servidor-------------------");
        //Inicia DatagramSocket na porta 1995
        DatagramSocket serverSocket = new DatagramSocket(new InetSocketAddress("192.168.50.4", 1995));
        DatagramPacket receivePacket, sendPacket;
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        //ArrayList de dados do Jogador
        ArrayList<Jogador> Jogadores = new ArrayList();
        Jogador j;
        int contaJogadores = 0, contaJogadas = 0;
        System.out.println("Aguardando Clientes se conectarem...");
        Confirm thread1=null, thread2=null;
        while (contaJogadores < 2) {
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            j = new Jogador(sentence, IPAddress, port);
            Jogadores.add(j);
            j.print();
            String resposta = "\nCliente " + sentence;
            sendData = resposta.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            if (contaJogadores == 0) {
                thread1 = new Confirm(sentence, IPAddress);        
            } else if (contaJogadores == 1) {
                thread2 = new Confirm(sentence, IPAddress);    
            }
            contaJogadores++;
        }
        thread1.start();
        thread2.start();
        

        

        while (contaJogadas < 2) {
            receiveData = new byte[1024];
            sendData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            for (int i = 0; i < Jogadores.size(); i++) {
                if (Jogadores.get(i).getIp().equals(IPAddress)) {

                    Jogadores.get(i).setJogada(sentence.charAt(0) - '0');
                    System.out.println("\nJogada do cliente: " + receivePacket.getAddress().toString() + " Jogada: " + sentence);
                }
            }
            sendData = "Jogou".getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            contaJogadas++;
        }

        switch (Jogadores.get(0).getJogada()) {
            //Pedra
            case 1:

                switch (Jogadores.get(1).getJogada()) {
                    //Empate
                    case 1:
                        sendData = "Empate".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    //Derrota
                    case 2:
                        sendData = "Derrota".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendData = "Vitoria".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    //Vitoria
                    case 3:
                        sendData = "Vitoria".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendData = "Derrota".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    default:
                        sendData = "Algum erro aconteceu".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);

                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                }
                break;
            //Papel
            case 2:

                switch (Jogadores.get(1).getJogada()) {
                    //Vitoria
                    case 1:
                        sendData = "Vitoria".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendData = "Derrota".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    //Empate
                    case 2:
                        sendData = "Empate".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);

                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    //Derrota
                    case 3:
                        sendData = "Derrota".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendData = "Vitoria".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    default:
                        sendData = "Algum erro aconteceu".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);

                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                }

                break;
            //Tesoura
            case 3:

                switch (Jogadores.get(1).getJogada()) {
                    //Derrota
                    case 1:
                        sendData = "Derrota".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendData = "Vitoria".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    //Vitoria
                    case 2:
                        sendData = "Vitoria".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        sendData = "Derrota".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    //Empate
                    case 3:
                        sendData = "Empate".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);

                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                    default:
                        sendData = "Algum erro aconteceu".getBytes();
                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(0).getIp(), 2000);
                        serverSocket.send(sendPacket);

                        sendPacket = new DatagramPacket(sendData, sendData.length, Jogadores.get(1).getIp(), 2000);
                        serverSocket.send(sendPacket);
                        break;
                }

                break;
        }

    }

}

class Jogador {

    String name;
    InetAddress ip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    int jogada, port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getJogada() {
        return jogada;
    }

    public void setJogada(int jogada) {
        this.jogada = jogada;
    }

    public Jogador(String name, InetAddress ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    void print() {
        System.out.println("Novo Jogador Conectado\nNickname: " + name + "\nIP: " + ip);
    }
}

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
