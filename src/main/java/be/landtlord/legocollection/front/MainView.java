package be.landtlord.legocollection.front;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryRepository;
import be.landtlord.legocollection.inventory.inventories.boundary.PartsInventoryRepository;
import be.landtlord.legocollection.inventory.parts.boundary.PartsRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("index")
@CssImport("./styles/shared-styles.css")
public class MainView extends HorizontalLayout {

    public MainView(@Autowired PartsRepository partsRepository, @Autowired InventoryRepository inventoryRepository, @Autowired PartsInventoryRepository partsInventoryRepository) {
        addNavigationBar();
    }

    private void addNavigationBar() {
        Nav navigation = new Nav();
        VerticalLayout navbar = new VerticalLayout();
        Button sets = getButton("sets");
        Button mySets = getButton("mijn sets");
        Button miniFigures= getButton("minifiguren");
        Button myMinifigures = getButton("mijn minifiguren");
        Button parts = getButton("blokken");
        Button myParts = getButton("mijn blokken");
        Button account = getButton("account info");
        Button logOff = getButton("uitloggen");
        navbar.add(sets , mySets, miniFigures, myMinifigures, parts, myParts, account, logOff );
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
