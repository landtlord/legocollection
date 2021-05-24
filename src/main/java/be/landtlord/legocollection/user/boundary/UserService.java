package be.landtlord.legocollection.user.boundary;

import be.landtlord.legocollection.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User authenticate(String userName, String passWord);
}
