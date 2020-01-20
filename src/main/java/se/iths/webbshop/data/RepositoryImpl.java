package se.iths.webbshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryImpl implements Repository {
    @Autowired
    private OrderRepository order;
    @Autowired
    private LineRepository line;
    @Autowired
    private ProductRepository product;
    @Autowired
    private UserRepository user;

    @Override
    public OrderRepository order() {
        return order;
    }

    @Override
    public void setOrder(OrderRepository order) {
        this.order = order;
    }

    @Override
    public LineRepository line() {
        return line;
    }

    @Override
    public void setLine(LineRepository line) {
        this.line = line;
    }

    @Override
    public ProductRepository product() {
        return product;
    }

    @Override
    public void setProduct(ProductRepository product) {
        this.product = product;
    }

    @Override
    public UserRepository user() {
        return user;
    }

    @Override
    public void setUser(UserRepository user) {
        this.user = user;
    }
}
