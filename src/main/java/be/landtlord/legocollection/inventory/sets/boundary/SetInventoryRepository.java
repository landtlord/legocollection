package be.landtlord.legocollection.inventory.sets.boundary;

import be.landtlord.legocollection.inventory.sets.entity.SetInventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SetInventoryRepository extends CrudRepository<SetInventory, String> {
    List<SetInventory> findBySet_SetNumberContains(String setNumber);
    }
