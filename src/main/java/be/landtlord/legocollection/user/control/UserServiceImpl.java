package be.landtlord.legocollection.user.control;

import be.landtlord.legocollection.user.boundary.UserRepository;
import be.landtlord.legocollection.user.boundary.UserService;
import be.landtlord.legocollection.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User authenticate(String userName, String passWord) {
        if (isNull(userName) || isNull(passWord)){
            return null;
        }
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isEmpty()) {
            return null;
        }
        if (!passWord.equals(user.get().getPasswordHash())){
            return null;
        }
        return user.get();
    }
}
