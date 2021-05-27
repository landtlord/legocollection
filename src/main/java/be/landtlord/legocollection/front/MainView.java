package be.landtlord.legocollection.front;

import be.landtlord.legocollection.front.minifigure.MiniFiguresSearchView;
import be.landtlord.legocollection.front.minifigure.MyMiniFiguresSearchView;
import be.landtlord.legocollection.front.parts.MyPartsSearchView;
import be.landtlord.legocollection.front.parts.PartsSearchView;
import be.landtlord.legocollection.front.set.MySetSearchView;
import be.landtlord.legocollection.front.set.SetSearchView;
import be.landtlord.legocollection.user.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.Objects;

@Route("index")
@CssImport("./styles/shared-styles.css")
public class MainView extends HorizontalLayout implements BeforeEnterObserver {

    public MainView() {
        addNavigationBar();
        setHeightFull();
    }

    private void addNavigationBar() {
        Nav navigation = new Nav();
        VerticalLayout navbar = new VerticalLayout();
        Button sets = getButton("sets");
        sets.addClickListener(e -> UI.getCurrent().navigate(SetSearchView.class));
        Button mySets = getButton("mijn sets");
        mySets.addClickListener(e -> UI.getCurrent().navigate(MySetSearchView.class));
        Button miniFigures = getButton("minifiguren");
        miniFigures.addClickListener(e -> UI.getCurrent().navigate(MiniFiguresSearchView.class));
        Button myMiniFigures = getButton("mijn minifiguren");
        myMiniFigures.addClickListener(e -> UI.getCurrent().navigate(MyMiniFiguresSearchView.class));
        Button parts = getButton("blokken");
        parts.addClickListener(e -> UI.getCurrent().navigate(PartsSearchView.class));
        Button myParts = getButton("mijn blokken");
        myParts.addClickListener(e -> UI.getCurrent().navigate(MyPartsSearchView.class));
        Button account = getButton("account info");
        account.addClickListener(e -> UI.getCurrent().navigate(AccountView.class));
        Button logOff = getButton("uitloggen");
        logOff.addClickListener(e -> logout());
        navbar.add(sets, mySets, miniFigures, myMiniFigures, parts, myParts, account, logOff);
        navigation.add(navbar);
        navigation.setWidth("175 px");

        add(navigation);
    }

    private void logout() {
        VaadinSession.getCurrent().setAttribute("user", null);
        UI.getCurrent().navigate(LoginView.class);
    }


    private Button getButton(String label) {
        Button button = new Button(label);
        button.setWidthFull();
        return button;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        if (Objects.isNull(user)) {
            UI.getCurrent().navigate(LoginView.class);
            UI.getCurrent().getPage().reload();
        }
    }
}
