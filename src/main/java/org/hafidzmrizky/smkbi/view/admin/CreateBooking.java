package org.hafidzmrizky.smkbi.view.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.dao.BookingDao;
import org.hafidzmrizky.smkbi.dao.LocationDao;
import org.hafidzmrizky.smkbi.dao.ScheduleDao;
import org.hafidzmrizky.smkbi.model.Booking;
import org.hafidzmrizky.smkbi.model.Location;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Create Booking")
@Route(value = "admin/create-booking", layout = SideMenu.class)

public class CreateBooking extends VerticalLayout {

    private final LocationDao locationDao;
    private static BookingDao bookingDao;
    private final ScheduleDao scheduleDao;
    public CreateBooking() {
        locationDao = new LocationDao();
        scheduleDao = new ScheduleDao();
        bookingDao = new BookingDao();
        topHeader();
        createForm();

    }

    private static class createBookingFormLayout extends FormLayout {
        private final TextField idTextField = new TextField();
        private final TextField fromTextField = new TextField("Dari");
        private final TextField toTextField = new TextField("Ke");
        private final DatePicker departureDatePicker = new DatePicker("Tanggal Keberangkatan");
        private final TextField nameTextField = new TextField("Nama");
        private final TextField priceTextField = new TextField("Harga");
        private final Button saveBooking = new Button("Simpan");

        public createBookingFormLayout() {
            idTextField.setVisible(false);
            add(idTextField);
            fromTextField.setReadOnly(true);
            add(fromTextField);
            toTextField.setReadOnly(true);
            add(toTextField);
            departureDatePicker.setReadOnly(true);
            Stream.of(fromTextField, toTextField, departureDatePicker, nameTextField, priceTextField, saveBooking)
                    .forEach(this::add);
            saveBooking.addClickListener(clickEvent -> {
                Booking booking = new Booking();
                booking.setScheduleId(Long.parseLong(idTextField.getValue()));
                booking.setName(nameTextField.getValue());
                booking.setPrice(Long.parseLong(priceTextField.getValue()));
                Optional<Integer> id = bookingDao.save(booking);
                id.ifPresent(integer -> {
                    ConfirmDialog confirmDialog = new ConfirmDialog();
                    confirmDialog.setText("Booking created with id " + integer);
                    confirmDialog.setCancelable(false);
                    confirmDialog.setRejectable(false);
                    confirmDialog.setConfirmText("OK");
                    confirmDialog.addConfirmListener(confirmEvent -> {
                        confirmDialog.close();
                        Notification notification = new Notification(
                                "Booking was created with the Booking ID of " + integer, 3000,
                                Notification.Position.TOP_END);
                        notification.open();
                    });
                    add(confirmDialog);
                    confirmDialog.open();
                });
            });
        }

        public void setScheduleDTO(ScheduleDTO scheduleDTO) {
            idTextField.setValue(String.valueOf(scheduleDTO.getId()));
            fromTextField.setValue(scheduleDTO.getDepartureLocation());
            toTextField.setValue(scheduleDTO.getArrivalLocation());
            departureDatePicker.setValue(LocalDate.parse(scheduleDTO.getDepartureDate().toString()));
        }

    }

    private static ComponentRenderer<createBookingFormLayout, ScheduleDTO> createBookingRenderer() {
        return new ComponentRenderer<>(createBookingFormLayout::new, createBookingFormLayout::setScheduleDTO);
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
        grid.setItemDetailsRenderer(createBookingRenderer());
        add(fromComboBox, toComboBox, departureDatePicker, arrivalDatePicker, searchButton, grid);

    }

}
