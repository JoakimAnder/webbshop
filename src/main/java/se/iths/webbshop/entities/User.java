package se.iths.webbshop.entities;

import se.iths.webbshop.controllers.utilities.Cart;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(unique = true, nullable = false)
    private String username = "";
    @Column(nullable = false)
    private String password = "";
    @Column(nullable = false)
    private String address = "";
    private boolean isAdmin;

    public User() {
    }

    public User(int id, String username, String password, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.isAdmin = false;
    }
    public User(int id, String username, String password, String address, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.isAdmin = isAdmin;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
