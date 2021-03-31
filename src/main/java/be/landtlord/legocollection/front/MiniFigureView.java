package be.landtlord.legocollection.front;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MiniFigureView extends MainView implements HasUrlParameter<String> {
    private MiniFigure miniFigure;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public void setParameter(BeforeEvent beforeEvent, String setNumber) {
        this.miniFigure = inventoryService.getMiniFigureBy(setNumber);
        add(new Span(miniFigure.toString()));
    }
}
