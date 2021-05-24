package be.landtlord.legocollection.front;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route
public class AccountView extends MainView{
    public AccountView() {
        add(new Span("Accountview"));
    }
}
