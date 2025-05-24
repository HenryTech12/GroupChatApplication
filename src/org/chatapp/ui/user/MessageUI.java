package org.chatapp.ui.user;

import org.chatapp.ui.UIColor;
import org.chatapp.ui.UIConfiguration;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MessageUI {

    private List<JTextArea> sMessages = new ArrayList<>();
    private List<JTextArea> rMessages = new ArrayList<>();
    private List<JTextArea> messages = new ArrayList<>();
    private boolean sendExists = false;
    private boolean receiveExists = false;
    private int listY = 0;
    private Dimension scrollDimension;
    private Border border = BorderFactory.createLineBorder(UIColor.otherTextColor,10,true);
    public JPanel getMessagePanel() {
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.Y_AXIS));
        return messagePanel;
    }


    public JScrollPane sendMessageUI(String smsg) {


        if(smsg != null) {
            JTextArea sendTextArea = getTextArea(smsg);
            sendTextArea.setAlignmentX(Component.RIGHT_ALIGNMENT);
            sMessages.add(sendTextArea);
            messages.add(sendTextArea);

            JScrollPane scrollPane = getScrollPane(sendTextArea);
            scrollPane.setAlignmentX(Component.RIGHT_ALIGNMENT); // Important for BoxLayout

            return scrollPane;
        }

        return null;
    }

    public JScrollPane getScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.darkGray;
                this.trackColor = Color.black;
            }
        });
        scrollPane.setMaximumSize(textArea.getPreferredSize());
        scrollPane.setPreferredSize(textArea.getPreferredSize());

        return scrollPane;
    }

    public JTextArea getTextArea(String msg) {
        JTextArea textArea = new JTextArea(msg) {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                int width = Math.max(d.width, 300);
                int height = Math.max(d.height, msg.length());
                return new Dimension(width, height);
            }
        };
        textArea.setLineWrap(true);
        textArea.setBackground(UIColor.otherTextColor);
        textArea.setForeground(UIColor.containerColor);
        textArea.setFont(new Font(Font.SERIF,Font.ITALIC,14));
        textArea.setBorder(border);
        textArea.setMargin(new Insets(10,10,10,10));

        return textArea;
    }


    public JScrollPane receiveMessageUI(String rmsg) {

        if(rmsg != null) {
            JTextArea receiveTextArea = getTextArea(rmsg);
            receiveTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
            rMessages.add(receiveTextArea);
            messages.add(receiveTextArea);

            JScrollPane scrollPane = getScrollPane(receiveTextArea);
            scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT); // Important for BoxLayout

            return scrollPane;
        }
        return null;
    }
}
