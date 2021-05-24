package be.landtlord.legocollection.front;

import be.landtlord.legocollection.user.boundary.UserService;
import be.landtlord.legocollection.user.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.nonNull;

@Route("")
@CssImport("./styles/shared-styles.css")
public class LoginView extends HorizontalLayout {
    private VerticalLayout loginscreen = new VerticalLayout();

    @Autowired
    private UserService userService;

    public LoginView() {
        LoginForm component = new LoginForm();
        component.setId("login");
        component.addLoginListener(e -> {
            boolean isAuthenticated = authenticate(e);
            if (isAuthenticated) {
                UI.getCurrent().navigate("index");
            } else {
                component.setError(true);
            }
        });
        add(component);
    }

    private boolean authenticate(AbstractLogin.LoginEvent event) {
        String userName = event.getUsername();
        String passWord = event.getPassword();
        User user = userService.authenticate(userName, passWord);
        VaadinSession.getCurrent().setAttribute("user", user);
        return nonNull(user);
    }
}
