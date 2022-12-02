package com.compinter.net;

import java.io.*;
import java.net.*;

import com.compinter.controller.AuthorizationController;
import com.compinter.controller.ChatController;
import com.compinter.services.MailServices;

public class Client {
    private String emergencyEmail;
    private static final int SERVER_PORT = 9090;
    private AuthorizationController controller;
    private static ClientThread thread;
    private MailServices mail;
    private static PrintWriter out;

    Socket socket;
    BufferedReader input;

    public Client(String serverIp, String emergencyEmail) throws UnknownHostException, IOException {
        this.emergencyEmail = emergencyEmail;
        mail = new MailServices();
        this.socket = new Socket(serverIp, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    // Método que envia un comando para desencadenar la alerta de aceptación del visitante.
    public void acceptVisitor(int number) {
        out.println("ACP " + number);
    }

    public void start() throws IOException {
        thread = new ClientThread(socket, controller);
        thread.start();
    }

    // Método que envia un comando para desencadenar la alerta de rechazo del visitante.
    public void denyVisitor(int number) {
        out.println("DNY " + number);
    }
    
    // Método que envia un comando para desencadenar la alarma de emergencia y enviar el correo electronico.
    public void playAlarm(int number) {
        out.println("EMG " + number);
    }

    public void setController(ChatController controller) {
        thread.setController(controller);
    }   

    public static ClientThread getThread() {
        return thread;
    }

	public String getEmergencyEmail() {
		return emergencyEmail;
	}

    //Realiza la solicitud hacia dónde se dirige el chat.
    public void requestChat(int from) throws IOException {
        out.println("CRQ " + from);
    }

    public void acceptChat(int num) throws UnknownHostException, IOException {
        out.println("ACH " + num);
    }

    public void denyChat(int num) {
        out.println("DCH " + num);
    }

    public void finishChat(int num) {
        out.println("FCH " + num);
    }

    public void sendMessage(String msg, int num) {
        out.println("MSG " + num + " " + msg);
    }

    public void setController(AuthorizationController controller) {
        this.controller = controller;
    }
}
