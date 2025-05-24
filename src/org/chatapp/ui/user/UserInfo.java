package org.chatapp.ui.user;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    private List<String> connectedUsers = new ArrayList<>();
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        connectedUsers.add(user);
        this.user = user;
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<String> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }
}
