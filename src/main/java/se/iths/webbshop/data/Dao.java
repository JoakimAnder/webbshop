package se.iths.webbshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.webbshop.controllers.utilities.Cart;
import se.iths.webbshop.entities.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScope
public class Dao {
    @Autowired
    Repository repo;

    public User createUser(User user) {
        return repo.user.save(user);
    }
    public Order createOrder(Order order) {
        order.getLines().forEach(this::createLine);
        return repo.order.save(order);
    }
    public Product createProduct(Product product) {
        return repo.product.save(product);
    }
    public Line createLine(Line line) {
        return repo.line.save(line);
    }

    public Order updateOrder(int id, Order order) {
        Order oldOrder = repo.order.findById(id).orElse(null);
        if(oldOrder == null)
            return order;

        oldOrder.setStatus(order.getStatus());
        return repo.order.save(oldOrder);
    }

    public Product updateProduct(int id, Product product) {
        Product oldProduct = repo.product.findById(id).orElse(null);
        if(oldProduct == null)
            return product;

        oldProduct.setImage(product.getImage());
        oldProduct.setTitle(product.getTitle());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setTags(product.getTags());
        return repo.product.save(oldProduct);
    }

    public User login(String username, String password) {
        User user = repo.user.findUserByUsername(username);
        if(user != null) {
            if(!user.getPassword().equals(password))
                user = null;
        }
        return user;
    }

    public User getUser(int id) {
        return repo.user.findById(id).orElse(null);
    }
    public Order getOrder(int id) {
        return repo.order.findById(id).orElse(null);
    }
    public Product getProduct(int id) {
        return repo.product.findById(id).orElse(null);
    }
    public Line getLine(int id) {
        return repo.line.findById(id).orElse(null);
    }

    public List<Order> getOrders(String query) {
        if(query.trim().isEmpty())
            return repo.order.findAll();

        Integer[] ids = getNumbers(query.split(","))
                .toArray(new Integer[0]);

        return repo.order.findAllByIdIn(ids);
    }

    public List<Product> getProducts(String tags, String query) {
        if(tags.trim().isEmpty() && query.trim().isEmpty())
            return repo.product.findAll();

        List<String> tagsList = Arrays.stream(tags.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        List<Integer> ids = getNumbers(query.split(","));
        List<String> titles = Arrays.stream(query.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toList());


        return repo.product.findAll().stream()
                .filter(p -> tagsList.isEmpty() || anyLike(p.getTags(), tagsList))
                .filter(p -> (ids.isEmpty() && titles.isEmpty()) || ids.contains(p.getId()) || titles.stream().anyMatch(s -> p.getTitle().toLowerCase().contains(s.toLowerCase())))
                .collect(Collectors.toList());
    }

    private List<Integer> getNumbers(String[] arr) {
        return Arrays.stream(arr)
                .map(String::trim)
                .filter(s -> {
                    try {
                        Integer.parseInt(s);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private boolean anyLike(List<String> target, List<String> notTarget) {
        target = target.stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());
        notTarget = notTarget.stream().map(String::trim).map(String::toLowerCase).collect(Collectors.toList());

        for(String s : target) {
            if(notTarget.contains(s))
                return true;
        }
        return false;
    }
}
