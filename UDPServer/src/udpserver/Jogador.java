/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.net.InetAddress;

/**
 *
 * @author innanplinio
 */
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
