package se.iths.webbshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.webbshop.entities.Line;
import se.iths.webbshop.entities.Product;
import se.iths.webbshop.utilities.Cart;
import se.iths.webbshop.utilities.Entry;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CartTest {
    Cart cart;

    @BeforeEach
    void setup() {
        cart = new Cart();
    }

    @Test
    void getEntries() {
        Product p1 = new Product(1, "p1", "img1", 10.0, Arrays.asList("p1t1","p1t2","p1t3"));
        Product p2 = new Product(2, "p2", "img2", 20.0, Arrays.asList("p2t1","p2t2","p2t3"));
        int v1 = 1;
        int v2 = 2;

        cart.put(p1, v1);
        cart.put(p2, v2);
        List<Entry> entries = cart.getEntries();
        entries.sort(Comparator.comparingInt(e -> e.getKey().getId()));

        Entry entry = entries.get(0);
        Assertions.assertEquals(p1.getId(), entry.getKey().getId());
        Assertions.assertEquals(p1.getImage(), entry.getKey().getImage());
        Assertions.assertEquals(p1.getPrice(), entry.getKey().getPrice());
        Assertions.assertEquals(p1.getTags(), entry.getKey().getTags());
        Assertions.assertEquals(p1.getTitle(), entry.getKey().getTitle());
        Assertions.assertEquals(v1, entry.getValue());

        entry = entries.get(1);
        Assertions.assertEquals(p2.getId(), entry.getKey().getId());
        Assertions.assertEquals(p2.getImage(), entry.getKey().getImage());
        Assertions.assertEquals(p2.getPrice(), entry.getKey().getPrice());
        Assertions.assertEquals(p2.getTags(), entry.getKey().getTags());
        Assertions.assertEquals(p2.getTitle(), entry.getKey().getTitle());
        Assertions.assertEquals(v2, entry.getValue());

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> entries.get(2));
    }

    @Test
    void convertToLines() {
        double price1 = 10.0;
        double price2 = 20.0;
        Product p1 = new Product(1, "p1", "img1", price1, Arrays.asList("p1t1","p1t2","p1t3"));
        Product p2 = new Product(2, "p2", "img2", price2, Arrays.asList("p2t1","p2t2","p2t3"));
        int v1 = 1;
        int v2 = 2;

        cart.put(p1, v1);
        cart.put(p2, v2);
        List<Line> lines = cart.convertToLines();
        lines.sort(Comparator.comparingInt(e -> e.getProduct().getId()));

        Line line = lines.get(0);
        Assertions.assertEquals(p1.getId(), line.getProduct().getId());
        Assertions.assertEquals(p1.getImage(), line.getProduct().getImage());
        Assertions.assertEquals(p1.getPrice(), line.getProduct().getPrice());
        Assertions.assertEquals(p1.getTags(), line.getProduct().getTags());
        Assertions.assertEquals(p1.getTitle(), line.getProduct().getTitle());
        Assertions.assertEquals(v1, line.getAmount());
        Assertions.assertEquals(price1, line.getPrice());

        line = lines.get(1);
        Assertions.assertEquals(p2.getId(), line.getProduct().getId());
        Assertions.assertEquals(p2.getImage(), line.getProduct().getImage());
        Assertions.assertEquals(p2.getPrice(), line.getProduct().getPrice());
        Assertions.assertEquals(p2.getTags(), line.getProduct().getTags());
        Assertions.assertEquals(p2.getTitle(), line.getProduct().getTitle());
        Assertions.assertEquals(v2, line.getAmount());
        Assertions.assertEquals(price2, line.getPrice());

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> lines.get(2));
    }

    @Test
    void getSize() {
        Product p1 = new Product(1, "", "", 0, Collections.emptyList());
        Product p2 = new Product(2, "", "", 0, Collections.emptyList());
        int v1 = (int) (Math.random() * 1000);
        int v2 = (int) (Math.random() * 1000);

        cart.put(p1, v1);
        cart.put(p2, v2);

        Assertions.assertEquals(v1+v2, cart.getSize());
    }

    @Test
    void findAmount() {
        Product p1 = new Product(1, "", "", 0, Collections.emptyList());
        Product p2 = new Product(2, "", "", 0, Collections.emptyList());
        Product p3 = new Product(3, "", "", 0, Collections.emptyList());
        int v1 = (int) (Math.random() * 1000);
        int v2 = (int) (Math.random() * 1000);

        cart.put(p1, v1);
        cart.put(p2, v2);

        Assertions.assertEquals(v1, cart.findAmount(p1));
        Assertions.assertEquals(v2, cart.findAmount(p2));
        Assertions.assertEquals(0, cart.findAmount(p3));
        Assertions.assertEquals(0, cart.findAmount(null));
    }

    @Test
    void put() {
        Product p1 = new Product(1, "p1", "img1", 10.0, Arrays.asList("p1t1","p1t2","p1t3"));
        Product p2 = new Product(2, "p2", "img2", 20.0, Arrays.asList("p2t1","p2t2","p2t3"));
        Product p3 = null;
        int v1 = (int) (Math.random() * 1000);
        int v2 = (int) (Math.random() * 1000) + 1;
        int v3 = (int) (Math.random() * 1000) + 1;

        cart.put(p1, v1);
        cart.put(p2, v2);
        cart.put(p3, v3);
        List<Entry> entries = cart.getEntries();
        entries.sort(Comparator.comparingInt(e -> e.getKey().getId()));

        Assertions.assertEquals(2, entries.size());
        Assertions.assertEquals(v1+v2, cart.getSize());

        Entry entry = entries.get(0);
        Assertions.assertEquals(p1.getId(), entry.getKey().getId());
        Assertions.assertEquals(p1.getImage(), entry.getKey().getImage());
        Assertions.assertEquals(p1.getPrice(), entry.getKey().getPrice());
        Assertions.assertEquals(p1.getTags(), entry.getKey().getTags());
        Assertions.assertEquals(p1.getTitle(), entry.getKey().getTitle());
        Assertions.assertEquals(v1, entry.getValue());

        entry = entries.get(1);
        Assertions.assertEquals(p2.getId(), entry.getKey().getId());
        Assertions.assertEquals(p2.getImage(), entry.getKey().getImage());
        Assertions.assertEquals(p2.getPrice(), entry.getKey().getPrice());
        Assertions.assertEquals(p2.getTags(), entry.getKey().getTags());
        Assertions.assertEquals(p2.getTitle(), entry.getKey().getTitle());
        Assertions.assertEquals(v2, entry.getValue());

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> entries.get(2));
    }


    @Test
    void putUnderOneAmount() {
        Product p1 = new Product(1, "", "", 0, Collections.emptyList());
        Product p2 = new Product(2, "", "", 0, Collections.emptyList());
        int v1 = 0;
        int v2 = (int) (Math.random() * -1000) - 1;

        cart.put(p1, v1);
        cart.put(p2, v2);
        List<Entry> entries = cart.getEntries();

        Assertions.assertEquals(0, cart.getSize());
        Assertions.assertEquals(0, entries.size());
    }

    @Test
    void putSameProduct() {
        Product p1 = new Product(1, "", "", 0, Collections.emptyList());
        int v1 = (int) (Math.random() * 1000);
        int v2 = (int) (Math.random() * 1000) + 1;

        cart.put(p1, v1);
        cart.put(p1, v2);
        List<Entry> entries = cart.getEntries();

        Assertions.assertEquals(v2, cart.getSize());
        Assertions.assertEquals(v2, cart.findAmount(p1));
        Assertions.assertEquals(1, entries.size());
    }

    @Test
    void getTotalPrice() {
        double price1 = Math.random() * 10000;
        double price2 = Math.random() * 10000;
        Product p1 = new Product(1, "", "", price1, Collections.emptyList());
        Product p2 = new Product(2, "", "", price2, Collections.emptyList());
        int v1 = (int) (Math.random() * 1000);
        int v2 = (int) (Math.random() * 1000);

        double expectedPrice = price1 * v1 + price2 * v2;

        cart.put(p1, v1);
        cart.put(p2, v2);

        Assertions.assertEquals(expectedPrice, cart.getTotalPrice());
    }

    @Test
    void getTotalPriceOnEmptyCart() {
        Assertions.assertEquals(0, cart.getTotalPrice());
    }

    @Test
    void clear() {
        Product p1 = new Product(1, "", "", 0, Collections.emptyList());
        Product p2 = new Product(2, "", "", 0, Collections.emptyList());

        cart.put(p1, 10);
        cart.put(p2, 10);

        cart.clear();

        Assertions.assertEquals(0, cart.convertToLines().size());
        Assertions.assertEquals(0, cart.getEntries().size());
        Assertions.assertEquals(0, cart.getSize());
    }
}