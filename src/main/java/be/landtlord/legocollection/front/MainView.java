package be.landtlord.legocollection.front;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("index")
@CssImport("./styles/shared-styles.css")
public class MainView extends HorizontalLayout {

    public MainView() {
        addNavigationBar();
        setHeightFull();
    }

    private void addNavigationBar() {
        Nav navigation = new Nav();
        VerticalLayout navbar = new VerticalLayout();
        Button sets = getButton("sets");
        sets.addClickListener(e-> UI.getCurrent().navigate(SetSearchView.class));
        Button mySets = getButton("mijn sets");
        Button miniFigures= getButton("minifiguren");
        Button myMiniFigures = getButton("mijn minifiguren");
        Button parts = getButton("blokken");
        Button myParts = getButton("mijn blokken");
        Button account = getButton("account info");
        Button logOff = getButton("uitloggen");
        navbar.add(sets , mySets, miniFigures, myMiniFigures, parts, myParts, account, logOff );
        navigation.add(navbar);
        navigation.setWidth("175 px");

        add(navigation);
    }


    private Button getButton(String label) {
        Button button = new Button(label);
        button.setWidthFull();
        return button;
    }
    

}
