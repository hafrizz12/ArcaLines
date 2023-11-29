package org.hafidzmrizky.smkbi.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.MainView;
import org.hafidzmrizky.smkbi.dao.PlaneDao;

@PageTitle("Flight")
@Route(value = "flight", layout = MainView.class)
@CssImport("font.css")
public class FlightView extends VerticalLayout {

    private PlaneDao planeDao;


    public FlightView() {
        planeDao = new PlaneDao();
        topFlightView();
        flightCompMain();
    }

    private void topFlightView()  {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        H1 title = new H1("Flights");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        topLayout.add(title);
        add(topLayout);
    }

    private void flightCompMain() {
        HorizontalLayout flightCompMain = new HorizontalLayout();
        flightCompMain.setWidthFull();

        H3 flightComp = new H3("Fleets");
        flightComp.getStyle()
                .set("font-size", "var(--lumo-font-size-xl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");

        add(flightComp);

        // make div, add border small of #f9f9f9, add padding 10px, add margin 10px, add border radius 10px, h4, searchbox
        Div flightCompDiv = new Div();
        flightCompDiv.setWidthFull();
        flightCompDiv.getStyle()
                .set("border", "1px solid #f9f9f9")
                .set("padding", "10px")
                .set("margin", "10px")
                .set("border-radius", "10px");
        H2 flightCompDivH3 = new H2("E-Fleet Information");
        flightCompDivH3.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        flightCompDiv.add(flightCompDivH3);

        ComboBox<PlaneView> field = new ComboBox<>("Plane Model");
        field.setPlaceholder("Plane Model");



        add(flightCompDiv);






    }

}


