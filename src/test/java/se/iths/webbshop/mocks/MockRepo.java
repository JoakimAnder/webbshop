package se.iths.webbshop.mocks;


import se.iths.webbshop.data.*;
import se.iths.webbshop.entities.Line;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;
import se.iths.webbshop.entities.User;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MockRepo implements Repository {

    OrderRepository orderRepo;
    LineRepository lineRepo;
    ProductRepository productRepo;
    UserRepository userRepo;

    List<String> calls = new ArrayList<>();

    public MockRepo() {
        orderRepo = new MockOrderRepo(c -> calls.add("Order "+c));
        lineRepo = new MockLineRepo(c -> calls.add("Line "+c));
        productRepo = new MockProductRepo(c -> calls.add("Product "+c));
        userRepo = new MockUserRepo(c -> calls.add("User "+c));
    }

    public List<String> getCalls() {
        return calls;
    }

    @Override
    public OrderRepository order() {
        return orderRepo;
    }

    @Override
    public void setOrder(OrderRepository order) {

    }

    @Override
    public LineRepository line() {
        return lineRepo;
    }

    @Override
    public void setLine(LineRepository line) {

    }

    @Override
    public ProductRepository product() {
        return productRepo;
    }

    @Override
    public void setProduct(ProductRepository product) {

    }

    @Override
    public UserRepository user() {
        return userRepo;
    }

    @Override
    public void setUser(UserRepository user) {

    }

    private static class MockOrderRepo implements OrderRepository {

        Consumer<String> call;
        List<Order> orders = new ArrayList<>();

        public MockOrderRepo(Consumer<String> call) {
            this.call = call;
        }

        @Override
        public List<Order> findAllByIdIn(Integer[] id) {
            call.accept("findAllByIdIn");
            return null;
        }

        @Override
        public <S extends Order> S save(S s) {
            call.accept("save");
            orders = orders.stream().filter(o -> o.getId() != s.getId()).collect(Collectors.toList());
            orders.add(s);
            return s;
        }

        @Override
        public <S extends Order> Iterable<S> saveAll(Iterable<S> iterable) {
            call.accept("saveAll");
            return null;
        }

        @Override
        public Optional<Order> findById(Integer id) {
            call.accept("findById");

            return orders.stream().filter(o -> o.getId() == id).findFirst();
        }

        @Override
        public boolean existsById(Integer integer) {
            call.accept("existsById");
            return false;
        }

        @Override
        public List<Order> findAll() {
            call.accept("findAll");
            return orders;
        }

        @Override
        public Iterable<Order> findAllById(Iterable<Integer> iterable) {
            call.accept("findAllById");
            return null;
        }

        @Override
        public long count() {
            call.accept("count");
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {
            call.accept("deleteById");

        }

        @Override
        public void delete(Order order) {
            call.accept("delete");

        }

        @Override
        public void deleteAll(Iterable<? extends Order> iterable) {
            call.accept("deleteAll");

        }

        @Override
        public void deleteAll() {
            call.accept("deleteAll");

        }
    }
    private static class MockLineRepo implements LineRepository {
        Consumer<String> call;

        List<Line> lines = new ArrayList<>();

        public MockLineRepo(Consumer<String> call) {
            this.call = call;
        }

        @Override
        public <S extends Line> S save(S s) {
            call.accept("save");
            lines = lines.stream().filter(o -> o.getId() != s.getId()).collect(Collectors.toList());
            lines.add(s);
            return s;
        }

        @Override
        public <S extends Line> Iterable<S> saveAll(Iterable<S> iterable) {
            call.accept("saveAll");
            return null;
        }

        @Override
        public Optional<Line> findById(Integer id) {
            call.accept("findById");
            return lines.stream().filter(o -> o.getId() == id).findFirst();
        }

        @Override
        public boolean existsById(Integer integer) {
            call.accept("existsById");
            return false;
        }

        @Override
        public List<Line> findAll() {
            call.accept("findAll");
            return lines;
        }

        @Override
        public Iterable<Line> findAllById(Iterable<Integer> iterable) {
            call.accept("findAllById");
            return null;
        }

        @Override
        public long count() {
            call.accept("count");
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {
            call.accept("deleteById");

        }

        @Override
        public void delete(Line line) {
            call.accept("delete");

        }

        @Override
        public void deleteAll(Iterable<? extends Line> iterable) {
            call.accept("deleteAll");

        }

        @Override
        public void deleteAll() {
            call.accept("deleteAll");

        }

        @Override
        public List<Line> save(Iterable<Line> lines) {
            call.accept("save");
            List<Line> lineArr = new ArrayList<>();

            for(Line line : lines) {
                this.lines.add(line);
                lineArr.add(line);
            }
            return lineArr;
        }
    }
    private static class MockProductRepo implements ProductRepository {
        Consumer<String> call;
        List<Product> products = new ArrayList<>();

        public MockProductRepo(Consumer<String> call) {
            this.call = call;
        }

        @Override
        public <S extends Product> S save(S s) {
            call.accept("save");
            products = products.stream().filter(o -> o.getId() != s.getId()).collect(Collectors.toList());
            products.add(s);
            return s;
        }

        @Override
        public <S extends Product> Iterable<S> saveAll(Iterable<S> iterable) {
            call.accept("saveAll");
            return null;
        }

        @Override
        public Optional<Product> findById(Integer id) {
            call.accept("findById");
            return products.stream().filter(o -> o.getId() == id).findFirst();
        }

        @Override
        public boolean existsById(Integer integer) {
            call.accept("existsById");
            return false;
        }

        @Override
        public List<Product> findAll() {
            call.accept("findAll");
            return products;
        }

        @Override
        public Iterable<Product> findAllById(Iterable<Integer> iterable) {
            call.accept("findAllById");
            return null;
        }

        @Override
        public long count() {
            call.accept("count");
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {
            call.accept("deleteById");
        }

        @Override
        public void delete(Product product) {
            call.accept("delete");
        }

        @Override
        public void deleteAll(Iterable<? extends Product> iterable) {
            call.accept("deleteAll");
        }

        @Override
        public void deleteAll() {
            call.accept("deleteAll");
        }
    }
    private static class MockUserRepo implements UserRepository {
        Consumer<String> call;
        List<User> users = new ArrayList<>();

        public MockUserRepo(Consumer<String> call) {
            this.call = call;
        }
        @Override
        public <S extends User> S save(S s) {
            call.accept("save");
            users = users.stream().filter(o -> o.getId() != s.getId()).collect(Collectors.toList());
            users.add(s);
            return s;
        }

        @Override
        public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
            call.accept("saveAll");
            return null;
        }

        @Override
        public Optional<User> findById(Integer id) {
            call.accept("findById");
            return users.stream().filter(o -> o.getId() == id).findFirst();
        }

        @Override
        public boolean existsById(Integer integer) {
            call.accept("existsById");
            return false;
        }

        @Override
        public List<User> findAll() {
            call.accept("findAll");
            return users;
        }

        @Override
        public Iterable<User> findAllById(Iterable<Integer> iterable) {
            call.accept("findAllById");
            return null;
        }

        @Override
        public long count() {
            call.accept("count");
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {
            call.accept("deleteById");

        }

        @Override
        public void delete(User user) {
            call.accept("delete");

        }

        @Override
        public void deleteAll(Iterable<? extends User> iterable) {
            call.accept("deleteAll");

        }

        @Override
        public void deleteAll() {
            call.accept("deleteAll");

        }

        @Override
        public User findUserByUsername(String username) {
            call.accept("findUserByUsername");
            return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        }
    }
}
