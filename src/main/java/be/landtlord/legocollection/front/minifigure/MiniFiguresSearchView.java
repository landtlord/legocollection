package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.parts.entity.Part;
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
public class MiniFiguresSearchView extends MainView {

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<MiniFigure> grid = new Grid();

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
        grid.addItemClickListener(e-> UI.getCurrent().navigate(MiniFigureView.class, e.getItem().getMiniFigureNumber()));

        grid.addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        grid.addColumn(MiniFigure::getName).setHeader("Naam");
        grid.addColumn(MiniFigure::getMiniFigureNumber).setHeader("Part nummer");
        grid.addColumn(MiniFigure::getNumberOfParts).setHeader("Aantal blokken");
    }

    private Image setImageOnGrid(MiniFigure miniFigure) {
        Image image = new Image();
        if (Objects.nonNull(miniFigure.getImageUrl())) {
            image.setSrc(miniFigure.getImageUrl());
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
