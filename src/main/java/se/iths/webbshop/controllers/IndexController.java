package se.iths.webbshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.iths.webbshop.utilities.Login;
import se.iths.webbshop.utilities.LoginRequest;
import se.iths.webbshop.utilities.Search;
import se.iths.webbshop.entities.User;

@Controller
@RequestMapping("")
public class IndexController {
    @Autowired
    Login login;

    private String forward(String URI) {
        String level = login.isAdmin() ? "admin" : "customer";
        return forward(URI, level);
    }
    private String forward(String URI, String level) {
        if(!login.isLoggedIn())
            return "redirect:/login";
        return String.format("forward:/%s/%d%s", level, login.getID(), URI);
    }

    @GetMapping("/**")
    String wildcard() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    ModelAndView getHome(Model model) {
        if(!login.isLoggedIn())
            return new ModelAndView("redirect:/login");
        return new ModelAndView(forward("/home"));
    }

    @PostMapping("/home")
    String postHome(Search search) {
        login.setSearch(search);

        return "redirect:/home";
    }

    @GetMapping("/login")
    String getLogin(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    String login(LoginRequest request) {
        login.attempt(request);
        return "redirect:/home";
    }

    @GetMapping("/register")
    String getRegister(Model model) {
        model.addAttribute("newUser", new User());
        return "register";
    }

    @PostMapping("/register")
    String register(User newUser) {
        User user = login.getService().create(newUser);
        return login(new LoginRequest(user.getUsername(), user.getPassword()));
    }

    @GetMapping("/logout")
    String logout() {
        login.logout();
        return "redirect:/login";
    }

    @GetMapping("/error")
    String getError(String type, Integer status, String message) {
        return String.format("forward:/warning?type=%s&status=%d&message=%s", type, status, message);
    }

    @GetMapping("/warning")
    String getWarning(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false, value = "message") String message,
            Model model
    ) {
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "error";
    }

    // region forwards

    @GetMapping("/product/{id}")
    ModelAndView getProduct(@PathVariable Integer id) {
        return new ModelAndView(forward("/product/"+id));
    }
    @GetMapping("/order/{id}")
    String getOrder(@PathVariable Integer id) {
        return forward("/order/"+id);
    }
    @GetMapping("/cart")
    String getCart() {
        return forward("/cart", "customer");
    }

    @PostMapping("/product")
    String postProduct() {
        return forward("/product");
    }
    @PostMapping("/order/{id}")
    String postOrder(@PathVariable Integer id) {
        return forward("/order/"+id, "admin");
    }
    @PostMapping("/cart")
    String postCart() {
        return forward("/cart", "customer");
    }
    @PostMapping("/product/{id}")
    String postProduct(@PathVariable Integer id) {
        return forward("/product/"+id);
    }
    @PostMapping("/order")
    String postOrder() {
        return forward("/order", "customer");
    }

    // endregion


    @GetMapping("/test")
    String getTest(@RequestParam(required = false) String text, Model model) {
        model.addAttribute("text", text);
        return "test";
    }
}
