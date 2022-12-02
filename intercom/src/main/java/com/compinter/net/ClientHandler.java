package com.compinter.net;

import java.io.*;
import java.net.*;
import com.compinter.controller.PorterLodgeController;


public class ClientHandler implements Runnable {
    private Socket client;
    private PrintWriter out;
    private InputThread input;

    public ClientHandler(Socket clientSocket, PorterLodgeController porter, Server server, int number) throws IOException {
        this.client = clientSocket;
        input = new InputThread(client, porter, server, number);
        out = new PrintWriter(client.getOutputStream(), true);
        out.println("NUM " + (number+1));
    }

    public void setPorter(PorterLodgeController controller) {
        input.setPorter(controller);
    };

    @Override
    public void run() {
        input.start();
    }

    //Envia al cliente un mensaje que contiene el comando ANN que se utiliza para identificar cuando una visita llega, 
    //así mismo se envia el nombre del visitante. 
    public void announce(String visitor) {
        out.println("ANN " + visitor);
    }


    public void requestChat() {
        out.println("CRQ");
    }

    public void acceptChat() {
        out.println("ACH");
    }

    public void denyChat() {
        out.println("DCH");
    }

    public void finishChat() {
        out.println("FCH");
    }

    public void sendMessage(String string) {
        out.println("MSG " + string);
    }
}