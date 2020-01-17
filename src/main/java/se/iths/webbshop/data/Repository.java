package se.iths.webbshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
public class Repository {
    @Autowired
    private OrderRepository order;
    @Autowired
    private LineRepository line;
    @Autowired
    private ProductRepository product;
    @Autowired
    private UserRepository user;

    public OrderRepository order() {
        return order;
    }

    public void setOrder(OrderRepository order) {
        this.order = order;
    }

    public LineRepository line() {
        return line;
    }

    public void setLine(LineRepository line) {
        this.line = line;
    }

    public ProductRepository product() {
        return product;
    }

    public void setProduct(ProductRepository product) {
        this.product = product;
    }

    public UserRepository user() {
        return user;
    }

    public void setUser(UserRepository user) {
        this.user = user;
    }
}
