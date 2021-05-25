package be.landtlord.legocollection.inventory.inventories.boundary;

import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryMiniFig;
import be.landtlord.legocollection.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInventoryMinifigureRepository extends CrudRepository<UserInventoryMiniFig, Long> {
    List<UserInventoryMiniFig> findAllByUser(User user);
}
