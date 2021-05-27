package be.landtlord.legocollection.front.set;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventorySet;
import be.landtlord.legocollection.inventory.sets.boundary.SetService;
import be.landtlord.legocollection.inventory.sets.entity.Set;
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
public class MySetSearchView extends MainView {

    private VerticalLayout content = new VerticalLayout();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Grid<UserInventorySet> setsGrid = new Grid();

    private InventoryService inventoryService;

    private SetService setService;

    @Autowired
    public MySetSearchView(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        setGrid();
        setSearchBar();
        content.add(searchBar, setsGrid);
        add(content);
    }

    private void setGrid() {
        setsGrid.setHeightByRows(true);
        setsGrid.addItemClickListener(e-> UI.getCurrent().navigate(MySetView.class, e.getItem().getId().toString()));

        setsGrid.addColumn(UserInventorySet::getId).setHeader("id");
        setsGrid.addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        setsGrid.addColumn(x -> x.getInventory().getSetNumber()).setHeader("id");
        setsGrid.addColumn(this::getSetName).setHeader("afbeelding");
        setsGrid.addColumn(UserInventorySet::isComplete).setHeader("volledig");
        setsGrid.addColumn(UserInventorySet::isSold).setHeader("verkocht");

        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        List<UserInventorySet> userInventorySetByUser = inventoryService.getUserInventorySetByUser(user);
        setsGrid.setItems(userInventorySetByUser);
    }

    private String getSetName(UserInventorySet userInventorySet) {
        return setService.getSetBy(userInventorySet.getInventory().getSetNumber()).getName();
    }

    private Image setImageOnGrid(UserInventorySet userInventorySet) {
        Image image = new Image();
        Set set = setService.getSetBy(userInventorySet.getInventory().getSetNumber());
        if (Objects.nonNull(set.getImageUrl())) {
            image.setSrc(set.getImageUrl());
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

    private void updateList(String setNumber) {
        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        setsGrid.setItems(inventoryService.getUserInventorySetByUserAndSetNumberContains(user, setNumber));
    }
}
