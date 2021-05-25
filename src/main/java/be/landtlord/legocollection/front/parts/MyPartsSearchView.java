package be.landtlord.legocollection.front.parts;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.front.set.MySetView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryPart;
import be.landtlord.legocollection.user.entity.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Route
public class MyPartsSearchView extends MainView {
    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<UserInventoryPart> grid = new Grid();

    private InventoryService inventoryService;

    @Autowired
    public MyPartsSearchView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setGrid();
        setSearchBar();
        content.add(searchBar, grid);
        add(content);
    }


    private void setGrid() {
        grid.setHeightByRows(true);
        grid.addItemClickListener(e-> UI.getCurrent().navigate(MySetView.class, e.getItem().getId().toString()));

        grid.addColumn(UserInventoryPart::getId).setHeader("id");
        grid.addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        grid.addColumn(x -> x.getInventoryParts().getPart().getPartNumber()).setHeader("part nummer");
        grid.addColumn(x -> x.getInventoryParts().getPart().getName()).setHeader("naam");
        grid.addColumn(x -> x.getInventoryParts().getColor().getName()).setHeader("kleur");
        grid.addColumn(UserInventoryPart::getQuantity).setHeader("aantal");
        grid.addColumn(UserInventoryPart::isSold).setHeader("verkocht");


        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        List<UserInventoryPart> userInventoryPartByUser = inventoryService.getUserInventoryPartWithStockByUser(user);
        grid.setItems(userInventoryPartByUser);
    }

    private Image setImageOnGrid(UserInventoryPart userInventoryPart) {
        Image image = new Image();
        String imageUrl = userInventoryPart.getInventoryParts().getPart().getImageUrl();
        if (Objects.nonNull(imageUrl)) {
            image.setSrc(imageUrl);
        }
        image.setWidth("75px");
        image.setHeight("75px");
        return image;
    }

    private void setSearchBar() {
        searchBar.setWidthFull();
        TextField search = new TextField();
        search.setPlaceholder("Filter");
        search.setWidth("60%");
        setWidthFull();
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.addValueChangeListener(e -> updateList(search.getValue()));
        searchBar.add(search);
    }

    private void updateList(String partNumber) {
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        grid.setItems(inventoryService.getUserInventoryPartByUserAndPartNumberContains(user, partNumber));
    }
}
