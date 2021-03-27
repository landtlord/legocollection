package be.landtlord.legocollection.front;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class LandingPage extends VerticalLayout {
    public LandingPage(){
        add(new Span("Hello World"));
    }
}
