package be.landtlord.legocollection.front.parts;

import be.landtlord.legocollection.front.MainView;
import be.landtlord.legocollection.inventory.parts.boundary.PartsService;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route
public class PartView extends MainView implements HasUrlParameter<String> {
    private final HorizontalLayout content = new HorizontalLayout();

    private final VerticalLayout info = new VerticalLayout();

    private final Image image = new Image();

    private Part part;

    @Autowired
    private PartsService partsService;

    public PartView() {
        content.add(image, info);
        add(content);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String partNumber) {
        part = partsService.getPartBy(partNumber);
        if (Objects.nonNull(part.getImageUrl())) {
            image.setSrc(part.getImageUrl());
        }
        info.add(new Span("naam: " + part.getName()));
    }
}
