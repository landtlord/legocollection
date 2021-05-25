package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.InventoryParts;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryMiniFig;
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

    List<InventoryParts> getPartsForMiniFigNumber(String miniFigNumber);

    UserInventorySet addToMyList(Set set, User user);

    UserInventoryMiniFig addToMyList(MiniFigure set, User user);

    UserInventorySet getUserInventorySetBy(String id);

    UserInventoryMiniFig getUserInventoryMiniFigureBy(String id);

    List<UserInventorySet> getUserInventorySetByUser(User user);

    List<UserInventoryMiniFig> getUserInventoryMiniFigureByUser(User user);

    List<UserInventoryPart> getUserInventoryPartsByUserInventorySet(UserInventorySet userInventorySet);

    List<UserInventoryPart> getUserInventoryPartsByUserInventoryMiniFigure(UserInventoryMiniFig userInventoryMiniFig);

    UserInventoryPart updateUserInventoryPart(UserInventoryPart userInventoryPart);

    List<UserInventoryPart> getUserInventoryPartWithStockByUser(User user);

    List<UserInventorySet> getUserInventorySetByUserAndSetNumberContains(User user, String setNumber);

    List<UserInventoryMiniFig> getUserInventoryMiniFigureByUserAndFigNumberContains(User user, String setNumber);

    List<UserInventoryPart> getUserInventoryPartByUserAndPartNumberContains(User user, String partNumber);
}
