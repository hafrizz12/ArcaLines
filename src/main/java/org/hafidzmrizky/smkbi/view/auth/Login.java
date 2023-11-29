package org.hafidzmrizky.smkbi.view.auth;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.hafidzmrizky.smkbi.dao.auth.Authentication;
import org.hafidzmrizky.smkbi.model.User;

import java.util.Optional;


@PageTitle("Plane")
@Route(value = "login")
@CssImport("font.css")
public class Login extends VerticalLayout {
    public Login() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setAlignSelf(FlexComponent.Alignment.CENTER);
        layout.setWidthFull();
        layout.getStyle()
                        .setMargin("10px");
        layout.setSizeFull();
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        // register button
        Div register = new Div();
        register.setText("Register");
        register.getStyle()
                .set("font-size", "var(--lumo-font-size-m)")
                .set("margin", "15px")
                .set("font-family", "Poppins, sans-serif")
                .set("font-weight", "700")
                .set("cursor", "pointer");
        register.addClickListener(e -> {
            UI.getCurrent().getPage().executeJs("window.open(\"http://localhost:8080/register\", \"_self\");");
        });

        if (VaadinSession.getCurrent().getAttribute("role") != null) {
            if (VaadinSession.getCurrent().getAttribute("role").equals("admin")) {
                UI.getCurrent().navigate("/admin/dashboard");
            }

            if (VaadinSession.getCurrent().getAttribute("role") == "user") {
                loginForm.getUI().ifPresent(ui -> ui.navigate("dashboard"));
                UI.getCurrent().navigate("dashboard");
            }
        }
        loginForm.addLoginListener(e -> {
            Authentication authentication = new Authentication();
            Optional<User> auth = authentication.login(e.getUsername(), e.getPassword());
            System.out.println(e.getPassword());

            if (auth.isEmpty()) {
                loginForm.setError(true);
                return;
            }

            auth.ifPresent(user -> {
                if (user.getRole() == null || user.getRole().equals("")) {
                    loginForm.setError(true);
                    return;
                }

                VaadinSession.getCurrent().setAttribute("role", user.getRole());
                if (VaadinSession.getCurrent().getAttribute("role").equals("admin")) {
                    VaadinSession.getCurrent().setAttribute("user", user.getUser_id());
                    VaadinSession.getCurrent().setAttribute("username", user.getUsername());
                    VaadinSession.getCurrent().setAttribute("Full Name", user.getFullName());
                    loginForm.getUI().ifPresent(ui -> ui.navigate("/admin/dashboard"));
                } else if (VaadinSession.getCurrent().getAttribute("role").equals("user")) {
                    VaadinSession.getCurrent().setAttribute("user", user.getUser_id());
                    VaadinSession.getCurrent().setAttribute("username", user.getUsername());
                    VaadinSession.getCurrent().setAttribute("Full Name", user.getFullName());
                    loginForm.getUI().ifPresent(ui -> ui.navigate("dashboard"));
                }
            });

        });
        layout.add(loginForm);
        layout.add(register);
        add(layout);
    }
}

