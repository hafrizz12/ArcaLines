package org.hafidzmrizky.smkbi.view.auth;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.hafidzmrizky.smkbi.dao.auth.Authentication;
import org.hafidzmrizky.smkbi.model.User;

import java.util.Optional;

@PageTitle("Daftar")
@Route(value = "register")
@CssImport("font.css")
public class Register extends VerticalLayout {
    public Register() {
        registerForm();
    }

    private void registerForm() {
        VerticalLayout registerForm = new VerticalLayout();
        registerForm.setWidthFull();
        registerForm.setAlignSelf(Alignment.CENTER);
        registerForm.setAlignItems(Alignment.CENTER);

        System.out.println(VaadinSession.getCurrent().getAttribute("role"));
        if (VaadinSession.getCurrent().getAttribute("role") != null) {
            if (VaadinSession.getCurrent().getAttribute("role").equals("admin")) {
                UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/admin/dashboard\", \"_self\");");
            }

            if (VaadinSession.getCurrent().getAttribute("role") == "user") {
                UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/dashboard\", \"_self\");");
            }
        }


        TextField fullName = new TextField("Nama Lengkap");
        fullName.setWidth("300px");
        TextField username = new TextField("Username");
        username.setWidth("300px");
        PasswordField password = new PasswordField("Password");
        password.setWidth("300px");
        TextField phoneNumber = new TextField("Nomor Telepon");
        phoneNumber.setWidth("300px");
        Button register = new Button("Daftar");
        Notification notification = new Notification();
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.TOP_END); // hafidzmrizky xi rpl yg buat :D
        register.addClickListener(e -> {
            if (fullName.getValue().isEmpty() || username.getValue().isEmpty() || password.getValue().isEmpty() || phoneNumber.getValue().isEmpty()) {
                notification.setText("Mohon isi semua field");
                notification.open();
            } else {
                Authentication authentication = new Authentication();

                authentication.register(username.getValue(), password.getValue(), fullName.getValue(), phoneNumber.getValue());
                notification.setText("Berhasil mendaftar");
                notification.open();

                Optional<User> user = authentication.login(username.getValue(), password.getValue());
                if (user.isEmpty()) {
                    notification.setText("Gagal mendaftar, sudah terdaftar!");
                    notification.open();
                }


                user.ifPresent(value -> {
                    VaadinSession.getCurrent().setAttribute("user", value);
                    VaadinSession.getCurrent().setAttribute("username", value.getUsername());
                    VaadinSession.getCurrent().setAttribute("Full Name", value.getFullName());
                    VaadinSession.getCurrent().setAttribute("role", value.getRole());
                    getUI().ifPresent(ui -> ui.navigate("dashboard"));
                });

            }
        });

        Button login = new Button("Login");
        login.addClickListener(e -> {
            UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/login\", \"_self\");");
        });

        registerForm.add(fullName, username, password, phoneNumber, register, login);
        add(registerForm);



    }


}
