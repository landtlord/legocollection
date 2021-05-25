package be.landtlord.legocollection.front.parts;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryPart;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class MyPartListComponent extends Grid<UserInventoryPart> {
    private InventoryService inventoryService;

    public MyPartListComponent(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        addComponentColumn(this::getPartImage)
                .setHeader("Afbeelding");
        addColumn(x -> x.getInventoryParts().getPart().getPartNumber())
                .setHeader("Part number")
                .setSortable(true);
        addColumn(x -> x.getInventoryParts().getPart().getName())
                .setHeader("Part naam")
                .setSortable(true);
        addColumn(x -> x.getInventoryParts().getColor().getName())
                .setHeader("Kleur")
                .setSortable(true);
        addComponentColumn(this::getPartsCount)
                .setHeader("Aantal in bezit");
        addColumn(x -> x.getInventoryParts().getQuantity())
                .setHeader("Aantal in nodig");
        addComponentColumn(this::getSpareBox)
                .setHeader("Reserve");
    }

    private NumberField getPartsCount(UserInventoryPart userInventoryPart) {
        Binder<UserInventoryPart> binder = new Binder<>();
        NumberField numberField = new NumberField();
        numberField.setHasControls(true);
        numberField.setMax(userInventoryPart.getInventoryParts().getQuantity());
        numberField.setMin(0d);
        binder.bind(numberField, userInventoryPart1 -> (double) userInventoryPart1.getQuantity(), (userInventoryPart2, quantity) -> userInventoryPart2.setQuantity(quantity.intValue()));
        binder.readBean(userInventoryPart);
        numberField.addValueChangeListener(e -> {
            try {
                binder.writeBean(userInventoryPart);
                inventoryService.updateUserInventoryPart(userInventoryPart);
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });
        return numberField;
    }

    private Icon getSpareBox(UserInventoryPart userInventoryParts) {
        Icon icon = new Icon(VaadinIcon.CHECK_CIRCLE);
        icon.setColor("green");
        icon.setVisible(userInventoryParts.getInventoryParts().isSpare());
        return icon;
    }

    private Image getPartImage(UserInventoryPart userInventoryPart) {
        Image image = new Image();
        String imageUrl = userInventoryPart.getInventoryParts().getPart().getImageUrl();
        if (Objects.nonNull(imageUrl)) {
            image.setSrc(imageUrl);
        }
        image.setWidth("75px");
        image.setHeight("75px");
        return image;
    }
}
