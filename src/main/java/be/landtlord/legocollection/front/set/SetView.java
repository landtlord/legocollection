package be.landtlord.legocollection.front.set;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.front.parts.PartsListComponent;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.InventoryParts;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import be.landtlord.legocollection.inventory.sets.boundary.SetService;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class SetView extends MainView implements HasUrlParameter<String> {
    private VerticalLayout content = new VerticalLayout();

    private SetGeneralInfo generalInfo;

    private PartsListComponent partsGrid;

    private Set set;

    private InventoryService inventoryService;

    @Autowired
    private SetService setService;

    @Autowired
    public SetView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        generalInfo = new SetGeneralInfo(inventoryService);
        generalInfo.setHeight("50%");
        partsGrid = new PartsListComponent();
        content.add(generalInfo, partsGrid);
        add(content);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String setNumber) {
        set = setService.getSetBy(setNumber);
        generalInfo.fillWith(set);
        partsGrid.setItems(inventoryService.getPartsForSetNumber(set.getSetNumber()));
    }

    private HorizontalLayout getGeneralInfo() {
        HorizontalLayout generalInfo = new HorizontalLayout();
        Image image = new Image(set.getImageUrl(), "image");
        generalInfo.add(image);
        return generalInfo;
    }
}
