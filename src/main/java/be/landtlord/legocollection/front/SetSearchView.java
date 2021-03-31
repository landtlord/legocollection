package be.landtlord.legocollection.front;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryRepository;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.sets.boundary.SetInventoryRepository;
import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import be.landtlord.legocollection.inventory.sets.entity.SetInventory;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route
public class SetSearchView extends MainView {

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<Set> setsGrid = new Grid(Set.class);

    private InventoryService inventoryService;

    @Autowired
    public SetSearchView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setGrid();
        setSearchBar();
        content.add(searchBar, setsGrid);
        add(content);
    }

    private void setGrid() {
        setsGrid.setHeightByRows(true);
        setsGrid.setVisible(false);
        setsGrid.addItemClickListener(e-> UI.getCurrent().navigate(SetView.class, e.getItem().getSetNumber()));
    }

    private void setSearchBar() {
        searchBar.setWidthFull();
        TextField search = new TextField();
        Button searchButton = new Button("Zoeken");
        searchButton.addClickListener(event -> setGridData(search.getValue()));
        searchButton.setWidth("175px");
        search.setPlaceholder("Set nummer");
        search.setWidth("60%");
        setWidthFull();
        searchBar.add(search, searchButton);
    }

    private void setGridData(String setNumber) {
        List<Set> sets = inventoryService.findBySetNumberContains(setNumber);
        setsGrid.setItems(sets);
        setsGrid.setVisible(true);
    }
}
