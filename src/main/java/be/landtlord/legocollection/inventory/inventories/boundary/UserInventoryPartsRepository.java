package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryMiniFig;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryPart;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInventoryPartsRepository extends CrudRepository<UserInventoryPart, Long> {
    List<UserInventoryPart> findAllByUserInventorySet(UserInventorySet userInventorySet);

    List<UserInventoryPart> findAllByUserInventoryMiniFig(UserInventoryMiniFig userInventoryMiniFig);

    List<UserInventoryPart> findAllByUser(User user);
}
