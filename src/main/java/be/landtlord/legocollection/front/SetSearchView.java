package be.landtlord.legocollection.front;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryRepository;
import be.landtlord.legocollection.inventory.inventories.boundary.SetInventoryRepository;
import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
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
    private Grid<Inventory> setsGrid = new Grid();

    private InventoryRepository inventoryRepository;
    private SetInventoryRepository setInventoryRepository;


    public SetSearchView(@Autowired InventoryRepository inventoryRepository, @Autowired SetInventoryRepository setInventoryRepository) {
        super();
        this.inventoryRepository = inventoryRepository;
        this.setInventoryRepository = setInventoryRepository;
        setSearchBar();
        setGrid();
        content.setWidthFull();
        content.add(searchBar, setsGrid);
        add(content);
    }

    private void setGrid() {
        setsGrid.addColumn(Inventory::getId).setHeader("id");
        setsGrid.addColumn(Inventory::getSetNumber).setHeader("setnummer");
        setsGrid.setHeightByRows(true);
        setsGrid.setVisible(false);
    }

    private void setSearchBar() {
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
        List<Inventory> inventories = inventoryRepository.findBySetNumberContains(setNumber);
        setsGrid.setItems(inventories);
        setsGrid.setVisible(true);
    }
}
