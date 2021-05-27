package be.landtlord.legocollection.front.parts;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.parts.boundary.PartsService;
import be.landtlord.legocollection.inventory.parts.entity.Part;
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
public class PartsSearchView extends MainView {

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<Part> partsGrid = new Grid();

    private PartsService partService;

    @Autowired
    public PartsSearchView(PartsService partService) {
        this.partService = partService;
        setGrid();
        setSearchBar();
        content.add(searchBar, partsGrid);
        add(content);
    }

    private void setGrid() {
        partsGrid.setHeightByRows(true);
        partsGrid.setVisible(false);
        partsGrid.addItemClickListener(e -> UI.getCurrent().navigate(PartView.class, e.getItem().getPartNumber()));

        partsGrid.addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        partsGrid.addColumn(Part::getName).setHeader("Naam");
        partsGrid.addColumn(Part::getPartNumber).setHeader("Part nummer");
        partsGrid.addColumn(Part::getPartCategory).setHeader("Thema");
    }

    private Image setImageOnGrid(Part part) {
        Image image = new Image();
        if (Objects.nonNull(part.getImageUrl())) {
            image.setSrc(part.getImageUrl());
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
        search.setPlaceholder("Part nummer");
        search.setWidth("60%");
        setWidthFull();
        searchBar.add(search, searchButton);
    }

    private void setGridData(String partNumber) {
        List<Part> parts = partService.finByPartNumberContains(partNumber);
        partsGrid.setItems(parts);
        partsGrid.setVisible(true);
    }
}
