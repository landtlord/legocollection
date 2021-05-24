package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.InventoryParts;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryPart;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import be.landtlord.legocollection.inventory.sets.entity.SetInventory;
import be.landtlord.legocollection.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    List<Set> findBySetNumberContains(String setNumber);

    Set getSetBy(String setNumber);

    List<MiniFigure> findByMiniFigureNumberContains(String setNumber);

    MiniFigure getMiniFigureBy(String miniFigureNumber);

    List<Part>  finByPartNumberContains(String partNumber);

    Part getPartBy(String partNumber);

    List<InventoryParts> getPartsForSetNumber(String setNumber);

    UserInventorySet addToMyList(Set set, User user);

    UserInventorySet getUserInventorySetBy(String id);

    List<UserInventorySet> getUserInventorySetByUser(User user);

    List<UserInventoryPart> getUserInventoryPartsByUserInventorySet(UserInventorySet userInventorySet);

    UserInventoryPart updateUserInventoryPart(UserInventoryPart userInventoryPart);

    List<UserInventoryPart> getUserInventoryPartWithStockByUser(User user);
}
