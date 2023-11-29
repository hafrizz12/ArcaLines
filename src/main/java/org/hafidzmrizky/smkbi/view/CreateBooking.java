package org.hafidzmrizky.smkbi.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.MainView;
import org.hafidzmrizky.smkbi.dao.LocationDao;
import org.hafidzmrizky.smkbi.dao.ScheduleDao;
import org.hafidzmrizky.smkbi.model.Location;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;


@PageTitle("Create Booking")
@Route(value = "booking", layout = MainView.class)
public class BookingView extends VerticalLayout {

    private final LocationDao locationDao;
    private final ScheduleDao scheduleDao;
    public BookingView() {
        locationDao = new LocationDao();
        scheduleDao = new ScheduleDao();
        topHeader();
        createForm();
    }

    private void topHeader() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        H1 title = new H1("Create Booking");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xxl)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        topLayout.add(title);
        add(topLayout);
    }


    private void createForm() {

        setAlignItems(Alignment.STRETCH);

        ComboBox<Location> fromComboBox = new ComboBox<>("Dari");
        fromComboBox.setItems(locationDao.getAll());
        fromComboBox.setItemLabelGenerator(Location::getName);

        ComboBox<Location> toComboBox = new ComboBox<>("Ke");
        toComboBox.setItems(locationDao.getAll());
        toComboBox.setItemLabelGenerator(Location::getName);

        DatePicker departureDatePicker = new DatePicker("Tanggal Keberangkatan");
        DatePicker arrivalDatePicker = new DatePicker("Tanggal Kepulangan");
        Button searchButton = new Button("Search");
        searchButton.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY
        );

        Grid<ScheduleDTO> grid = new Grid<>(ScheduleDTO.class, false);
        grid.addColumn(ScheduleDTO::getId).setHeader("id");
        grid.addColumn(ScheduleDTO::getFlightNumber).setHeader("Nomor Pesawat");
        grid.addColumn(ScheduleDTO::getDepartureLocation).setHeader("Keberangkatan");
        grid.addColumn(ScheduleDTO::getArrivalLocation).setHeader("Kedatangan");
        grid.addColumn(ScheduleDTO::getDepartureDate).setHeader("Waktu Keberangkatan");

        searchButton.addClickListener(clickEvent -> {
            Collection<ScheduleDTO> scheduleDTOCollection = scheduleDao.searchSchedule(
                    fromComboBox.getValue().getId(),
                    toComboBox.getValue().getId(),
                    Date.from(departureDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            grid.setItems(scheduleDTOCollection);
        });

        add(fromComboBox, toComboBox, departureDatePicker, arrivalDatePicker, searchButton, grid);

    }

}

