package se.iths.webbshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.webbshop.annotations.Credentials;
import se.iths.webbshop.utilities.Login;
import se.iths.webbshop.utilities.Search;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;

@Controller
@Credentials("admin")
@RequestMapping("/admin/{id}")
public class AdminController {
    @Autowired
    Login login;

    @Credentials
    @GetMapping("/home")
    public String getHome(@PathVariable Integer id, Model model) {
        Search search = login.getSearch();
        model.addAttribute("search", search);
        model.addAttribute("isAdmin", login.isAdmin());

        if(search.getCategory().equals("products")) {
            model.addAttribute("newProduct", new Product());
            model.addAttribute("products", login.getService().getProducts(search.getTags(), search.getQuery()));
        }
        if(search.getCategory().equals("orders")) {
            model.addAttribute("orders", login.getService().getOrders(search.getQuery()));
        }

        return "admin/"+search.getCategory();
    }

    @Credentials
    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable Integer id, @PathVariable Integer productId, Model model) {
        model.addAttribute("product", login.getService().getProduct(productId));
        model.addAttribute("isAdmin", login.isAdmin());

        return "admin/product";
    }

    @Credentials
    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Integer id, @PathVariable Integer orderId, Model model) {
        model.addAttribute("order", login.getService().getOrder(orderId));
        model.addAttribute("isAdmin", login.isAdmin());


        return "admin/order";
    }

    @Credentials
    @PostMapping("/product")
    public String createProduct(@PathVariable Integer id, Product product) {
        login.getService().create(product);

        return "redirect:/home";
    }

    @Credentials
    @PostMapping("/order/{orderId}")
    public String changeOrder(@PathVariable Integer id, @PathVariable Integer orderId, Order order) {
        login.getService().update(orderId, order);

        return "redirect:/order/"+orderId;
    }

    @Credentials
    @PostMapping("/product/{productId}")
    public String changeProduct(@PathVariable Integer id, @PathVariable Integer productId, Product newProduct) {
        login.getService().update(productId, newProduct);

        return "redirect:/product/"+productId;
    }


}
