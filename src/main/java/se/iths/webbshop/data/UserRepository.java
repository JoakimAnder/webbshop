package se.iths.webbshop.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.webbshop.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByUsernameAndPassword(String username, String password);
}
