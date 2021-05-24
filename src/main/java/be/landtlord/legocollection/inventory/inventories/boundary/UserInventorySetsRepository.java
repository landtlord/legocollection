package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInventorySetsRepository extends CrudRepository<UserInventorySet, Long> {
    List<UserInventorySet> findAllByUser(User user);
}
