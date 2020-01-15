package se.iths.webbshop.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.webbshop.entities.Line;

@Repository
public interface LineRepository extends CrudRepository<Line, Integer> {
}
