package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
import be.landtlord.legocollection.inventory.inventories.entity.PartsInventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartsInventoryRepository extends CrudRepository<PartsInventory, Long> {
    List<PartsInventory> findAllByInventory(Inventory inventory);
}
