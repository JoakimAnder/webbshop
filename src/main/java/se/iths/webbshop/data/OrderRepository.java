package se.iths.webbshop.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.webbshop.entities.Order;
import se.iths.webbshop.entities.User;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAllByIdIn(Integer[] id);
    List<Order> findAll();
}
