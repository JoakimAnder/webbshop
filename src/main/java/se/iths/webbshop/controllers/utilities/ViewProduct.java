package se.iths.webbshop.controllers.utilities;

import se.iths.webbshop.entities.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewProduct {
    private Product product;

    public ViewProduct() {
    }

    public ViewProduct(int id, String title, String image, double price, String tags) {
        List<String> tagList = Arrays.stream(tags.split(",")).map(String::trim).collect(Collectors.toList());
        product = new Product(id, title, image, price, tagList);
    }

    public int getId() {
        return product.getId();
    }

    public void setId(int id) {
        product.setId(id);
    }

    public String getTitle() {
        return product.getTitle();
    }

    public void setTitle(String title) {
        product.setTitle(title);
    }

    public String getImage() {
        return product.getImage();
    }

    public void setImage(String image) {
        product.setImage(image);
    }

    public double getPrice() {
        return product.getPrice();
    }

    public void setPrice(double price) {
        product.setPrice(price);
    }

    public String getTags() {
        return product.getTags().stream().reduce((t1, t2) -> String.format("%s, %s", t1, t2)).orElse("");
    }

    public void setTags(String tags) {
        product.setTags(Arrays.stream(tags.split(",")).map(String::trim).collect(Collectors.toList()));
    }
}
