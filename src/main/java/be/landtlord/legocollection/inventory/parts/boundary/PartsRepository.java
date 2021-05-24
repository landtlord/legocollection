package be.landtlord.legocollection.inventory.parts.boundary;

import be.landtlord.legocollection.inventory.parts.entity.Part;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PartsRepository extends CrudRepository<Part, String> {
    List<Part> findByPartNumberContains(String partNumber);

    Optional<Part> findByPartNumber(String partNumber);
}
