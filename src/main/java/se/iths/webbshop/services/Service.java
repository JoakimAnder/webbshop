package se.iths.webbshop.services;

import se.iths.webbshop.utilities.Entry;
import se.iths.webbshop.views.*;
import se.iths.webbshop.entities.Line;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;
import se.iths.webbshop.entities.User;

import java.util.List;

public interface Service {
    User login(String username, String password);

    Entry convert(ViewEntry viewEntry);

    Line convert(ViewLine viewLine);

    Order convert(ViewOrder viewOrder);

    Product convert(ViewProduct viewProduct);

    User convert(ViewUser viewUser);

    List<User> getUsers();

    List<Order> getOrders();

    List<Order> getOrders(String query);

    List<Line> getLines();

    List<Product> getProducts();

    List<Product> getProducts(String tags, String query);

    User getUser(Integer id);

    Order getOrder(Integer id);

    Line getLine(Integer id);

    Product getProduct(Integer id);

    User create(User newUser);

    Order create(Order newOrder);

    Product create(Product newProduct);

    User update(Integer id, User newUser);

    Order update(Integer id, Order newOrder);

    Line update(Integer id, Line newLine);

    Product update(Integer id, Product newProduct);

    void deleteUser(Integer id);

    void deleteOrder(Integer id);

    void deleteLine(Integer id);

    void deleteProduct(Integer id);

}
