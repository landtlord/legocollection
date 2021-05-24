package be.landtlord.legocollection.user.boundary;

import be.landtlord.legocollection.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
