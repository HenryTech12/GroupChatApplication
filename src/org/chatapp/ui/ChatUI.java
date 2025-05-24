package org.chatapp.ui;

import org.chatapp.ui.user.MessageUI;
import org.chatapp.ui.user.UserInfo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatUI extends JPanel implements ActionListener {
    private JFrame uiFrame;
    private JTextField textField;
    private JLabel serverInfo;
    private JButton sendButton;
    private MessageUI messageUI;
    private static Socket clientSocket;
    private String rMsg = "";
    private UserInfo userInfo;
    private JScrollPane scrollPane;
    JPanel messagePanel;

    public ChatUI(Socket clientSocket, UserInfo userInfo) {
        ChatUI.clientSocket = clientSocket;
        messageUI = new MessageUI();
        this.userInfo = userInfo;
    }

    public ChatUI(Socket clientSocket) {
        ChatUI.clientSocket = clientSocket;
        messageUI = new MessageUI();
    }

    public ChatUI() {
        messageUI = new MessageUI();
    }

    public void initUI() {
        Timer timer = new Timer(1, this);
        timer.start();
        uiFrame = new JFrame();
        uiFrame.setSize(UIConfiguration.WIDTH,UIConfiguration.HEIGHT);
        uiFrame.setResizable(false);
        uiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        uiFrame.setAlwaysOnTop(true);
        uiFrame.setTitle(userInfo.getUser().concat(" Chat Space"));

        messagePanel = messageUI.getMessagePanel();
        messagePanel.setBackground(UIColor.containerColor);

        this.setSize(UIConfiguration.WIDTH, UIConfiguration.HEIGHT);
        this.setVisible(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(UIColor.containerColor);
        this.setOpaque(true);
        this.setAutoscrolls(true);
        this.setFocusable(true);


        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(UIColor.containerColor);
        labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));

        serverInfo = new JLabel("Chat Room");
        serverInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        serverInfo.setBackground(UIColor.containerColor);
        serverInfo.setFont(new Font(Font.SERIF,Font.ITALIC,20));
        serverInfo.setForeground(UIColor.otherTextColor);

        labelPanel.add(serverInfo);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.X_AXIS));

        textField = new JTextField("Message");
        textField.setPreferredSize(new Dimension(UIConfiguration.WIDTH+500,50));
        textField.setMaximumSize(new Dimension(UIConfiguration.WIDTH+500,50));
        textField.setVisible(true);
        Border border = BorderFactory.createLineBorder(UIColor.mainTextColor,5,true);
        textField.setBorder(border);
        textField.setOpaque(true);

        sendButton = new JButton("SEND");
        sendButton.setVisible(true);
        sendButton.setPreferredSize(new Dimension(80,50));
        sendButton.setMaximumSize(new Dimension(80,50));
        sendButton.addActionListener(this);
        sendButton.setFocusable(false);
        sendButton.setFont(Font.getFont(Font.SANS_SERIF));
        sendButton.setBackground(UIColor.containerColor);
        sendButton.setForeground(UIColor.otherTextColor);
        sendButton.setOpaque(true);




        this.add(labelPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(messagePanel);
        inputPanel.add(textField);
        inputPanel.add(sendButton);
        this.add(Box.createVerticalGlue());
        this.add(inputPanel);

        this.setPreferredSize(new Dimension(380,UIConfiguration.HEIGHT));


        scrollPane = new JScrollPane(this);
        scrollPane.getViewport().setBackground(Color.BLACK);


        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.darkGray;
                this.trackColor = Color.black;
            }
        });

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        uiFrame.add(scrollPane);
        uiFrame.setVisible(true);
    }

    public void userUI(UserInfo userInfo) {

    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setBackground(UIColor.otherTextColor);
        graphics2D.drawLine(0, 230, WIDTH, 230);


    }

    public JPanel addUsers() {

        JPanel panel = new JPanel();
        panel.setBackground(UIColor.containerColor);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sendButton) {
            String message = textField.getText();
            updateTextField();

            System.out.println("clicked!!");

            new Thread(() -> {
                JScrollPane jScrollPane = messageUI.sendMessageUI(message);
                if(!messagePanel.isAncestorOf(jScrollPane)) {
                       JPanel rightMessagePanel = new JPanel();
                       rightMessagePanel.setLayout(new BoxLayout(rightMessagePanel,BoxLayout.X_AXIS));
                       rightMessagePanel.setBackground(UIColor.containerColor);
                       rightMessagePanel.add(Box.createHorizontalGlue());
                       rightMessagePanel.add(jScrollPane);
                       messagePanel.add(Box.createVerticalStrut(10));
                       messagePanel.add(rightMessagePanel);
                       messagePanel.add(Box.createVerticalStrut(10));

                }
                moveToBottom();
                revalidate();
                repaint();
            }).start();

            Thread thread = new Thread(() -> sendMessage(message));
            thread.start();
        }

        new Thread(() -> updateChatBox()).start();

        new Thread(() -> readMessage()).start();


        //updates scroll bar to be at bottom always
        SwingUtilities.invokeLater(() -> {
            if(scrollPane != null) {
                JScrollBar scrollBar1 = scrollPane.getVerticalScrollBar();
                if(scrollBar1 != null) {
                    scrollBar1.setValue(scrollBar1.getMaximum());
                }
            }
        });

        revalidate();
        repaint();
    }

    public void updateChatBox() {
        if(messagePanel != null)
            this.setPreferredSize(new Dimension(380,(messagePanel.getHeight() + 200)));
    }
    public void moveToBottom() {
        if(scrollPane != null) {
            JScrollBar scrollBar1 = scrollPane.getVerticalScrollBar();
            if(scrollBar1 != null) {
                scrollBar1.setValue(scrollBar1.getMaximum());
            }
        }
    }
    public void sendMessage(String mess) {
        if(clientSocket != null)
            try {
                // if (chatMessage.getSentMessage() != null) {
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                printWriter.println(userInfo.getUser()+": "+mess);

                printWriter.flush();
                System.out.println("message sent to server.");
                //}
             } catch (IOException ie) {
                System.out.println("an error occurred!!: " + ie.getMessage());
            }
        }

        public void updateTextField() {
           textField.setText("");
        }
    public void readMessage() {
        String line = "";
        if (clientSocket != null)
            try {
                InputStreamReader streamReader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                while ((line = bufferedReader.readLine()) != null) {
                        rMsg = line;
                 new Thread(() -> {
                    JScrollPane jScrollPane = messageUI.receiveMessageUI(rMsg);
                        if(!messagePanel.isAncestorOf(jScrollPane)) {
                            JPanel leftMessagePanel = new JPanel();
                            leftMessagePanel.setLayout(new BoxLayout(leftMessagePanel,BoxLayout.X_AXIS));
                            leftMessagePanel.setBackground(UIColor.containerColor);
                            leftMessagePanel.add(jScrollPane);
                            leftMessagePanel.add(Box.createHorizontalGlue());
                            messagePanel.add(leftMessagePanel);
                            messagePanel.add(Box.createVerticalStrut(10));
                        }
                    revalidate();
                    repaint();
                     System.out.println("MSG: "+ rMsg);
                 }).start();
                    System.out.println(line);
                }
            } catch (IOException ie) {
                System.out.println("an error occurred!!: " + ie.getMessage());
            }
        }
    }

