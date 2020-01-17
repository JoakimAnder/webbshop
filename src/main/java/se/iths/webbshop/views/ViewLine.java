package se.iths.webbshop.views;

import se.iths.webbshop.entities.Line;

public class ViewLine {
    public int id;
    public Integer product;
    public double price;
    public int amount;

    public ViewLine() {
    }

    public ViewLine(Line line) {
        this.id = line.getId();
        this.product = line.getProduct().getId();
        this.price = line.getPrice();
        this.amount = line.getAmount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
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
}
