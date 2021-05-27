package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryMiniFig;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.user.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import java.util.Objects;

public class MiniFigureGeneralInfo extends HorizontalLayout {
    Image image;

    VerticalLayout info;

    private final InventoryService inventoryService;

    public MiniFigureGeneralInfo(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        image = new Image();
        image.setAlt("Image not found");
        info = new VerticalLayout();
        add(image, info);
    }

    public void fillWith(MiniFigure set) {
        if (Objects.nonNull(set.getImageUrl())) {
            image.setSrc(set.getImageUrl());
        }
        info.add(new Span("setnummer: " + set.getMiniFigureNumber()));
        Button addToMyList = new Button("Toevoegen aan mijn sets");
        addToMyList.addClickListener(e -> saveAndGotoPage(set));
        info.add(addToMyList);
    }

    private void saveAndGotoPage(MiniFigure set) {
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        UserInventoryMiniFig userInventoryMiniFig = inventoryService.addToMyList(set, user);
        UI.getCurrent().navigate(MyMiniFigureView.class, userInventoryMiniFig.getId().toString());
    }
}
