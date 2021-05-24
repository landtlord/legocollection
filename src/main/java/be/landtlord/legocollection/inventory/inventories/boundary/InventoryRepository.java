package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    List<Inventory> findBySetNumber(String setNumber);
}
