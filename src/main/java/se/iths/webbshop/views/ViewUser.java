package se.iths.webbshop.views;

import se.iths.webbshop.entities.User;

public class ViewUser {
    private int id;
    private String username = "";
    private String password = "";
    private String address = "";
    private boolean isAdmin;

    public ViewUser() {
    }

    public ViewUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.isAdmin = user.isAdmin();
    }

    public ViewUser removePass() {
        password = "";
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
