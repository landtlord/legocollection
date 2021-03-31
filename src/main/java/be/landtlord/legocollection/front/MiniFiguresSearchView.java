package be.landtlord.legocollection.front;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
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
public class MiniFiguresSearchView extends MainView {

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<MiniFigure> grid = new Grid(MiniFigure.class);

    private InventoryService inventoryService;

    @Autowired
    public MiniFiguresSearchView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setGrid();
        setSearchBar();
        content.add(searchBar, grid);
        add(content);
    }

    private void setGrid() {
        grid.setHeightByRows(true);
        grid.setVisible(false);
        grid.addItemClickListener(e-> UI.getCurrent().navigate(SetView.class, e.getItem().getMiniFigureNumber()));
    }

    private void setSearchBar() {
        searchBar.setWidthFull();
        TextField search = new TextField();
        Button searchButton = new Button("Zoeken");
        searchButton.addClickListener(event -> setGridData(search.getValue()));
        searchButton.setWidth("175px");
        search.setPlaceholder("Mini figure nummer");
        search.setWidth("60%");
        setWidthFull();
        searchBar.add(search, searchButton);
    }

    private void setGridData(String setNumber) {
        List<MiniFigure> miniFigures = inventoryService.findByMiniFigureNumberContains(setNumber);
        grid.setItems(miniFigures);
        grid.setVisible(true);
    }
}
