package se.iths.webbshop.entities;

import javax.persistence.*;

@Entity
@Table(name = "my_line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToOne
    private Product product;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private int amount;

    public Line() {
    }

    public Line(int id, Product product, double price, int amount) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", product=" + product +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return id == line.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
