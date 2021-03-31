package be.landtlord.legocollection.inventory.sets.boundary;

import be.landtlord.legocollection.inventory.sets.entity.Set;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SetRepository extends CrudRepository<Set, String> {
    List<Set> findAllBySetNumberContains(String setNumber);
}
