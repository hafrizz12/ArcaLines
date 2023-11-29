package org.hafidzmrizky.smkbi.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.MainView;
import org.hafidzmrizky.smkbi.dao.LocationDao;
import org.hafidzmrizky.smkbi.dao.PlaneDao;
import org.hafidzmrizky.smkbi.model.Location;

import java.util.Collection;


@PageTitle("Airport")
@Route(value = "airport", layout = MainView.class)
public class Airport extends VerticalLayout {

    private LocationDao airportLocation;
    private PlaneDao planeDao;

    public Airport() {
        airportLocation = new LocationDao();
        planeDao = new PlaneDao();

        topAirportView();
        locationCard();

    }

    private void locationCard() {
        HorizontalLayout horiLocation = new HorizontalLayout();
        horiLocation.setJustifyContentMode(JustifyContentMode.BETWEEN);
        horiLocation.setWidthFull();

        Div locationFleet = new Div();
        locationFleet.setWidth("calc(100vh - 80px");
        locationFleet.getStyle()
                .set("border", "2px solid #f9f9f9")
                .set("padding", "20px")
                .set("margin", "10px 10px 10px 10px")
                .set("border-radius", "10px");

        H2 locationDisplay = new H2("Fleet Locations");
        locationDisplay.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");

        locationFleet.add(locationDisplay);



        // show only 3



        horiLocation.add(locationFleet);


        add(horiLocation);







    }

    private void topAirportView() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        H1 title = new H1("Airport");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        topLayout.add(title);
        add(topLayout);
    }







}
