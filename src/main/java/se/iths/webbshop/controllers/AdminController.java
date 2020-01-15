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
import se.iths.webbshop.data.Dao;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;

@Controller
@Credentials("admin")
@RequestMapping("/admin/{id}")
public class AdminController {
    @Autowired
    Login login;
    @Autowired
    Dao dao;

    @Credentials
    @GetMapping("/home")
    public String getHome(@PathVariable Integer id, Model model) {
        Search search = login.getSearch();
        model.addAttribute("search", search);
        model.addAttribute("isAdmin", login.isAdmin());

        if(search.getCategory().equals("products")) {
            model.addAttribute("newProduct", new Product());
            model.addAttribute("products", dao.getProducts(search.getTags(), search.getQuery()));
        }
        if(search.getCategory().equals("orders")) {
            model.addAttribute("orders", dao.getOrders(search.getQuery()));
        }

        return "admin/"+search.getCategory();
    }

    @Credentials
    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable Integer id, @PathVariable Integer productId, Model model) {
        model.addAttribute("product", dao.getProduct(productId));
        model.addAttribute("isAdmin", login.isAdmin());

        return "admin/product";
    }

    @Credentials
    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Integer id, @PathVariable Integer orderId, Model model) {
        model.addAttribute("order", dao.getOrder(orderId));
        model.addAttribute("isAdmin", login.isAdmin());


        return "admin/order";
    }

    @Credentials
    @PostMapping("/product")
    public String createProduct(@PathVariable Integer id, Product product) {
        dao.createProduct(product);

        return "redirect:/home";
    }

    @Credentials
    @PostMapping("/order/{orderId}")
    public String changeOrder(@PathVariable Integer id, @PathVariable Integer orderId, Order order) {
        dao.updateOrder(orderId, order);

        return "redirect:/order/"+orderId;
    }

    @Credentials
    @PostMapping("/product/{productId}")
    public String changeProduct(@PathVariable Integer id, @PathVariable Integer productId, Product newProduct) {
        dao.updateProduct(productId, newProduct);

        return "redirect:/product/"+productId;
    }


}
