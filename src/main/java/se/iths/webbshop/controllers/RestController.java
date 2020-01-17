package se.iths.webbshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.iths.webbshop.views.*;
import se.iths.webbshop.entities.*;
import se.iths.webbshop.services.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    Service service;

    @GetMapping("/users")
    List<ViewUser> getUsers() {
        return service.getUsers().stream().map(ViewUser::new).map(ViewUser::removePass).collect(Collectors.toList());
    }
    @GetMapping("/orders")
    List<ViewOrder> getOrders() {
        return service.getOrders().stream().map(ViewOrder::new).collect(Collectors.toList());
    }
    @GetMapping("/lines")
    List<ViewLine> getLines() {
        return service.getLines().stream().map(ViewLine::new).collect(Collectors.toList());
    }
    @GetMapping("/products")
    List<ViewProduct> getProducts() {
        return service.getProducts().stream().map(ViewProduct::new).collect(Collectors.toList());
    }

    @PostMapping("/user")
    ViewUser createUser(ViewUser newUser) {
        User user = service.convert(newUser);
        user = service.create(user);
        return new ViewUser(user);
    }
    @PostMapping("/order")
    ViewOrder createOrder(ViewOrder newOrder) {
        Order order = service.convert(newOrder);
        order = service.create(order);
        return new ViewOrder(order);
    }

    @PostMapping("/product")
    ViewProduct createProduct(ViewProduct newProduct) {
        Product product = service.convert(newProduct);
        product = service.create(product);
        return new ViewProduct(product);
    }

    @PutMapping("/user/{id}")
    ViewUser updateUser(@PathVariable Integer id, ViewUser newUser) {
        User user = service.convert(newUser);
        user = service.update(id, user);
        return new ViewUser(user).removePass();
    }
    @PutMapping("/order/{id}")
    ViewOrder updateOrder(@PathVariable Integer id, ViewOrder newOrder) {
        Order order = service.convert(newOrder);
        order = service.update(id, order);
        return new ViewOrder(order);
    }
    @PutMapping("/line/{id}")
    ViewLine updateLine(@PathVariable Integer id, ViewLine newLine) {
        Line line = service.convert(newLine);
        line = service.update(id, line);
        return new ViewLine(line);
    }
    @PutMapping("/product/{id}")
    ViewProduct updateProduct(@PathVariable Integer id, ViewProduct newProduct) {
        Product product = service.convert(newProduct);
        product = service.update(id, product);
        return new ViewProduct(product);
    }

    @GetMapping("/user/{id}")
    ViewUser getUser(@PathVariable Integer id) {
        User user = service.getUser(id);
        return new ViewUser(user).removePass();
    }
    @GetMapping("/order/{id}")
    ViewOrder getOrder(@PathVariable Integer id) {
        Order order = service.getOrder(id);
        return new ViewOrder(order);
    }
    @GetMapping("/line/{id}")
    ViewLine getLine(@PathVariable Integer id) {
        Line line = service.getLine(id);
        return new ViewLine(line);
    }
    @GetMapping("/product/{id}")
    ViewProduct getProduct(@PathVariable Integer id) {
        Product product = service.getProduct(id);
        return new ViewProduct(product);
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
    }
    @DeleteMapping("/order/{id}")
    void deleteOrder(@PathVariable Integer id) {
        service.deleteOrder(id);
    }
    @DeleteMapping("/line/{id}")
    void deleteLine(@PathVariable Integer id) {
        service.deleteLine(id);
    }
    @DeleteMapping("/product/{id}")
    void deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
    }

    @GetMapping("/fillDB")
    void fillDB() {

        createUser(new ViewUser(new User(0, "user", "", "Customer RD 133")));
        createUser(new ViewUser(new User(0, "admin", "", "Local", true)));

        createProduct(new ViewProduct(new Product(0, "PlayStation", "https://images.app.goo.gl/PKwBfrZcEEqVEFHo6", 3000, Arrays.asList("sony", "console"))));
        createProduct(new ViewProduct(new Product(0, "Xbox", "https://images.app.goo.gl/hdWvEDYtPM6FKnn66", 2000, Arrays.asList("microsoft", "console"))));
        createProduct(new ViewProduct(new Product(0, "Switch", "https://images.app.goo.gl/fGaBBKjKDCFKk5Sv7", 4000, Arrays.asList("nintendo", "console"))));

        createProduct(new ViewProduct(new Product(0, "Keyboard", "https://images.app.goo.gl/498R7iXXxMf5Fzg69", 200, Arrays.asList("keyboard", "accessory"))));
        createProduct(new ViewProduct(new Product(0, "Mouse", "https://images.app.goo.gl/Qb8TkiAmfUsKEMSL7", 100, Arrays.asList("mouse", "accessory"))));
    }
}
