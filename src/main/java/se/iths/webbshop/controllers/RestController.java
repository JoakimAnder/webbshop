package se.iths.webbshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.iths.webbshop.data.Repository;
import se.iths.webbshop.entities.*;

import java.util.Arrays;
import java.util.Optional;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    Repository repo;

    @GetMapping("/users")
    Iterable<User> getUsers() {
        return repo.user.findAll();
    }
    @GetMapping("/carts")
    Iterable<Cart> getCarts() {
        return repo.cart.findAll();
    }
    @GetMapping("/orders")
    Iterable<Order> getOrders() {
        return repo.order.findAll();
    }
    @GetMapping("/lines")
    Iterable<Line> getOrderLines() {
        return repo.line.findAll();
    }
    @GetMapping("/products")
    Iterable<Product> getProducts() {
        return repo.product.findAll();
    }

    @PostMapping("/user")
    User createUser(User newUser) {
        return repo.user.save(newUser);
    }
    @PostMapping("/cart")
    Cart createCart(Cart newCart) {
        return repo.cart.save(newCart);
    }
    @PostMapping("/order")
    Order createOrder(Order newOrder) {
        return repo.order.save(newOrder);
    }
    @PostMapping("/line")
    Line createOrderLine(Line newLine) {
        return repo.line.save(newLine);
    }
    @PostMapping("/product")
    Product createProduct(Product newProduct) {
        return repo.product.save(newProduct);
    }

    @PutMapping("/user")
    User updateUser(User newUser) {
        return repo.user.save(newUser);
    }
    @PutMapping("/cart")
    Cart updateCart(Cart newCart) {
        return repo.cart.save(newCart);
    }
    @PutMapping("/order")
    Order updateOrder(Order newOrder) {
        return repo.order.save(newOrder);
    }
    @PutMapping("/line")
    Line updateOrderLine(Line newLine) {
        return repo.line.save(newLine);
    }
    @PutMapping("/product")
    Product updateProduct(Product newProduct) {
        return repo.product.save(newProduct);
    }

    @GetMapping("/user")
    Optional<User> getUser(Integer id) {
        return repo.user.findById(id);
    }
    @GetMapping("/cart")
    Optional<Cart> getCart(Integer id) {
        return repo.cart.findById(id);
    }
    @GetMapping("/order")
    Optional<Order> getOrder(Integer id) {
        return repo.order.findById(id);
    }
    @GetMapping("/line")
    Optional<Line> getOrderLine(Integer id) {
        return repo.line.findById(id);
    }
    @GetMapping("/product")
    Optional<Product> getProduct(Integer id) {
        return repo.product.findById(id);
    }

    @DeleteMapping("/user")
    void deleteUser(User user) {
        repo.user.delete(user);
    }
    @DeleteMapping("/cart")
    void deleteCart(Cart user) {
        repo.cart.delete(user);
    }
    @DeleteMapping("/order")
    void deleteOrder(Order order) {
        repo.order.delete(order);
    }
    @DeleteMapping("/line")
    void deleteOrderLine(Line line) {
        repo.line.delete(line);
    }
    @DeleteMapping("/product")
    void deleteProduct(Product product) {
        repo.product.delete(product);
    }

    @GetMapping("/fillDB")
    void fillDB() {
        createUser(new User(0, "user", "", "Customer RD 133", createCart(new Cart(0))));
        createUser(new User(0, "admin", "", "Local", true, createCart(new Cart(0))));

        createProduct(new Product(0, "PlayStation", "https://images.app.goo.gl/PKwBfrZcEEqVEFHo6", 3000, Arrays.asList("sony", "console")));
        createProduct(new Product(0, "Xbox", "https://images.app.goo.gl/hdWvEDYtPM6FKnn66", 2000, Arrays.asList("microsoft", "console")));
        createProduct(new Product(0, "Switch", "https://images.app.goo.gl/fGaBBKjKDCFKk5Sv7", 4000, Arrays.asList("nintendo", "console")));

        createProduct(new Product(0, "Keyboard", "https://images.app.goo.gl/498R7iXXxMf5Fzg69", 200, Arrays.asList("keyboard", "accessory")));
        createProduct(new Product(0, "Mouse", "https://images.app.goo.gl/Qb8TkiAmfUsKEMSL7", 100, Arrays.asList("mouse", "accessory")));
    }
}
