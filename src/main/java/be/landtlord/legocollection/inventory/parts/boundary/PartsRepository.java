package be.landtlord.legocollection.inventory.parts.boundary;

import be.landtlord.legocollection.inventory.parts.entity.Part;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartsRepository extends CrudRepository<Part, String> {
    List<Part> findByPartNumberContains(String partNumber);
}
