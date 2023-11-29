package org.hafidzmrizky.smkbi.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("Logout")
@Route(value = "logout")
public class Logout extends VerticalLayout {
    public Logout() {
        VaadinSession.getCurrent().close();
        getUI().ifPresent(ui -> ui.navigate("login"));
    }
}
