package se.iths.webbshop.utilities;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.webbshop.entities.User;
import se.iths.webbshop.services.Service;

@Component
@SessionScope
public class Login {

    @Autowired
    private Service service;
    private User user;
    private Cart cart = new Cart();
    private Search search = new Search();

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return isLoggedIn() && user.isAdmin();
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        cart.clear();
    }

    public void attempt(LoginRequest request) {
        user = service.login(request.getUsername(), request.getPassword());
    }

    public int getID() {
        return user == null ? -1 : user.getId();
    }
}
