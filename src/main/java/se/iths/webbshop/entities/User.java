package se.iths.webbshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private String address;
    private boolean isAdmin;
    @OneToOne
    private Cart cart;

    public User() {
    }

    public User(int id, String username, String password, String address, Cart cart) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.isAdmin = false;
        this.cart = cart;
    }
    public User(int id, String username, String password, String address, boolean isAdmin, Cart cart) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.isAdmin = isAdmin;
        this.cart = cart;
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
