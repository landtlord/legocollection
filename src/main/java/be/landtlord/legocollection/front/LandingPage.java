package be.landtlord.legocollection.front;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryRepository;
import be.landtlord.legocollection.inventory.inventories.boundary.PartsInventoryRepository;
import be.landtlord.legocollection.inventory.inventories.entity.Inventory;
import be.landtlord.legocollection.inventory.inventories.entity.PartsInventory;
import be.landtlord.legocollection.inventory.parts.boundary.PartsRepository;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class LandingPage extends VerticalLayout {



    public LandingPage(@Autowired PartsRepository partsRepository, @Autowired InventoryRepository inventoryRepository, @Autowired PartsInventoryRepository partsInventoryRepository) {
        add(new Span("Hello World"));

        TextField test = new TextField();
        test.setLabel("parts");
        Button button = new Button("press me");
        Grid<Part> partGrid = new Grid<>();

        partGrid.addColumn(Part::getPartNumber);
        partGrid.addColumn(Part::getName);

        button.addClickListener(buttonClickEvent -> partGrid.setItems(partsRepository.findByPartNumberContains(test.getValue())));

        add(test, button);
        add(partGrid);

        TextField testSet = new TextField();
        testSet.setLabel("sets");
        Button buttonSet = new Button("press me");
        Grid<Inventory> setGrid = new Grid<>();

        setGrid.addColumn(Inventory::getId);
        setGrid.addColumn(Inventory::getSetNumber);

        buttonSet.addClickListener(buttonClickEvent -> setGrid.setItems(inventoryRepository.findBySetNumberContains(testSet.getValue())));

        add(testSet, buttonSet, setGrid);

        TextField testSetList = new TextField();
        testSetList.setLabel("setsList");
        Button buttonSetList = new Button("press me");
        Grid<PartsInventory> setListGrid = new Grid<>();

        setListGrid.addColumn(PartsInventory::getId);
        setListGrid.addColumn(PartsInventory::getPart);
        setListGrid.addColumn(PartsInventory::isSpare);

        buttonSetList.addClickListener(buttonClickEvent -> setListGrid.setItems(partsInventoryRepository.findAllByInventory(inventoryRepository.findById(Long.parseLong(testSetList.getValue())).get())));

        add(testSetList, buttonSetList, setListGrid);
    }
}
