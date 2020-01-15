package se.iths.webbshop.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.webbshop.entities.Cart;
import se.iths.webbshop.entities.User;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
}
