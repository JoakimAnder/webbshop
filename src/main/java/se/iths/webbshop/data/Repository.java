package se.iths.webbshop.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
public class Repository {
    @Autowired
    public CartRepository cart;
    @Autowired
    public OrderRepository order;
    @Autowired
    public LineRepository line;
    @Autowired
    public ProductRepository product;
    @Autowired
    public UserRepository user;
}
