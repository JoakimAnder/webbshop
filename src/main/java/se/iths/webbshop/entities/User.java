package se.iths.webbshop.entities;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!username.equals(user.username)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
