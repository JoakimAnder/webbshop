package se.iths.webbshop.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "my_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToOne
    private User user;
    @Column(nullable = false)
    private String status;
    @OneToMany(targetEntity = Line.class)
    @Column(nullable = false)
    private List<Line> lines;

    public Order() {
    }

    public Order(int id, User user, List<Line> lines) {
        this.id = id;
        this.user = user;
        status = "Pending";
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", lines=" + lines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id == order.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
