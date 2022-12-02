package com.compinter.net;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.compinter.controller.AreaController;
import com.compinter.controller.PorterLodgeController;

public class Server extends Thread {
    private static final int PORT = 9090;
    private AreaController areaController;
    private PorterLodgeController porterLodgeController;
    final static private int APARTMENT_NUMBER = 2;
    private boolean[] calls = new boolean[APARTMENT_NUMBER];

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(APARTMENT_NUMBER);
    private static int clientCount;

    public Server(PorterLodgeController porterLodgeController) {
        this.porterLodgeController = porterLodgeController;
    }

    // Abre un puerto para conectarse con los clientes.
    @Override
    public void run() {
        try {
            ServerSocket listener = new ServerSocket(PORT);
            areaController.println("[SERVER] Server started, IP: " + InetAddress.getLocalHost().getHostAddress());

            while (true) {
                if (clientCount >= APARTMENT_NUMBER) {
                    areaController.println("[SERVER] Required number of connections reached");
                    areaController.enableContinue();
                }

                areaController.println("[SERVER] Waiting for client connection");
                Socket client = listener.accept();
                clientCount++;
                areaController.println("[SERVER] Connected to client #" + clientCount);

                ClientHandler clientThread = new ClientHandler(client, porterLodgeController, this, clientCount-1);
                clients.add(clientThread);
                pool.execute(clientThread);
            }
        } catch (IOException e) {
            areaController.println("[SERVER] ERROR - SERVER COULD NOT START");
        }
    }

    // Hace el llamado al hilo que corresponde a un determinado apartamento para informar de una visita. 
    // retorna un boolean para verificar si ya se encuentra en una solicitud. 
    public boolean announce(int apartment, String visitor) {
        apartment--;
        if (!calls[apartment]) {
            calls[apartment] = true;
            clients.get(apartment).announce(visitor);
            return true;
        }

        return false;
    }

    public void setController(AreaController areaController) {
        this.areaController = areaController;
    }

    public void setController(PorterLodgeController porterLodgeController) {
        this.porterLodgeController = porterLodgeController;
    }

    // Setea a false la solicitud a un determiado apartamento, es decir se encuentra disponible para recibir más solicitudes de visita.
    public void endCall(int number) {
        calls[number] = false;
    }

    // Hace un llamado al método request chat dirigido al apartamento objetivo.
    public void requestChat(int from) {
        from--;
        int to=0;
        if (from==0) {
            to=1;
        }
        clients.get(to).requestChat();
    }

    public void acceptChat(int from) {
        from--;
        int to=0;
        if (from==0) {
            to=1;
        }
        clients.get(to).acceptChat();
    }

    public void denyChat(int from) {
        from--;
        int to=0;
        if (from==0) {
            to=1;
        }
        clients.get(to).denyChat();
    }

    public void finishChat(int from) {
        from--;
        int to=0;
        if (from==0) {
            to=1;
        }
        clients.get(to).finishChat();
    }

    public void sendMessage(String string, int from) {
        from--;
        int to=0;
        if (from==0) {
            to=1;
        }
        clients.get(to).sendMessage(string);
    }
}
