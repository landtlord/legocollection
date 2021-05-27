package be.landtlord.legocollection.front;

import be.landtlord.legocollection.user.boundary.UserService;
import be.landtlord.legocollection.user.entity.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;


@Route
public class AccountView extends MainView {
    private final User user;

    private final UserService userService;

    @Autowired
    public AccountView(UserService userService) {
        this.userService = userService;
        user = (User) VaadinSession.getCurrent().getAttribute("user");

        TextField textField = new TextField();
        textField.setWidth("300px");
        textField.setLabel("API key rebrickable");
        Binder<User> binder = new Binder<>();
        binder.bind(textField, User::getAPIKey, User::setAPIKey);
        binder.readBean(user);
        Button saveButton = new Button("Opslaan");
        saveButton.addClickListener(e -> {
            try {
                binder.writeBean(user);
                updateUser();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });
        add(textField, saveButton);
    }

    private void updateUser() {
        userService.update(user);
    }

}
