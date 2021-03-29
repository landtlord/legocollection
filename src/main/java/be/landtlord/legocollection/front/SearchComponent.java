package be.landtlord.legocollection.front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class SearchComponent extends HorizontalLayout {

    private TextField search = new TextField();

    private Button searchButton = new Button("Zoeken");

    public SearchComponent() {

    }
}
