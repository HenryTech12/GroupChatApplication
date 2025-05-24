package org.chatapp.ui.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerThread implements Runnable {

    private Socket serverSocket;
    private List<Socket> listOfSocket;
    private String msg = "";
    public ServerThread(Socket serverSocket, List<Socket> listOfSocket) {
        this.serverSocket = serverSocket;
        this.listOfSocket = listOfSocket;
    }

    public void readMessage() {

    }
    public void forwardMessage() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        String line = "";
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);

            for (Socket socket : listOfSocket) {
                System.out.println(socket.getPort());
                System.out.println(serverSocket.getPort());

                if (socket.getPort() != serverSocket.getPort()) {
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(line);
                    printWriter.flush();

                    System.out.println("message forwarded to other socket.");
                }
            }
        }
    }


    @Override
    public void run() {

        new Thread(() -> {
            try {
                forwardMessage();
            }
            catch (IOException ie) {
                ie.printStackTrace();
            }
        }).start();


    }
}
