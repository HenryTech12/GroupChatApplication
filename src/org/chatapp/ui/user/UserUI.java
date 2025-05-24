package org.chatapp.ui.user;

import org.chatapp.ui.ChatUI;
import org.chatapp.ui.UIColor;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class UserUI extends JPanel {

    private JFrame uiFrame;
    private Socket clientSocket;
    private UserInfo userInfo;

    public UserUI(Socket clientSocket, UserInfo userInfo) {
        this.clientSocket = clientSocket;
        this.userInfo = userInfo;
    }

    public  UserUI() {

    }

    public void initUI() {

        this.setSize(300,200);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setVisible(true);
        this.setBackground(UIColor.containerColor);
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setOpaque(true);


        JLabel jLabel = new JLabel("Enter Your Username");
        jLabel.setBounds(30, 0, 100, 20);
        jLabel.setVisible(true);
        jLabel.setBackground(UIColor.containerColor);
        jLabel.setFont(new Font(Font.SERIF,Font.ITALIC,15));
        jLabel.setForeground(UIColor.COLOR_ORANGE);
        jLabel.setOpaque(true);
        //this.add(jLabel);

        JTextField jTextField = new JTextField(" Your Username ");
        jTextField.setBounds(30, 0, 300, 40);
        jTextField.setMaximumSize(new Dimension(500,40));
        jTextField.setLayout(null);
        jTextField.setForeground(Color.DARK_GRAY);
        jTextField.setBackground(UIColor.containerColor);
        jTextField.setVisible(true);


        JButton jButton = new JButton("Add");
        jButton.setBounds(30, 0, 80, 40);
        jButton.setMaximumSize(new Dimension(80,40));
        jButton.setVisible(true);
        jButton.setOpaque(true);
        jButton.addActionListener((e) -> {
            if(jTextField.getText() != null) {
                userInfo.setUser(jTextField.getText());
                new ChatUI(clientSocket,userInfo).initUI();
                uiFrame.dispose();
            }
        });
        jButton.setBackground(UIColor.COLOR_ORANGE);
        jButton.setFocusable(false);

        this.add(jLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(jTextField);
        this.add(Box.createVerticalStrut(10));
        this.add(jButton);

        uiFrame = new JFrame();
        uiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        uiFrame.setVisible(true);
        uiFrame.setLocationRelativeTo(null);
        uiFrame.setResizable(false);
        uiFrame.setSize(300,200);
        uiFrame.add(this);

    }
}
