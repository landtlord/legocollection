package be.landtlord.legocollection.front.set;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryPart;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route
public class MySetView extends MainView implements HasUrlParameter<String> {
    private VerticalLayout content = new VerticalLayout();

    private UserInventorySet userInventorySet;

    private MySetGeneralInfo setGeneralInfo;

    private InventoryService inventoryService;

    private Grid<UserInventoryPart> parts;

    @Autowired
    public MySetView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setGeneralInfo = new MySetGeneralInfo(inventoryService);
        setGeneralInfo.setHeight("30%");
        setupPartsGrid();
        content.add(setGeneralInfo, parts);
        add(content);
    }

    private void setupPartsGrid() {
        parts = new Grid();
        parts.addComponentColumn(this::getPartImage);
        parts.addColumn(x -> x.getInventoryParts().getPart().getPartNumber());
        parts.addColumn(x -> x.getInventoryParts().getPart().getName());
        parts.addColumn(x -> x.getInventoryParts().getColor().getName());
        parts.addComponentColumn(this::getPartsCount);
        parts.addColumn(x -> x.getInventoryParts().getQuantity());
        parts.addComponentColumn(this::getSpareBox);
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

    @Override
    public void setParameter(BeforeEvent event, String id) {
        userInventorySet = inventoryService.getUserInventorySetBy(id);
        Set set = inventoryService.getSetBy(userInventorySet.getInventory().getSetNumber());
        setGeneralInfo.fillWith(set);
        parts.setItems(inventoryService.getUserInventoryPartsByUserInventorySet(userInventorySet).stream());
    }
}
