package be.landtlord.legocollection.front.minifigure;

import be.landtlord.legocollection.front.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route
public class MyMiniFiguresSearchView extends MainView {
    public MyMiniFiguresSearchView() {
        add(new Span("my minifigures search view"));
    }
}
