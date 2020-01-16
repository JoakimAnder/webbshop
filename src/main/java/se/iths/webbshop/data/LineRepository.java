package se.iths.webbshop.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.iths.webbshop.entities.Line;

import java.util.List;

@Repository
public interface LineRepository extends CrudRepository<Line, Integer> {
    List<Line> findAll();
    List<Line> save(Iterable<Line> lines);
}
