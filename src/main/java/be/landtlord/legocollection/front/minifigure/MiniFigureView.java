package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.front.parts.PartsListComponent;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFigureService;
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
    private final VerticalLayout content = new VerticalLayout();

    private MiniFigure miniFigure;

    private final MiniFigureGeneralInfo generalInfo;

    private final PartsListComponent partsGrid;


    private final InventoryService inventoryService;

    private final MiniFigureService miniFigureService;

    @Autowired
    public MiniFigureView(InventoryService inventoryService, MiniFigureService miniFigureService) {
        this.inventoryService = inventoryService;
        this.miniFigureService = miniFigureService;
        generalInfo = new MiniFigureGeneralInfo(inventoryService);
        generalInfo.setHeight("50%");
        partsGrid = new PartsListComponent();
        content.add(generalInfo, partsGrid);
        add(content);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String setNumber) {
        miniFigure = miniFigureService.getMiniFigureBy(setNumber);
        generalInfo.fillWith(miniFigure);
        partsGrid.setItems(inventoryService.getPartsForMiniFigNumber(miniFigure.getMiniFigureNumber()));
    }

    private HorizontalLayout getGeneralInfo() {
        HorizontalLayout generalInfo = new HorizontalLayout();
        Image image = new Image(miniFigure.getImageUrl(), "image");
        generalInfo.add(image);
        return generalInfo;
    }
}
