package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.front.parts.PartsListComponent;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MiniFigureView extends MainView implements HasUrlParameter<String> {
    private VerticalLayout content = new VerticalLayout();

    private MiniFigure miniFigure;

    private MiniFigureGeneralInfo generalInfo;

    private PartsListComponent partsGrid;

    @Autowired
    private InventoryService inventoryService;

    public MiniFigureView() {
        generalInfo = new MiniFigureGeneralInfo();
        generalInfo.setHeight("50%");
        partsGrid = new PartsListComponent();
        content.add(generalInfo, partsGrid);
        add(content);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String setNumber) {
        miniFigure = inventoryService.getMiniFigureBy(setNumber);
        generalInfo.fillWith(miniFigure);
        partsGrid.setItems(inventoryService.getPartsForSetNumber(miniFigure.getMiniFigureNumber()));
    }

    private HorizontalLayout getGeneralInfo() {
        HorizontalLayout generalInfo = new HorizontalLayout();
        Image image = new Image(miniFigure.getImageUrl(), "image");
        generalInfo.add(image);
        return generalInfo;
    }
}
