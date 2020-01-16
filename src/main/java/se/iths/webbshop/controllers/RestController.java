package se.iths.webbshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.iths.webbshop.controllers.utilities.Entry;
import se.iths.webbshop.controllers.utilities.Views.*;
import se.iths.webbshop.data.Repository;
import se.iths.webbshop.entities.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    Repository repo;

    @GetMapping("/users")
    List<ViewUser> getUsers() {
        return repo.user.findAll().stream().map(ViewUser::new).map(ViewUser::removePass).collect(Collectors.toList());
    }
    @GetMapping("/orders")
    List<ViewOrder> getOrders() {
        return repo.order.findAll().stream().map(ViewOrder::new).collect(Collectors.toList());
    }
    @GetMapping("/lines")
    List<ViewLine> getLines() {
        return repo.line.findAll().stream().map(ViewLine::new).collect(Collectors.toList());
    }
    @GetMapping("/products")
    List<ViewProduct> getProducts() {
        return repo.product.findAll().stream().map(ViewProduct::new).collect(Collectors.toList());
    }

    @PostMapping("/user")
    ViewUser createUser(ViewUser newUser) {
        User user = new User();
        user.setPassword(newUser.getPassword());
        user.setUsername(newUser.getUsername());
        user.setAddress(newUser.getAddress());
        user.setAdmin(newUser.isAdmin());

        return new ViewUser(repo.user.save(user));
    }
    @PostMapping("/order")
    ViewOrder createOrder(ViewOrder newOrder) {
        Order order = new Order();
        order.setStatus("Pending");

        User user = repo.user.findById(newOrder.getUser()).orElse(null);
        if(user == null)
            return null;
        order.setUser(user);

        List<Line> lines = newOrder.getLines().stream()
                .map(line -> new ViewEntry(line.getProduct(), line.getAmount()))
                .map(ve -> new Entry(repo.product.findById(ve.getKey()).orElse(null), ve.getValue()))
                .filter(e -> e.getKey() != null)
                .map(e -> new Line(0, e.getKey(), e.getKey().getPrice(), e.getValue()))
                .collect(Collectors.toList());

        lines = repo.line.save(lines);
        order.setLines(lines);


        return new ViewOrder(repo.order.save(order));
    }

    @PostMapping("/product")
    ViewProduct createProduct(ViewProduct newProduct) {
        Product product = new Product();
        product.setImage(newProduct.getImage());
        product.setTitle(newProduct.getTitle());
        product.setPrice(newProduct.getPrice());
        product.setTags(newProduct.getTags());

        return new ViewProduct(repo.product.save(product));
    }

    @PutMapping("/user/{id}")
    ViewUser updateUser(@PathVariable Integer id, ViewUser newUser) {
        User user = repo.user.findById(id).orElse(null);
        if(user == null)
            return null;

        user.setAddress(newUser.getAddress());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        return new ViewUser(repo.user.save(user)).removePass();
    }
    @PutMapping("/order/{id}")
    ViewOrder updateOrder(@PathVariable Integer id, ViewOrder newOrder) {
        Order order = repo.order.findById(id).orElse(null);
        if(order == null)
            return null;

        order.setStatus(newOrder.getStatus());
        return new ViewOrder(repo.order.save(order));
    }
    @PutMapping("/line/{id}")
    ViewLine updateLine(@PathVariable Integer id, ViewLine newLine) {
        Line line = repo.line.findById(id).orElse(null);
        if(line == null)
            return null;
        return new ViewLine(line);
    }
    @PutMapping("/product/{id}")
    ViewProduct updateProduct(@PathVariable Integer id, ViewProduct newProduct) {
        Product product = repo.product.findById(id).orElse(null);
        if(product == null)
            return null;

        product.setImage(newProduct.getImage());
        product.setTitle(newProduct.getTitle());
        product.setPrice(newProduct.getPrice());
        product.setTags(newProduct.getTags());
        return new ViewProduct(repo.product.save(product));
    }

    @GetMapping("/user/{id}")
    ViewUser getUser(@PathVariable Integer id) {
        User user = repo.user.findById(id).orElse(null);
        if(user == null)
            return null;
        return new ViewUser(user).removePass();
    }
    @GetMapping("/order/{id}")
    ViewOrder getOrder(@PathVariable Integer id) {
        Order order = repo.order.findById(id).orElse(null);
        if(order == null)
            return null;
        return new ViewOrder(order);
    }
    @GetMapping("/line/{id}")
    ViewLine getLine(@PathVariable Integer id) {
        Line line = repo.line.findById(id).orElse(null);
        if(line == null)
            return null;
        return new ViewLine(line);
    }
    @GetMapping("/product/{id}")
    ViewProduct getProduct(@PathVariable Integer id) {
        Product product = repo.product.findById(id).orElse(null);
        if(product == null)
            return null;
        return new ViewProduct(product);
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Integer id) {
        User user = repo.user.findById(id).orElse(null);
        repo.user.delete(user);
    }
    @DeleteMapping("/order/{id}")
    void deleteOrder(@PathVariable Integer id) {
        Order order = repo.order.findById(id).orElse(null);
        repo.order.delete(order);
    }
    @DeleteMapping("/line/{id}")
    void deleteLine(@PathVariable Integer id) {
        Line line = repo.line.findById(id).orElse(null);
        repo.line.delete(line);
    }
    @DeleteMapping("/product/{id}")
    void deleteProduct(@PathVariable Integer id) {
        Product product = repo.product.findById(id).orElse(null);
        repo.product.delete(product);
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
