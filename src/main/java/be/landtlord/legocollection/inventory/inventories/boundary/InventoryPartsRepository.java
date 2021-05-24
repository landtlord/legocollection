package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
import be.landtlord.legocollection.inventory.inventories.entity.InventoryParts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryPartsRepository extends CrudRepository<InventoryParts, Long> {
    List<InventoryParts> findByInventory(Inventory inventory);
}
