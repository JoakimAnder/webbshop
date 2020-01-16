package se.iths.webbshop.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.webbshop.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
    User findUserByUsername(String username);
}
