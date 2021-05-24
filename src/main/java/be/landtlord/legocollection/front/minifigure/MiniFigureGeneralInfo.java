package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Objects;

public class MiniFigureGeneralInfo extends HorizontalLayout {
    Image image;

    VerticalLayout info;

    public MiniFigureGeneralInfo() {
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
        info.add(new Button("Toevoegen aan mijn sets"));
    }
}
