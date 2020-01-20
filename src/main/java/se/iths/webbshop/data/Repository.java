package se.iths.webbshop.data;

public interface Repository {
    OrderRepository order();

    void setOrder(OrderRepository order);

    LineRepository line();

    void setLine(LineRepository line);

    ProductRepository product();

    void setProduct(ProductRepository product);

    UserRepository user();

    void setUser(UserRepository user);
}
