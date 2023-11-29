package org.hafidzmrizky.smkbi.view.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.hafidzmrizky.smkbi.view.*;

import java.io.IOException;
import java.net.URL;

@Route(value = "admin")
@CssImport("font.css")
public class SideMenu extends AppLayout {

    public SideMenu() {
        if (VaadinSession.getCurrent().getAttribute("role") == null) {
            UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/login\", \"_self\");");
        } else if (VaadinSession.getCurrent().getAttribute("role").equals("admin")) {
            createDrawer();
            setPrimarySection(Section.DRAWER);
        } else {
            UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/admin/\", \"_self\");");
        }
    }

    private void createDrawer() {
        StreamResource imageResource = new StreamResource("myimage.png", () -> {
            try {
                URL url = new URL("https://is3.cloudhost.id/eventnimz-jktstrg/eventnimz-jktstrg/19/arcalines-logo%20final2%202.png");
                return url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });

        Image image = new Image(imageResource, "ArcaLines Logo");
        VerticalLayout layoutCenter = new VerticalLayout();
        layoutCenter.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layoutCenter.add(image);

        addToDrawer(new VerticalLayout(
                layoutCenter,
                new RouterLink("Dashboard", Dashboard.class),
                new RouterLink("Create Booking", CreateBooking.class),
                new RouterLink("Plane", PlaneView.class),
                new RouterLink("Search Booking", SearchBooking.class),
                new RouterLink("Logout", Logout.class)
        ));
    }



}
