package org.hafidzmrizky.smkbi.view.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.dao.BookingDao;
import org.hafidzmrizky.smkbi.model.Booking;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Search Booking")
@Route(value = "admin/search-booking", layout = SideMenu.class)
public class SearchBooking extends VerticalLayout {

    private static BookingDao bookingDao;

    private Optional<Connection> connection;

    public SearchBooking() {
        bookingDao = new BookingDao();
        topHeader();
        gridBookingData();
    }



    private void gridBookingData() {

        setAlignItems(Alignment.CENTER);


        Grid<Booking> grid = new Grid<>(Booking.class, false);
        grid.addColumn(Booking::getId).setHeader("ID");
        grid.addColumn(Booking::getScheduleId).setHeader("Schedule ID");
        grid.addColumn(Booking::getName).setHeader("Nama");
        grid.addColumn(Booking::getPrice).setHeader("Price");

        TextField searchField = new TextField("Cari berdasarkan kode booking");
        searchField.setWidth("300px");
        Button searchButton = new Button("Cari");

        Collection<Booking> bookingz = bookingDao.getAll();
        grid.setItems(bookingz);
        searchButton.addClickListener(event2 -> {
            if (searchField.getValue().isEmpty()) {
                Collection<Booking> bookings = bookingDao.getAll();
                grid.setItems(bookings);
            } else {
                Optional<Booking> bookings = bookingDao.get(Integer.parseInt(searchField.getValue()));
                Collection<Booking> booking = bookings.map(Stream::of).orElseGet(Stream::empty).toList();
                grid.setItems(booking);
            }
        });





        add(searchField, searchButton, grid);
    }

    private void topHeader() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        H1 title = new H1("Search Booking Data");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        topLayout.add(title);
        add(topLayout);
    }


}
