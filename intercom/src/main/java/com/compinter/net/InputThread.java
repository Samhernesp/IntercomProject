package com.compinter.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.compinter.controller.PorterLodgeController;

public class InputThread extends Thread {
    private BufferedReader in;
    private PorterLodgeController porter;
    private Server server;
    private int number;

    public InputThread(Socket client, PorterLodgeController porter, Server server, int number) throws IOException {
        this.porter = porter;
        this.server = server;
        this.number = number;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void setPorter(PorterLodgeController controller) {
        this.porter = controller;
    }

    // Método que se encuentra constantemente activo, recibiendo los mensajes del cliente, y se encarga de distinguir cada uno de los comandos.
    @Override
    public void run() {
        try {
            while (true) {
                String request;
                request = in.readLine();
                String toLog;
                System.out.println(request + " -- INPUT");
                String[] msg = request.split(" ", 3);
                switch (msg[0]) {
                    case "ACP":
                        porter.showPermissionAccepted(Integer.parseInt(msg[1]));
                        toLog = "[APT " + number + "] Permission accepted to visitor\n";
                        server.endCall(number);
                        break;
                    case "DNY":
                        porter.showPermissionDeny(Integer.parseInt(msg[1]));
                        toLog = "[APT " + number + "] Permission denied to visitor\n";

                        server.endCall(number);
                        break;
                    case "EMG":
                        porter.panicButton(Integer.parseInt(msg[1]));
                        toLog = "[APT " + number + "] PANIC BUTTON ACTIONED \n";
                        break;
                    case "CRQ":
                        server.requestChat(Integer.parseInt(msg[1]));
                        break;
                    case "ACH":
                        server.acceptChat(Integer.parseInt(msg[1]));
                        break;
                    case "DCH":
                        server.denyChat(Integer.parseInt(msg[1]));
                        break;
                    case "FCH":
                        server.finishChat(Integer.parseInt(msg[1]));
                        break;
                    case "MSG":
                        server.sendMessage(msg[2],Integer.parseInt(msg[1]));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Something went wrong");
            System.err.println(e.getStackTrace());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
