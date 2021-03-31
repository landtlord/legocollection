package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import be.landtlord.legocollection.inventory.sets.entity.SetInventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    List<Set> findBySetNumberContains(String setNumber);

    Set getSetBy(String setNumber);

    List<MiniFigure> findByMiniFigureNumberContains(String setNumber);
}
