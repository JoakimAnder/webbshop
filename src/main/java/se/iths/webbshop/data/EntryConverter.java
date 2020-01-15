package se.iths.webbshop.data;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import se.iths.webbshop.entities.Product;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Converter
public class EntryConverter implements AttributeConverter<Entry, String> {
    @Autowired
    Dao dao;

    @Override
    public String convertToDatabaseColumn(Entry entry) {
        return String.format("%d,%d", entry.product.getId(), entry.getAmount());
    }

    @Override
    public Entry convertToEntityAttribute(String s) {
        String[] ids = s.split(",");
        int productId = Integer.parseInt(ids[0]);
        int amount = Integer.parseInt(ids[1]);
        return new Entry(dao.getProduct(productId), amount);
    }
}
