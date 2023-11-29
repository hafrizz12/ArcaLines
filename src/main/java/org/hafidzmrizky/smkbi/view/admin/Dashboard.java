package org.hafidzmrizky.smkbi.view.admin;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.dao.BookingDao;
import org.hafidzmrizky.smkbi.core.FlightCore;

import java.text.NumberFormat;
import java.util.Locale;

@PageTitle("Dashboard Admin")
@Route(value = "admin/dashboard", layout = SideMenu.class)
public class Dashboard extends VerticalLayout {


    private static BookingDao bookingDao;

    public Dashboard() {
        bookingDao = new BookingDao();
        topHeader();
        insights();
    }

    private void insights() {
        HorizontalLayout insightsLayout = new HorizontalLayout();
        insightsLayout.setWidthFull();
        insightsLayout.setHeightFull();

        Div ticketSales = new Div();
        ticketSales.getStyle()
                .set("border", "3px solid #f9f9f9")
                .set("padding", "10px")
                .set("margin", "10px 10px 10px 10px")
                .set("border-radius", "10px");

        H2 ticketSalesTitle = new H2("Ticket Sales");
        ticketSalesTitle.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        ticketSales.add(ticketSalesTitle);

        Paragraph ticketSalesParagraph = new Paragraph(NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(FlightCore.totalTicketSales()));
        ticketSalesParagraph.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "500");
        ticketSales.add(ticketSalesParagraph);

        Div totalBookings = new Div();
        totalBookings.getStyle()
                .set("border", "3px solid #f9f9f9")
                .set("padding", "10px")
                .set("margin", "10px 10px 10px 10px")
                .set("border-radius", "10px");

        H2 totalBookingsTitle = new H2("Total Bookings");
        totalBookingsTitle.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");

        totalBookings.add(totalBookingsTitle);

        Paragraph totalBookingsParagraph = new Paragraph(String.valueOf(bookingDao.getAll().size()));
        totalBookingsParagraph.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "500");

        totalBookings.add(totalBookingsParagraph);

        insightsLayout.add(totalBookings);
        insightsLayout.add(ticketSales);
        add(insightsLayout);
    }



    private void topHeader() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        H1 title = new H1("Dashboard");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        topLayout.add(title);
        add(topLayout);
    }

}
