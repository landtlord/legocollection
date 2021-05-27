package be.landtlord.legocollection.front.set;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.front.parts.MyPartListComponent;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.inventory.sets.boundary.SetService;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MySetView extends MainView implements HasUrlParameter<String> {
    private final VerticalLayout content = new VerticalLayout();

    private UserInventorySet userInventorySet;

    private final MySetGeneralInfo setGeneralInfo;

    private final InventoryService inventoryService;

    private final MyPartListComponent parts;

    @Autowired
    private SetService setService;

    @Autowired
    public MySetView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setGeneralInfo = new MySetGeneralInfo(inventoryService);
        parts = new MyPartListComponent(inventoryService);
        setGeneralInfo.setHeight("30%");
        content.add(setGeneralInfo, parts);
        add(content);
    }

    @Override
    public void setParameter(BeforeEvent event, String id) {
        userInventorySet = inventoryService.getUserInventorySetBy(id);
        Set set = setService.getSetBy(userInventorySet.getInventory().getSetNumber());
        setGeneralInfo.fillWith(set);
        parts.setItems(inventoryService.getUserInventoryPartsByUserInventorySet(userInventorySet).stream());
    }
}
