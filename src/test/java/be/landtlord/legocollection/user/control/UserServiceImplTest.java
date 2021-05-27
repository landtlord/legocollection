package be.landtlord.legocollection.user.control;

import be.landtlord.legocollection.user.boundary.UserRepository;
import be.landtlord.legocollection.user.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenUserNameWithCorrectPassWord_whenAuthenticateIsInvoked_thenUserIsReturned() {
        User user = new User();
        user.setUserName("user");
        String md5Hex = DigestUtils
                .md5Hex("password").toUpperCase();
        user.setPasswordHash(md5Hex);

        doReturn(Optional.of(user)).when(userRepository).findByUserName("user");

        User authenticate = userService.authenticate("user", "password");

        assertThat(authenticate).isNotNull();
        assertThat(authenticate).isEqualTo(user);
    }

    @Test
    void givenUserNameWithIncorrectPassWord_whenAuthenticateIsInvoked_thenNullIsReturned() {
        User user = new User();
        user.setUserName("user");
        String md5Hex = DigestUtils
                .md5Hex("password").toUpperCase();
        user.setPasswordHash(md5Hex);

        doReturn(Optional.of(user)).when(userRepository).findByUserName("user");

        User authenticate = userService.authenticate("user", "wrongPassword");

        assertThat(authenticate).isNull();
    }

    @Test
    void givenUserNameWithNullPassWord_whenAuthenticateIsInvoked_thenNullIsReturned() {
        User authenticate = userService.authenticate("user", null);

        assertThat(authenticate).isNull();
    }

    @Test
    void givenNullUserNameWithPassWord_whenAuthenticateIsInvoked_thenNullIsReturned() {
        User authenticate = userService.authenticate(null, "password");

        assertThat(authenticate).isNull();
    }
    @Test
    void givenUnknownUserNameWithPassWord_whenAuthenticateIsInvoked_thenNullIsReturned() {
        doReturn(Optional.empty()).when(userRepository).findByUserName("unKnownUser");

        User authenticate = userService.authenticate("unKnownUser", "password");

        assertThat(authenticate).isNull();
    }

    @Test
    void updateUser(){
        User user = new User();

        userService.update(user);

        verify(userRepository, times(1)).save(user);
    }
}
