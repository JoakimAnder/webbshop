package se.iths.webbshop.controllers.utilities;


import se.iths.webbshop.entities.Line;
import se.iths.webbshop.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private List<Entry> basket;

    public Cart() {
        basket = new ArrayList<>();
    }

    public List<Entry> getEntries() {
        return basket;
    }

    public List<Line> convertToOrderLines() {
        return getEntries().stream()
                .map(e -> new Line(0, e.getKey(), e.getKey().getPrice(), e.getValue()))
                .collect(Collectors.toList());
    }

    public int getSize() {
        return basket.stream().map(Entry::getValue).reduce(0, Integer::sum);
    }

    public int findAmount(Product product) {
        Integer amount = get(product).getValue();
        return amount == null ? 0 : amount;
    }

    private Entry get(Product key) {
        return basket.stream()
                .filter(e -> e.getKey().getId() == key.getId())
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
                .map(e -> e.getKey().getPrice() * e.getValue())
                .reduce(0.0, Double::sum);
    }

    public void clear() {
        basket.clear();
    }
}
