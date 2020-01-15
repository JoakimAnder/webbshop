package se.iths.webbshop.data;

import se.iths.webbshop.entities.Product;

import javax.persistence.Convert;

@Convert(converter = EntryConverter.class)
public class Entry {
    Product product;
    Integer amount;

    public Entry() {
    }

    public Entry(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (product != null ? !product.equals(entry.product) : entry.product != null) return false;
        return amount != null ? amount.equals(entry.amount) : entry.amount == null;
    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}