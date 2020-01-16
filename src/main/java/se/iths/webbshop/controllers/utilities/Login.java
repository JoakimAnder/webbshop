package se.iths.webbshop.controllers.utilities;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import se.iths.webbshop.data.Dao;
import se.iths.webbshop.entities.User;

@Component
@SessionScope
public class Login {

    @Autowired
    private Dao dao;
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
        user = dao.login(request.getUsername(), request.getPassword());
    }

    public int getID() {
        return user == null ? -1 : user.getId();
    }
}
