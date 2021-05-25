package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.front.parts.MyPartListComponent;
import be.landtlord.legocollection.front.parts.PartsListComponent;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryMiniFig;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryPart;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MyMiniFigureView extends MainView implements HasUrlParameter<String> {
    private VerticalLayout content = new VerticalLayout();

    private UserInventoryMiniFig userInventoryMiniFig;

    private MiniFigureGeneralInfo generalInfo;

    private MyPartListComponent partsGrid;


    private InventoryService inventoryService;

    @Autowired
    public MyMiniFigureView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        generalInfo = new MiniFigureGeneralInfo(inventoryService);
        generalInfo.setHeight("50%");
        partsGrid = new MyPartListComponent(inventoryService);
        content.add(generalInfo, partsGrid);
        add(content);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String id) {
        userInventoryMiniFig = inventoryService.getUserInventoryMiniFigureBy(id);
        MiniFigure miniFigure = inventoryService.getMiniFigureBy(userInventoryMiniFig.getInventory().getSetNumber());
        generalInfo.fillWith(miniFigure);
        partsGrid.setItems(inventoryService.getUserInventoryPartsByUserInventoryMiniFigure(userInventoryMiniFig));
    }

    private HorizontalLayout getGeneralInfo() {
        MiniFigure miniFigure = inventoryService.getMiniFigureBy(userInventoryMiniFig.getInventory().getSetNumber());
        HorizontalLayout generalInfo = new HorizontalLayout();
        Image image = new Image(miniFigure.getImageUrl(), "image");
        generalInfo.add(image);
        return generalInfo;
    }
}
