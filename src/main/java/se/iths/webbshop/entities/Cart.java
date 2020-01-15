package se.iths.webbshop.entities;


import se.iths.webbshop.data.Entry;
import se.iths.webbshop.data.EntryConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Convert(converter = EntryConverter.class)
    @ElementCollection(targetClass = Entry.class)
    private List<Entry> basket;

    public Cart() {
    }

    public Cart(int id) {
        this.id = id;
        this.basket = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Entry> getBasket() {
        return basket;
    }

    public void setBasket(List<Entry> basket) {
        this.basket = basket;
    }

    public java.util.Set<Map.Entry<Product, Integer>> getEntries() {
        return basket.stream()
                .collect(Collectors.toMap(Entry::getProduct, Entry::getAmount))
                .entrySet();
    }

    public int getSize() {
        return basket.stream().map(Entry::getAmount).reduce(0, Integer::sum);
    }

    public int getAmount(Product product) {
        Integer amount = get(product).getAmount();
        return amount == null ? 0 : amount;
    }

    private Entry get(Product key) {
        return basket.stream()
                .filter(e -> e.getProduct().getId() == key.getId())
                .findFirst()
                .orElse(new Entry());
    }

    public void put(Product key, Integer value) {
        basket.remove(get(key));
        if(value > 0) {
            basket.add(new Entry(key, value));
        }
    }

    public double getTotalPrice() {
        return basket.stream()
                .map(e -> e.getProduct().getPrice() * e.getAmount())
                .reduce(0.0, Double::sum);
    }
}
