package se.iths.webbshop.controllers.utilities.Views;

import se.iths.webbshop.entities.Product;

import javax.persistence.ElementCollection;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewProduct {
    public int id;
    public String title;
    public String image;
    public double price;
    public List<String> tags;

    public ViewProduct() {
    }

    public ViewProduct(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.tags = product.getTags();
    }

    public void setTagsfromString(String tags) {
        this.tags = Arrays.stream(tags.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
