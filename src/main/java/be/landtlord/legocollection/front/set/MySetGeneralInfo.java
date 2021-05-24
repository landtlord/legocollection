package be.landtlord.legocollection.front.set;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import be.landtlord.legocollection.user.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import java.util.Objects;

public class MySetGeneralInfo extends HorizontalLayout {
    Image setImage;

    VerticalLayout info;

    private InventoryService inventoryService;

    public MySetGeneralInfo(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setImage = new Image();
        setImage.setAlt("Image not found");
        info = new VerticalLayout();
        add(setImage, info);
    }

    public void fillWith(Set set) {
        if (Objects.nonNull(set.getImageUrl())) {
            setImage.setSrc(set.getImageUrl());
        }
        info.add(new Span("setnummer: " + set.getSetNumber()));
    }
}
