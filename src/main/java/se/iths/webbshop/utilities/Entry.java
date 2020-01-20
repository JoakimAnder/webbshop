package se.iths.webbshop.utilities;

import se.iths.webbshop.entities.Product;

public class Entry {
    Product key;
    int value;

    public Entry() {
    }

    public Entry(Product key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Product getKey() {
        return key;
    }

    public void setKey(Product key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (value != entry.value) return false;
        return key != null ? key.equals(entry.key) : entry.key == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + value;
        return result;
    }
}