package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.inventories.entity.UserInventoryMiniFig;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFigureService;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
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
public class MyMiniFiguresSearchView extends MainView {

    private final VerticalLayout content = new VerticalLayout();
    private final HorizontalLayout searchBar = new HorizontalLayout();
    private final Grid<UserInventoryMiniFig> setsGrid = new Grid();

    private final MiniFigureService miniFigureService;

    private final InventoryService inventoryService;

    @Autowired
    public MyMiniFiguresSearchView(MiniFigureService miniFigureService, InventoryService inventoryService) {
        this.miniFigureService = miniFigureService;
        this.inventoryService = inventoryService;
        setGrid();
        setSearchBar();
        content.add(searchBar, setsGrid);
        add(content);
    }

    private void setGrid() {
        setsGrid.setHeightByRows(true);
        setsGrid.addItemClickListener(e -> UI.getCurrent().navigate(MyMiniFigureView.class, e.getItem().getId().toString()));

        setsGrid.addColumn(UserInventoryMiniFig::getId).setHeader("id");
        setsGrid.addComponentColumn(this::setImageOnGrid).setHeader("afbeelding");
        setsGrid.addColumn(x -> x.getInventory().getSetNumber()).setHeader("id");
        setsGrid.addColumn(this::getSetName).setHeader("afbeelding");
        setsGrid.addColumn(UserInventoryMiniFig::isComplete).setHeader("volledig");
        setsGrid.addColumn(UserInventoryMiniFig::isSold).setHeader("verkocht");

        User user = (User) VaadinSession.getCurrent().getAttribute("user");
        List<UserInventoryMiniFig> userInventorySetByUser = inventoryService.getUserInventoryMiniFigureByUser(user);
        setsGrid.setItems(userInventorySetByUser);
    }

    private String getSetName(UserInventoryMiniFig userInventoryMiniFig) {
        return miniFigureService.getMiniFigureBy(userInventoryMiniFig.getInventory().getSetNumber()).getName();
    }

    private Image setImageOnGrid(UserInventoryMiniFig userInventorySet) {
        Image image = new Image();
        MiniFigure miniFigure = miniFigureService.getMiniFigureBy(userInventorySet.getInventory().getSetNumber());
        if (Objects.nonNull(miniFigure.getImageUrl())) {
            image.setSrc(miniFigure.getImageUrl());
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
        setsGrid.setItems(inventoryService.getUserInventoryMiniFigureByUserAndFigNumberContains(user, setNumber));
    }
}
