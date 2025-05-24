package org.chatapp.ui.client;

import org.chatapp.ui.user.UserInfo;
import org.chatapp.ui.user.UserUI;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
    static List<Integer> l = new ArrayList<>();
    public static void main(String[] args) {


        try {
            Socket clientSocket = new Socket("localhost", 9090);

            System.out.println("client connected to server");


            UserInfo userInfo = new UserInfo();

            UserUI userUI = new UserUI(clientSocket,userInfo);
            userUI.initUI();

        }
        catch(IOException ie) {
            System.out.println(" an error occurred!! : "+ ie.getMessage());
        }
    }
}
