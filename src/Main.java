import org.chatapp.ui.ChatUI;
import org.chatapp.ui.user.UserInfo;
import org.chatapp.ui.user.UserUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends  JPanel {
    public static void main(String[] args) {

        /*List<String> list = new ArrayList<>();
        list.add("Hailie");
        UserInfo userInfo = new UserInfo();
        userInfo.setConnectedUsersName(list);
        chatUI.userUI(userInfo);*/

        ChatUI chatUI = new ChatUI();
        chatUI.initUI();


    }

    public void test() {
        JFrame jFrame = new JFrame();
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setLayout(null);
        this.setOpaque(true);
        this.setAutoscrolls(true);
        this.setFocusable(true);

        int y =40;

            JTextArea textPane = new JTextArea("hdjdhdjhdh" +
                    "hjdhhfhjfjhfjhf" +
                    "fhhfhfhfhhff" +
                    "fhfhhfhfhfhhfhhfhhf" +
                    "ffhfhfhfhfhhfhfh");
            textPane.setLineWrap(true);
            textPane.setSize(100,100);
            textPane.setWrapStyleWord(true);
            textPane.getDocument().addDocumentListener(new DocumentListener() {

                public void updateSize() {
                    int lines = textPane.getLineCount();

                    textPane.setRows(Math.min(lines+1,10));
                    textPane.revalidate();
                }
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateSize();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                        updateSize();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateSize();
                }
            });

            this.add(textPane);




        jFrame.add(this);
        jFrame.setVisible(true);
    }
}