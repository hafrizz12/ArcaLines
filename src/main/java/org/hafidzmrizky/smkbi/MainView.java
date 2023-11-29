package org.hafidzmrizky.smkbi;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.hafidzmrizky.smkbi.view.Airport;
import org.hafidzmrizky.smkbi.view.CreateBooking;
import org.hafidzmrizky.smkbi.view.FlightView;
import org.hafidzmrizky.smkbi.view.PlaneView;
import org.hafidzmrizky.smkbi.view.auth.Login;

import java.io.IOException;
import java.net.URL;

@Route
@CssImport("font.css")
public class MainView extends AppLayout {

    public MainView() {
        if (VaadinSession.getCurrent().getAttribute("role") == null) {
            UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/login\", \"_self\");");
        } else if (VaadinSession.getCurrent().getAttribute("role").equals("user")) {
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
                new RouterLink("Flight Management", FlightView.class),
                new RouterLink("Create Booking", CreateBooking.class),
                new RouterLink("Plane", PlaneView.class),
                new RouterLink("Airport", Airport.class)
        ));
    }

}
