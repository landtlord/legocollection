package be.landtlord.legocollection.front.set;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route
public class SetSearchView extends MainView {

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<Set> setsGrid = new Grid();

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

        setsGrid.addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        setsGrid.addColumn(Set::getName).setHeader("Naam");
        setsGrid.addColumn(Set::getSetNumber).setHeader("Set nummer");
        setsGrid.addColumn(Set::getTheme).setHeader("Thema");
        setsGrid.addColumn(Set::getYear).setHeader("Uitgekomen in").setSortable(false);

    }

    private Image setImageOnGrid(Set set) {
        Image image = new Image();
        if (Objects.nonNull(set.getImageUrl())) {
            image.setSrc(set.getImageUrl());
        }
        image.setWidth("75px");
        image.setHeight("75px");
        return image;
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
