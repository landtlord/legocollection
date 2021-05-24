package be.landtlord.legocollection.front.parts;

import be.landtlord.legocollection.inventory.inventories.entity.InventoryParts;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.Objects;

public class PartsListComponent extends Grid<InventoryParts> {
    public PartsListComponent() {
        setWidthFull();
        addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        addColumn(ip -> ip.getPart().getPartNumber()).setHeader("Part nummer");
        addColumn(ip -> ip.getPart().getName()).setHeader("Naam");
        addColumn(ip -> ip.getPart().getMaterial()).setHeader("Materiaal");
        addColumn(ip -> ip.getColor().getId()).setHeader("Kleur id");
        addColumn(ip -> ip.getColor().getName()).setHeader("Kleur naam");
        addColumn(InventoryParts::getQuantity).setHeader("Aantal");
        addComponentColumn(this::getSpareBox).setHeader("afbeelding");
    }

    private Icon getSpareBox(InventoryParts inventoryParts) {
        Icon icon = new Icon(VaadinIcon.CHECK_CIRCLE);
        icon.setColor("green");
        icon.setVisible(inventoryParts.isSpare());
        return icon;
    }

    private Image setImageOnGrid(InventoryParts inventoryParts) {
        Image image = new Image();
        if (Objects.nonNull(inventoryParts.getPart().getImageUrl())) {
            image.setSrc(inventoryParts.getPart().getImageUrl());
        }
        image.setWidth("75px");
        image.setHeight("75px");
        return image;
    }
}
