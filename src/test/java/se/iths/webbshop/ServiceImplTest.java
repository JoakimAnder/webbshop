package se.iths.webbshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.webbshop.data.Repository;
import se.iths.webbshop.entities.Line;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.Product;
import se.iths.webbshop.entities.User;
import se.iths.webbshop.mocks.MockRepo;
import se.iths.webbshop.services.Service;
import se.iths.webbshop.services.ServiceImpl;
import se.iths.webbshop.utilities.Entry;
import se.iths.webbshop.views.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


class ServiceImplTest {
    MockRepo repo;
    Service service;

    // region Deep Equals

    boolean isDeepEquals(User x, User y) {
        if(x == null && y == null)
            return true;
        if(x == null || y == null)
            return false;
        return
                x.getId() == y.getId()
                && x.getPassword().equals(y.getPassword())
                && x.getUsername().equals(y.getUsername())
                && x.getAddress().equals(y.getAddress());
    }
    boolean isDeepEquals(Entry x, Entry y) {
        if(x == null && y == null)
            return true;
        if(x == null || y == null)
            return false;
        return
                x.getValue() == y.getValue()
                && isDeepEquals(x.getKey(), x.getKey());
    }
    boolean isDeepEquals(Order x, Order y) {
        if(x == null && y == null)
            return true;
        if(x == null || y == null)
            return false;
        return
                x.getId() == y.getId()
                && x.getStatus().equals(y.getStatus())
                && isDeepEquals(x.getUser(), y.getUser())
                && isDeepEquals(x.getLines(), y.getLines());
    }
    boolean isDeepEquals(Product x, Product y) {
        if(x == null && y == null)
            return true;
        if(x == null || y == null)
            return false;
        return
                x.getId() == y.getId()
                && x.getTitle().equals(y.getTitle())
                && x.getTags().equals(y.getTags())
                && x.getPrice() == y.getPrice()
                && x.getImage().equals(y.getImage());
    }
    boolean isDeepEquals(Line x, Line y) {
        if(x == null && y == null)
            return true;
        if(x == null || y == null)
            return false;
        return
                x.getId() == y.getId()
                && isDeepEquals(x.getProduct(), y.getProduct())
                && x.getAmount() == y.getAmount()
                && x.getPrice() == y.getPrice();
    }

    boolean isDeepEquals(List<Line> x, List<Line> y) {
        if(x == null && y == null)
            return true;
        if(x == null || y == null)
            return false;
        if(x.size() != y.size())
            return false;

        Comparator<Line> comparator = Comparator.comparingInt(Line::getId);

        x.sort(comparator);
        y.sort(comparator);

        for(int i = 0 ; i < x.size() ; i++) {
            if(!isDeepEquals(x.get(i), y.get(i)))
                return false;
        }

        return true;
    }

    // endregion

    @BeforeEach
    void setup() {
        repo = new MockRepo();
        service = new ServiceImpl(repo);

        User user = new User(1, "user", "pass", "add");
        Product product = new Product(1,  "title", "image", 10, Arrays.asList("t1", "t2", "t3"));
        Line line = new Line(1, product, 10, 5);
        Order order = new Order(1, user, Arrays.asList(line));

        repo.user().save(user);
        repo.product().save(product);
        repo.line().save(line);
        repo.order().save(order);

        repo.getCalls().clear();
    }

    // region Login

    @Test
    void login() {
        User expectedUser = repo.user().findById(1).get();
        User user = service.login("user", "pass");

        Assertions.assertTrue(isDeepEquals(expectedUser, user));
    }

    @Test
    void loginWithNullUsername() {
        User user = service.login(null, "pass");

        Assertions.assertNull(user);
        Assertions.assertEquals(0, repo.getCalls().size());
    }

    @Test
    void loginWithNullPassword() {
        User user = service.login("user", null);

        Assertions.assertNull(user);
        Assertions.assertEquals(0, repo.getCalls().size());
    }

    @Test
    void loginWithWrongUsername() {
        User user = service.login("", "pass");
        Assertions.assertNull(user);
    }

    @Test
    void loginWithWrongPassword() {
        User user = service.login("user", "");
        Assertions.assertNull(user);
    }

    // endregion

    // region Convert
    @Test
    void convertNull() {
        ViewUser vu = null;
        ViewProduct vp = null;
        ViewOrder vo = null;
        ViewLine vl = null;
        ViewEntry ve = null;

        Assertions.assertNull(service.convert(vu));
        Assertions.assertNull(service.convert(vp));
        Assertions.assertNull(service.convert(vo));
        Assertions.assertNull(service.convert(vl));
        Assertions.assertNull(service.convert(ve));
    }

    @Test
    void convertWithNonextstingAttribute() {
        Line ol = new Line(0, repo.product().findById(1).get(), 10.0, 1);
        Order o = new Order(0, new User(), Arrays.asList(ol));
        Line l = new Line(0, new Product(), 50.0, 5);
        Entry e = new Entry(new Product(), 5);

        ViewOrder vo = new ViewOrder(o);
        ViewLine vl = new ViewLine(l);
        ViewEntry ve = new ViewEntry(e);

        o.setUser(null);
        l.setProduct(null);
        e.setKey(null);

        Order ao = service.convert(vo);
        Line al = service.convert(vl);
        Entry ae = service.convert(ve);

        Assertions.assertTrue(isDeepEquals(o, ao), String.format("\nExpected: %s\nActual: %s", o, ao));
        Assertions.assertTrue(isDeepEquals(l, al), String.format("\nExpected: %s\nActual: %s", l, al));
        Assertions.assertTrue(isDeepEquals(e, ae), String.format("\nExpected: %s\nActual: %s", e, ae));
    }

    @Test
    void convertViewUser() {
        User expected = repo.user().findById(1).get();

        ViewUser view = new ViewUser();
        view.setId(expected.getId());
        view.setAddress(expected.getAddress());
        view.setAdmin(expected.isAdmin());
        view.setPassword(expected.getPassword());
        view.setUsername(expected.getUsername());

        User actual = service.convert(view);

        Assertions.assertTrue(isDeepEquals(expected, actual), String.format("\nExpected: %s\nActual: %s", expected, actual));
    }

    @Test
    void convertViewProduct() {
        Product expected = repo.product().findById(1).get();

        ViewProduct view = new ViewProduct();
        view.setId(expected.getId());
        view.setImage(expected.getImage());
        view.setPrice(expected.getPrice());
        view.setTags(expected.getTags());
        view.setTitle(expected.getTitle());

        Product actual = service.convert(view);

        Assertions.assertTrue(isDeepEquals(expected, actual), String.format("\nExpected: %s\nActual: %s", expected, actual));
    }

    @Test
    void convertViewOrder() {
        Order expected = repo.order().findById(1).get();
        List<ViewLine> lines = expected.getLines().stream().map(ViewLine::new).collect(Collectors.toList());

        ViewOrder view = new ViewOrder();
        view.setId(expected.getId());
        view.setStatus(expected.getStatus());
        view.setLines(lines);
        view.setUser(expected.getUser().getId());

        Order actual = service.convert(view);

        Assertions.assertTrue(isDeepEquals(expected, actual), String.format("\nExpected: %s\nActual: %s", expected, actual));
    }

    @Test
    void convertViewEntry() {
        Entry expected = new Entry(repo.product().findById(1).get(), 1);

        ViewEntry view = new ViewEntry();
        view.setKey(expected.getKey().getId());
        view.setValue(expected.getValue());

        Entry actual = service.convert(view);

        Assertions.assertTrue(isDeepEquals(expected, actual), String.format("\nExpected: %s\nActual: %s", expected, actual));
    }

    @Test
    void convertViewLine() {
        Line expected = repo.line().findById(1).get();

        ViewLine view = new ViewLine();
        view.setId(expected.getId());
        view.setAmount(expected.getAmount());
        view.setPrice(expected.getPrice());
        view.setProduct(expected.getProduct().getId());

        Line actual = service.convert(view);

        Assertions.assertTrue(isDeepEquals(expected, actual), String.format("\nExpected: %s\nActual: %s", expected, actual));
    }

    // endregion

    // region Get
    @Test
    void getOrdersByQuery() {
        String emptyQuery = "";
        String numberQuery = "1";
        String severalNumberQuery = "3,2,1";
        String severalNumberAndTextsQuery = "q,u, 1 ,    e  ,r,y";
        String nonExistantQuery = "query";

        List<Order> allOrders = service.getOrders(emptyQuery);
        List<Order> idOrders = service.getOrders(numberQuery);
        List<Order> idsOrders = service.getOrders(severalNumberQuery);
        List<Order> textsOrders = service.getOrders(severalNumberAndTextsQuery);
        List<Order> emptyOrders = service.getOrders(nonExistantQuery);

        Assertions.assertEquals(1, allOrders.size());
        Assertions.assertEquals(1, idOrders.size());
        Assertions.assertEquals(1, idsOrders.size());
        Assertions.assertEquals(1, textsOrders.size());
        Assertions.assertEquals(0, emptyOrders.size());
    }

    @Test
    void getProductsByTagsOverQuery() {
        Product expected = repo.product().findById(1).get();

        List<Product> correctTag = service.getProducts(expected.getTags().get(0), expected.getTitle());
        List<Product> wrongTag = service.getProducts("wrongTag", expected.getTitle());

        Assertions.assertEquals(1, correctTag.size());
        Assertions.assertEquals(0, wrongTag.size());
    }

    @Test
    void getProductsByTags() {
        Product expected = repo.product().findById(1).get();
        String correctQuery = expected.getTitle();
        String emptyTag = "";
        String existingTag = expected.getTags().get(0);
        String severalTags = "wrongTag, wrongTag,  "+existingTag+"  ,  WrongTag";
        String partialTag = existingTag.substring(1);
        String caseInsensitiveUC = existingTag.toUpperCase();
        String caseInsensitiveLC = existingTag.toLowerCase();
        String wrongTag = "wrongTag";

        List<Product> allProducts = service.getProducts(emptyTag, correctQuery);
        List<Product> existingTagProducts = service.getProducts(existingTag, correctQuery);
        List<Product> severalTagsProducts = service.getProducts(severalTags, correctQuery);
        List<Product> partialTagsProducts = service.getProducts(partialTag, correctQuery);
        List<Product> ucTagsProducts = service.getProducts(caseInsensitiveUC, correctQuery);
        List<Product> lcTagsProducts = service.getProducts(caseInsensitiveLC, correctQuery);
        List<Product> noProducts = service.getProducts(wrongTag, correctQuery);

        Assertions.assertEquals(1, allProducts.size());
        Assertions.assertEquals(1, existingTagProducts.size());
        Assertions.assertEquals(1, severalTagsProducts.size());
        Assertions.assertEquals(1, ucTagsProducts.size());
        Assertions.assertEquals(1, lcTagsProducts.size());
        Assertions.assertEquals(0, partialTagsProducts.size());
        Assertions.assertEquals(0, noProducts.size());
    }

    @Test
    void getProductsByQuery() {
        Product expected = repo.product().findById(1).get();
        String correctTag = expected.getTags().get(0);

        String emptyQuery = "";
        String titleQuery = expected.getTitle();
        String idQuery = String.valueOf(expected.getId());
        String partialTitleQuery = expected.getTitle().substring(1);
        String severalQuery = "wrong,wrong, wrong,      "+titleQuery+"        , wrongQuery";
        String caseInsensitiveQueryUC = titleQuery.toUpperCase();
        String caseInsensitiveQueryLC = titleQuery.toLowerCase();
        String wrongQuery = "wrongQuery";

        List<Product> allProducts = service.getProducts(correctTag, emptyQuery);
        List<Product> titleProducts = service.getProducts(correctTag, titleQuery);
        List<Product> idProducts = service.getProducts(correctTag, idQuery);
        List<Product> partialProducts = service.getProducts(correctTag, partialTitleQuery);
        List<Product> severalProducts = service.getProducts(correctTag, severalQuery);
        List<Product> UCProducts = service.getProducts(correctTag, caseInsensitiveQueryUC);
        List<Product> LCProducts = service.getProducts(correctTag, caseInsensitiveQueryLC);
        List<Product> noProducts = service.getProducts(correctTag, wrongQuery);

        Assertions.assertEquals(1, allProducts.size());
        Assertions.assertEquals(1, titleProducts.size());
        Assertions.assertEquals(1, idProducts.size());
        Assertions.assertEquals(1, partialProducts.size());
        Assertions.assertEquals(1, severalProducts.size());
        Assertions.assertEquals(1, UCProducts.size());
        Assertions.assertEquals(1, LCProducts.size());
        Assertions.assertEquals(0, noProducts.size());
    }

    @Test
    void getNonexistantId() {
        Product p = service.getProduct(2);
        Order o = service.getOrder(2);
        Line l = service.getLine(2);
        User u = service.getUser(2);

        Assertions.assertNull(p);
        Assertions.assertNull(o);
        Assertions.assertNull(l);
        Assertions.assertNull(u);
    }

    // endregion

    // region Create

    @Test
    void createUser() {
        User expected = new User(0, "newUser", "newPass", "newAd");

        User received = service.create(expected);
        User added = repo.user().findById(0).get();

        Assertions.assertTrue(isDeepEquals(expected, received) && isDeepEquals(expected, added));
    }

    @Test
    void createProduct() {
        Product expected = new Product(0, "newTitle", "newImage", 10.0, Arrays.asList("new", "product"));

        Product received = service.create(expected);
        Product added = repo.product().findById(0).get();

        Assertions.assertTrue(isDeepEquals(expected, received) && isDeepEquals(expected, added));
    }

    @Test
    void createProductWithNegativePrice() {
        Product product = new Product(0, "newTitle", "newImage", -5.0, Arrays.asList("newTag1", "newTag2"));

        Assertions.assertThrows(RuntimeException.class, () -> service.create(product));
    }

    @Test
    void createOrder() {
        User user = repo.user().findById(1).get();
        Product product = repo.product().findById(1).get();
        List<Line> lines = Arrays.asList(new Line(0, product, 10.0, 5));
        Order expected = new Order(0, user, lines);

        Order received = service.create(expected);
        Order added = repo.order().findById(0).get();

        Assertions.assertTrue(isDeepEquals(expected, received) && isDeepEquals(expected, added));
    }

    @Test
    void createOrderWithNonexistantUser() {
        User user = new User(0, "nonUser", "nonPass", "nonAd");
        Product product = repo.product().findById(1).get();
        List<Line> lines = Arrays.asList(new Line(0, product, 10.0, 5));
        Order expected = new Order(0, user, lines);

        Assertions.assertThrows(RuntimeException.class, () -> service.create(expected));
    }

    @Test
    void createOrderWithoutUser() {
        Product product = repo.product().findById(1).get();
        List<Line> lines = Arrays.asList(new Line(0, product, 10.0, 5));
        Order expected = new Order(0, null, lines);

        Assertions.assertThrows(RuntimeException.class, () -> service.create(expected));
    }

    // endregion

    // region Update

    @Test
    void updateNonexistantEntity() {
        User user = repo.user().findById(1).get();
        Order order = repo.order().findById(1).get();
        Product product = repo.product().findById(1).get();
        Line line = repo.line().findById(1).get();

        Assertions.assertThrows(RuntimeException.class, () -> service.update(0, user));
        Assertions.assertThrows(RuntimeException.class, () -> service.update(0, order));
        Assertions.assertThrows(RuntimeException.class, () -> service.update(0, product));
        Assertions.assertThrows(RuntimeException.class, () -> service.update(0, line));
    }

    @Test
    void updateNull() {
        User user = null;
        Order order = null;
        Product product = null;
        Line line = null;

        Assertions.assertNull(service.update(1, user));
        Assertions.assertNull(service.update(1, order));
        Assertions.assertNull(service.update(1, product));
        Assertions.assertNull(service.update(1, line));
    }


    @Test
    void updateUser() {
        //imagine some tests here
    }

    @Test
    void updateProduct() {
        //imagine some tests here
    }

    @Test
    void updateOrder() {
        //imagine some tests here
    }

    @Test
    void updateLine() {
        //imagine some tests here
    }

    // endregion
}