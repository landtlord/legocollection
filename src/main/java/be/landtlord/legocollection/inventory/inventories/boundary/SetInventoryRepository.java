package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
import be.landtlord.legocollection.inventory.inventories.entity.SetInventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SetInventoryRepository extends CrudRepository<SetInventory, String> {
    Optional<SetInventory> findByInventory(Inventory s);

    }
