package se.iths.webbshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import se.iths.webbshop.utilities.Entry;
import se.iths.webbshop.views.*;
import se.iths.webbshop.data.Repository;
import se.iths.webbshop.entities.Line;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;
import se.iths.webbshop.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Autowired
    Repository repo;

    @Override
    public User login(String username, String password) {
        User user = repo.user().findUserByUsername(username);
        if(user != null) {
            if(!user.getPassword().equals(password))
                user = null;
        }
        return user;
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

    // region Conversions

    @Override
    public Entry convert(ViewEntry viewEntry) {
        Product key = repo.product().findById(viewEntry.getKey()).orElse(null);
        int value = viewEntry.getValue();

        Entry entry = new Entry();
        entry.setKey(key);
        entry.setValue(value);

        return entry;
    }
    @Override
    public Line convert(ViewLine viewLine) {
        int id = viewLine.getId();
        Product product = repo.product().findById(viewLine.getProduct()).orElse(null);
        int amount = viewLine.getAmount();
        double price = viewLine.getPrice();

        Line line = new Line();
        line.setProduct(product);
        line.setAmount(amount);
        line.setPrice(price);

        return line;
    }
    @Override
    public Order convert(ViewOrder viewOrder) {
        int id = viewOrder.getId();
        String status = viewOrder.getStatus();
        List<Line> lines = viewOrder.getLines().stream().map(this::convert).collect(Collectors.toList());
        User user = repo.user().findById(viewOrder.getUser()).orElse(null);

        Order order = new Order();
        order.setStatus(status);
        order.setLines(lines);
        order.setUser(user);

        return order;
    }
    @Override
    public Product convert(ViewProduct viewProduct) {
        int id = viewProduct.getId();
        List<String> tags = viewProduct.getTags();
        double price = viewProduct.getPrice();
        String title = viewProduct.getTitle();
        String image = viewProduct.getImage();

        Product product = new Product();
        product.setTags(tags);
        product.setPrice(price);
        product.setTitle(title);
        product.setImage(image);

        return product;
    }
    @Override
    public User convert(ViewUser viewUser) {
        int id = viewUser.getId();
        String address = viewUser.getAddress();
        String password = viewUser.getPassword();
        String username = viewUser.getUsername();
        boolean admin = viewUser.isAdmin();

        User user = new User();
        user.setAddress(address);
        user.setPassword(password);
        user.setUsername(username);
        user.setAdmin(admin);

        return user;
    }


    // endregion

    // region Getters
    @Override
    public List<User> getUsers() {
        return repo.user().findAll();
    }
    @Override
    public List<Order> getOrders() {
        return repo.order().findAll();
    }

    @Override
    public List<Order> getOrders(String query) {
        if(query.trim().isEmpty())
            return repo.order().findAll();

        Integer[] ids = getNumbers(query.split(","))
                .toArray(new Integer[0]);

        return repo.order().findAllByIdIn(ids);
    }

    @Override
    public List<Line> getLines() {
        return repo.line().findAll();
    }
    @Override
    public List<Product> getProducts() {
        return repo.product().findAll();
    }

    @Override
    public List<Product> getProducts(String tags, String query) {
        if(tags.trim().isEmpty() && query.trim().isEmpty())
            return repo.product().findAll();

        List<String> tagsList = Arrays.stream(tags.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        List<Integer> ids = getNumbers(query.split(","));
        List<String> titles = Arrays.stream(query.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toList());


        return repo.product().findAll().stream()
                .filter(p -> tagsList.isEmpty() || anyLike(p.getTags(), tagsList))
                .filter(p -> (ids.isEmpty() && titles.isEmpty()) || ids.contains(p.getId()) || titles.stream().anyMatch(s -> p.getTitle().toLowerCase().contains(s.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(Integer id) {
        return repo.user().findById(id).orElse(null);
    }
    @Override
    public Order getOrder(Integer id) {
        return repo.order().findById(id).orElse(null);
    }
    @Override
    public Line getLine(Integer id) {
        return repo.line().findById(id).orElse(null);
    }
    @Override
    public Product getProduct(Integer id) {
       return repo.product().findById(id).orElse(null);
    }
    // endregion

    // region Creates

    @Override
    public User create(User newUser) {
        String password = newUser.getPassword();
        String username = newUser.getUsername();
        String address = newUser.getAddress();
        boolean isAdmin = newUser.isAdmin();

        password = password == null ? "" : password;
        username = username == null ? "" : username;
        address = address == null ? "" : address;

        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setAddress(address);
        user.setAdmin(isAdmin);

        return repo.user().save(user);
    }
    @Override
    public Order create(Order newOrder) {
        User user = newOrder.getUser();
        List<Line> lines = newOrder.getLines();
        String status = newOrder.getStatus();

        if(user == null)
            throw new RuntimeException("Order needs an User");
        int userID = user.getId();
        user = repo.user().findById(userID).orElseThrow(() -> new RuntimeException("User with id '"+userID+"' doesn't exist"));

        lines = lines.stream().map(this::createLine).collect(Collectors.toList());

        status = (status == null || status.isEmpty()) ? "Pending" : status;

        Order order = new Order();
        order.setUser(user);
        order.setLines(lines);
        order.setStatus(status);

        return repo.order().save(order);
    }
    private Line createLine(Line newLine) {
        double price = newLine.getPrice();
        int amount = newLine.getAmount();
        Product product = newLine.getProduct();

        if(price < 0) throw new RuntimeException("Price can not be negative");
        if(amount < 1) throw new RuntimeException("Amount can not be negative or zero");
        if(product == null)
            throw new RuntimeException("Line needs a Product");
        int productId = product.getId();
        product = repo.product().findById(productId).orElseThrow(() -> new RuntimeException(String.format("Product with id '%d' doesn't exist", productId)));

        Line line = new Line();
        line.setPrice(price);
        line.setAmount(amount);
        line.setProduct(product);

        return repo.line().save(line);
    }
    @Override
    public Product create(Product newProduct) {
        String image = newProduct.getImage();
        String title = newProduct.getTitle();
        double price = newProduct.getPrice();
        List<String> tags = newProduct.getTags();

        image = image == null ? "" : image;
        title = title == null ? "" : title;
        if(price < 0) throw new RuntimeException("Price can not be negative");
        tags = tags == null ? new ArrayList<>() : tags.stream().filter(t -> !(t == null || t.trim().isEmpty())).map(String::toLowerCase).collect(Collectors.toList());

        Product product = new Product();
        product.setImage(image);
        product.setTitle(title);
        product.setPrice(price);
        product.setTags(tags);

        return repo.product().save(product);
    }

    // endregion

    // region Updates

    @Override
    public User update(Integer id, User newUser) {
        User user = getUser(id);
        String address = newUser.getAddress();
        String username = newUser.getUsername();
        String password = newUser.getPassword();

        if(user == null) throw new RuntimeException(String.format("User with id '%d' doesn't exist", id));
        address = (address == null || address.trim().isEmpty()) ? user.getAddress() : address;
        username = (username == null || username.trim().isEmpty()) ? user.getUsername() : username;
        password = (password == null || password.trim().isEmpty()) ? user.getPassword() : password;


        user.setAddress(address);
        user.setUsername(username);
        user.setPassword(password);

        return repo.user().save(user);
    }
    @Override
    public Order update(Integer id, Order newOrder) {
        Order order = getOrder(id);
        String status = newOrder.getStatus();
        User user = newOrder.getUser();
        List<Line> lines = newOrder.getLines();

        if(order == null) throw new RuntimeException(String.format("Order with id '%d' doesn't exist", id));
        status = (status == null || status.trim().isEmpty()) ? order.getStatus() : status;
        if(user == null)
            user = order.getUser();
        else {
            User xUser = getUser(user.getId());
            user = (xUser == null) ? order.getUser() : xUser;
        }
        if(lines == null || lines.isEmpty() || lines.equals(order.getLines()))
            lines = order.getLines();
        else {
            order.getLines().stream().map(Line::getId).forEach(this::deleteLine);
            lines = lines.stream().map(this::createLine).collect(Collectors.toList());
        }

        order.setLines(lines);
        order.setUser(user);
        order.setStatus(status);

        return repo.order().save(order);
    }
    @Override
    public Line update(Integer id, Line newLine) {
        Line line = getLine(id);
        int amount = newLine.getAmount();
        double price = newLine.getPrice();
        Product product = newLine.getProduct();

        if(line == null) throw new RuntimeException(String.format("Line with id '%d' doesn't exist", id));
        amount = (amount < 1) ? line.getAmount() : amount;
        price = (price < 0) ? line.getPrice() : price;
        if(product == null)
            product = line.getProduct();
        else {
            Product xProduct = getProduct(product.getId());
            product = (xProduct == null) ? line.getProduct() : xProduct;
        }

        line.setProduct(product);
        line.setAmount(amount);
        line.setPrice(price);

        return repo.line().save(line);
    }
    @Override
    public Product update(Integer id, Product newProduct) {
        Product product = getProduct(id);
        String image = newProduct.getImage();
        String title = newProduct.getTitle();
        double price = newProduct.getPrice();
        List<String> tags = newProduct.getTags();

        if(product == null) throw new RuntimeException(String.format("Product with id '%d' doesn't exist", id));
        image = (image == null || image.trim().isEmpty()) ? product.getImage() : image;
        title = (title == null || title.trim().isEmpty()) ? product.getTitle() : title;
        price = (price < 0) ? product.getPrice() : price;
        tags = (tags == null || tags.isEmpty()) ? product.getTags() : tags.stream().filter(t -> !(t == null || t.trim().isEmpty())).map(String::toLowerCase).collect(Collectors.toList());

        product.setImage(image);
        product.setTitle(title);
        product.setPrice(price);
        product.setTags(tags);
        return repo.product().save(product);
    }

    //endregion

    // region Deletes

    @Override
    public void deleteUser(Integer id) {
        User user = getUser(id);
        if(user != null)
            repo.user().delete(user);
    }
    @Override
    public void deleteOrder(Integer id) {
        Order order = getOrder(id);
        if(order != null)
            repo.order().delete(order);
    }
    @Override
    public void deleteLine(Integer id) {
        Line line = getLine(id);
        if(line != null)
            repo.line().delete(line);
    }
    @Override
    public void deleteProduct(Integer id) {
        Product product = getProduct(id);
        if(product != null)
            repo.product().delete(product);
    }
    // endregion
}
