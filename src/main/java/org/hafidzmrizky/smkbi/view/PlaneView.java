package org.hafidzmrizky.smkbi.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hafidzmrizky.smkbi.MainView;
import org.hafidzmrizky.smkbi.dao.LocationDao;
import org.hafidzmrizky.smkbi.dao.PlaneDao;
import org.hafidzmrizky.smkbi.model.Location;
import org.hafidzmrizky.smkbi.model.Plane;

import java.util.Collection;
import java.util.Optional;

@PageTitle("Plane")
@Route(value = "plane", layout = MainView.class)
public class PlaneView extends VerticalLayout {

    private PlaneDao planeDao;
    private LocationDao location;

    public PlaneView() {
        planeDao = new PlaneDao();
        topFlightView();
        flightCompMain();
    }

    private void topFlightView()  {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        H1 title = new H1("Planes");
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

        Div flightCompDiv = new Div();
        flightCompDiv.setWidth("calc(100vh - 20px)");
        flightCompDiv.getStyle()
                .set("border", "1px solid #f9f9f9")
                .set("padding", "10px")
                .set("margin", "10px 10px 10px 10px")
                .set("border-radius", "10px");
        H2 flightCompDivH3 = new H2("E-Fleet Information");
        flightCompDivH3.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        flightCompDiv.add(flightCompDivH3);

        HorizontalLayout planeBar = new HorizontalLayout();
        planeBar.setWidthFull();
        planeBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        planeBar.setAlignItems(Alignment.END);
        ComboBox<Plane> fleet = new ComboBox<>("Plane Model");
        fleet.setPlaceholder("Plane Model");
        fleet.setItems(planeDao.getAll());
        fleet.setItemLabelGenerator(Plane::getName);
        fleet.getStyle()
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700");
        planeBar.add(fleet);
        Button add = new Button("Add");
        add.getStyle()
                .set("background-color", "#F4F4F4")
                .set("color", "#000000")
                .set("margin", "15px");
        add.addClickListener(addEvents -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Add Plane");
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(true);
            VerticalLayout addLayout = new VerticalLayout();
            TextField planeName = new TextField("Plane Name");
            planeName.setPlaceholder("Plane Name");
            TextField planeCapacity = new TextField("Plane Capacity");
            planeCapacity.setPlaceholder("Plane Capacity");
            ComboBox<Location> location = new ComboBox<>("Location");
            location.setItems(new LocationDao().getAll());
            location.setItemLabelGenerator(Location::getName);
            location.setPlaceholder("Location");
            Button save = new Button("Save");
            save.getStyle()
                    .set("background-color", "#F9F9F9")
                    .set("color", "#000000");
            save.addClickListener(event -> {
                Plane plane = new Plane();
                plane.setName(planeName.getValue());
                plane.setCapacity(Integer.parseInt(planeCapacity.getValue()));
                plane.setLocationId((int) location.getValue().getId());
                planeDao.save(plane);
                Notification notification = new Notification(
                        "Plane has been added successfully", 3000,
                        Notification.Position.TOP_CENTER);
                notification.open();
                fleet.setItems(planeDao.getAll());
                dialog.close();
            });
            Button cancel = new Button("Cancel");
            cancel.getStyle()
                    .set("background-color", "#ffffff")
                    .set("color", "#000000");
            cancel.addClickListener(event -> {
                dialog.close();
                    });
            addLayout.add(planeName, planeCapacity, location, save, cancel);
            addLayout.setHorizontalComponentAlignment(Alignment.END, save, cancel);
            dialog.add(addLayout);
            dialog.open();
        });
        planeBar.add(add);
        flightCompDiv.add(planeBar);
        Div planeInfo = new Div();
        fleet.addValueChangeListener(event -> {
            // Clear existing content in flightCompDiv
            planeInfo.removeAll();
            // Get the selected plane
            Plane selectedPlane = event.getValue();
            if (selectedPlane != null) {
                HorizontalLayout planeInfoH = new HorizontalLayout();
                planeInfo.getStyle()
                        .set("border", "1px solid #f9f9f9")
                        .set("padding", "20px")
                        .set("margin", "10px 10px 10px 10px")
                        .set("border-radius", "10px");

                Paragraph planeInfoH2 = new Paragraph("Plane Information");
                planeInfoH2.getStyle()
                        .set("font-size", "var(--lumo-font-size-m)")
                        .set("font-family", "Poppins, sans-serif")
                        .set("font-weight", "500");

                H1 planeInfoH1 = new H1("" + selectedPlane.getName());
                planeInfoH1.getStyle()
                        .set("font-size", "var(--lumo-font-size-xl)")
                        .set("font-family", "Poppins, sans-serif")
                        .set("font-weight", "700");

                Paragraph planeInfoH3 = new Paragraph("Capacity: " + selectedPlane.getCapacity());
                planeInfoH3.getStyle()
                        .set("font-size", "var(--lumo-font-size-m)")
                        .set("font-family", "Poppins, sans-serif")
                        .set("font-weight", "500");

                Paragraph currentLocation = new Paragraph("Current Location: " + selectedPlane.getLocationId());
                currentLocation.getStyle()
                        .set("font-size", "var(--lumo-font-size-m)")
                        .set("font-family", "Poppins, sans-serif")
                        .set("font-weight", "500");

                location = new LocationDao();
                Optional<Location> locs = location.get(selectedPlane.getLocationId());
                locs.ifPresent(location -> {
                    currentLocation.setText("Current Location: " + location.getName());
                });

                planeInfo.add(planeInfoH2, planeInfoH1, planeInfoH3, currentLocation);
                Dialog dialogEdit = new Dialog();
                dialogEdit.setHeaderTitle("Edit Plane");
                dialogEdit.setCloseOnEsc(true);
                dialogEdit.setCloseOnOutsideClick(true);

                VerticalLayout editLayout = editLayout(planeDao, selectedPlane.getId(), selectedPlane.getName(), selectedPlane.getCapacity(), selectedPlane.getLocationId());
                dialogEdit.add(editLayout);
                Button cancel = new Button("Cancel");
                cancel.getStyle()
                        .set("background-color", "#ffffff")
                        .set("color", "#000000");
                cancel.addClickListener(even -> {
                    dialogEdit.close();
                });

                editLayout.setHorizontalComponentAlignment(Alignment.END, cancel);
                editLayout.add(cancel);
                Button edit = new Button("Edit");
                edit.addClickListener(e1 -> {
                    dialogEdit.open();
                });


                Dialog dialogDelete = new Dialog();
                dialogDelete.setHeaderTitle("Confirmation to Delete Plane");
                dialogDelete.setCloseOnEsc(true);
                dialogDelete.setCloseOnOutsideClick(true);
                Button confirmDelete = new Button("Confirm");
                confirmDelete.getStyle()
                        .set("background-color", "#ff0000")
                        .set("color", "#ffffff");
                confirmDelete.addClickListener(event1 -> {
                    planeDao.delete(selectedPlane);
                    dialogDelete.close();
                    Notification notification = new Notification(
                            "Plane has been deleted succesfully", 3000,
                            Notification.Position.TOP_CENTER);
                    notification.open();
                    fleet.setItems(planeDao.getAll());
                });
                Button cancelDelete = new Button("Cancel");
                cancelDelete.getStyle()
                        .set("background-color", "#ffffff")
                        .set("color", "#000000");
                cancelDelete.addClickListener(event1 -> {
                    dialogDelete.close();
                });
                dialogDelete.add(confirmDelete, cancelDelete);

                Button delete = new Button("Delete");
                delete.getStyle()
                        .set("background-color", "#ff0000")
                        .set("color", "#ffffff");
                delete.addClickListener(event1 -> {
                    dialogDelete.open();
                });
                planeInfoH.add(edit, delete);
                planeInfo.add(planeInfoH);
                flightCompDiv.add(planeInfo);
            }
        });


        add(flightCompDiv);
    }

    private VerticalLayout editLayout(PlaneDao planeDao, int id, String name, int capacity, int locationId) {
        TextField planeName = new TextField("Plane Name");
        planeName.setPlaceholder("Plane Name");
        TextField planeCapacity = new TextField("Plane Capacity");
        planeCapacity.setPlaceholder("Plane Capacity");
        ComboBox<Location> location = new ComboBox<>("Location");
        location.setItems(new LocationDao().getAll());
        location.setItemLabelGenerator(Location::getName);
        location.setPlaceholder("Location");
        Button save = new Button("Save");
        save.getStyle()
                .set("background-color", "#F9F9F9")
                .set("color", "#000000");
        save.addClickListener(event -> {
            Plane plane = new Plane();
            plane.setName(planeName.getValue());
            plane.setCapacity(Integer.parseInt(planeCapacity.getValue()));
            plane.setLocationId((int) location.getValue().getId());
            plane.setId(id);
            planeDao.update(plane);
            UI.getCurrent().getPage().reload();
        });
        VerticalLayout editLayout = new VerticalLayout();

        editLayout.add(planeName, planeCapacity, location, save);
        editLayout.setHorizontalComponentAlignment(Alignment.END, save);
        planeName.setValue(name);
        planeCapacity.setValue(String.valueOf(capacity));
        location.setValue(new LocationDao().get(locationId).get());
        return editLayout;
    }

}
