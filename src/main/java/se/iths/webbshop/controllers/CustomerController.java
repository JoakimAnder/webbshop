package se.iths.webbshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.webbshop.annotations.Credentials;
import se.iths.webbshop.controllers.utilities.Login;
import se.iths.webbshop.controllers.utilities.Search;
import se.iths.webbshop.controllers.utilities.Views.ViewEntry;
import se.iths.webbshop.data.Dao;
import se.iths.webbshop.controllers.utilities.Cart;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;
import se.iths.webbshop.entities.User;

@Controller
@Credentials("customer")
@RequestMapping("/customer/{id}")
public class CustomerController {

    @Autowired
    Login login;
    @Autowired
    Dao dao;

    @Credentials
    @GetMapping("/home")
    public String getHome(@PathVariable Integer id, Model model) {
        Cart cart = login.getCart();
        Search search = login.getSearch();

        model.addAttribute("products", dao.getProducts(search.getTags(), search.getQuery()));
        model.addAttribute("newEntry", new ViewEntry());
        model.addAttribute("isAdmin", login.isAdmin());
        model.addAttribute("search", search);
        model.addAttribute("cart", cart);

        return "customer/home";
    }

    @Credentials
    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable Integer id, @PathVariable Integer productId, Model model) {
        model.addAttribute("product", dao.getProduct(productId));
        model.addAttribute("entry", new ViewEntry());
        model.addAttribute("isAdmin", login.isAdmin());
        model.addAttribute("cart", login.getCart());


        return "customer/product";
    }

    @Credentials
    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Integer id, @PathVariable String orderId, Model model) {
        model.addAttribute("cart", login.getCart());
        model.addAttribute("order", dao.getOrder(Integer.parseInt(orderId)));
        model.addAttribute("isAdmin", login.isAdmin());


        return "customer/order";
    }

    @Credentials
    @GetMapping("/cart")
    public String getCart(@PathVariable Integer id, Model model) {
        model.addAttribute("cart", login.getCart());
        model.addAttribute("newEntry", new ViewEntry());
        model.addAttribute("isAdmin", login.isAdmin());


        return "customer/cart";
    }

    @Credentials
    @PostMapping("/product")
    public String addProduct(@PathVariable Integer id, ViewEntry entry) {
        Cart cart = login.getCart();
        Product product = dao.getProduct(entry.getKey());

        cart.put(product, 1+cart.findAmount(product));
        return "redirect:/home";
    }

    @Credentials
    @PostMapping("/cart")
    public String changeCart(@PathVariable Integer id, ViewEntry newEntry) {
        Cart cart = login.getCart();
        System.out.println(newEntry.getKey());
        System.out.println(newEntry.getValue());
        Product product = dao.getProduct(newEntry.getKey());

        cart.put(product, newEntry.getValue());
        return "redirect:/cart";
    }

    @Credentials
    @PostMapping("/order")
    public String createOrder(@PathVariable Integer id) {
        User user = dao.getUser(id);
        Cart cart = login.getCart();

        Order order = dao.createOrder(new Order(0, user, cart.convertToOrderLines()));
        cart.clear();

        return "redirect:/order/"+order.getId();
    }

    @Credentials
    @PostMapping("/product/{productId}")
    public String addProduct(@PathVariable Integer id, @PathVariable Integer productId, ViewEntry entry) {
        Cart cart = login.getCart();
        Product product = dao.getProduct(productId);

        cart.put(product, entry.getValue());
        return "redirect:/product/"+productId;
    }

}
