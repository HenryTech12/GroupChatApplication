package org.chatapp.ui.server;

import org.chatapp.ui.threads.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    int numOfConnectedUsers;
    static boolean stop = false;
    static List<Socket> listOfSocket = new ArrayList<>();
    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(9090);

            System.out.println("server created....");
            System.out.println("waiting for client to connect...");
          while(!stop) {
              Socket socket = serverSocket.accept();

              System.out.println("port: "+socket.getPort());
              System.out.println("localport: "+socket.getLocalPort());

              listOfSocket.add(socket);

              System.out.println(listOfSocket.size());
              System.out.println("client connected to server...");

              new Thread(new ServerThread(socket,listOfSocket)).start();
          }
        }
        catch (IOException ie) {
            System.out.println(" an error occurred!!: "+ie.getMessage());
        }
    }
}
