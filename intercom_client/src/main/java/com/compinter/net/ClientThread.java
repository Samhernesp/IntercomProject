package com.compinter.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.compinter.controller.AuthorizationController;
import com.compinter.controller.ChatController;

import javafx.application.Platform;

//Se encarga de recibir en todo momento.
public class ClientThread extends Thread {
    private BufferedReader in;
    AuthorizationController controller;
    ChatController controller2;
    
    public ClientThread(Socket client, AuthorizationController controller) throws IOException {
        this.controller = controller;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    // Método que se encuentra constantemente activo, recibiendo los mensajes del servidor,
    // y se encarga de distinguir cada uno de los comandos.
    @Override
    public void run(){
        try {
            while (true) {
                String serverResponse = in.readLine();
                System.out.println(serverResponse);
                String[] msg = serverResponse.split(" ", 2);
                System.out.println(msg[0]+"--");
                if (msg.length>1) {
                    System.out.println(msg[1]+"++");
                }             
                       
                switch(msg[0]) {
                    case "ANN":
                        Platform.runLater(() -> {
                            try {
                                controller.openCall(msg[1]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    case "NUM":
                        Platform.runLater(() -> controller.setNumber(Integer.parseInt(msg[1])));
                        break;
                    case "CRQ":
                        Platform.runLater(() ->  {
                            try {
                                controller.openChatR();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        });
                        break;
                    case "DCH":
                        Platform.runLater(() ->  {
                                controller.deniedChat();
                        });
                        break;
                    case "ACH":
                        Platform.runLater(() ->  {
                                try {
                                    controller.acceptedChat();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                        });
                        break;
                    case "FCH":
                        Platform.runLater(() ->  {
                                try {
                                    controller.finishChat();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                        });
                        break;
                    case "MSG":
                        Platform.runLater(() ->  {
                            controller2.receiveMessage(msg[1]);
                        });
                        break;
                }

            }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void setController(ChatController controller) {
        this.controller2 = controller;
    }
}
