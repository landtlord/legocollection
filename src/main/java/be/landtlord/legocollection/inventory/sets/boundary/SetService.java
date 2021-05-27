package be.landtlord.legocollection.inventory.sets.boundary;

import be.landtlord.legocollection.inventory.sets.entity.Set;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetService {
    List<Set> findBySetNumberContains(String setNumber);

    Set getSetBy(String setNumber);
}
