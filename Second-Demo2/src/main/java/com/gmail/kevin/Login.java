package com.gmail.kevin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.login.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class Login extends VerticalLayout {

    private LoginOverlay login = new LoginOverlay();

    public Login(){

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Movie Cinema");
        //i18n.getHeader().setDescription(
        //        "admin@vaadin.com + admin\n" + "barista@vaadin.com + barista");
        i18n.setAdditionalInformation(null);
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Sign in");
        i18n.getForm().setTitle("Sign in");
        i18n.getForm().setUsername("Email");
        i18n.getForm().setPassword("Password");
        login.setI18n(i18n);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("Home");
        login.setOpened(true);
        add(login);
    }

    private void navigateToMainPage() {
        Div menu = new Div();
        //Button Home = new Button("Home");
        setHorizontalComponentAlignment(Alignment.CENTER, menu);
        menu.add(new RouterLink("Click Here To Enter Website", MainView.class));
        add(menu);
    }

    private boolean authenticate(AbstractLogin.LoginEvent e) {
        AbstractLogin.LoginEvent log;


        if(e.getPassword() == "employee" && e.getUsername() == "employee"){
            return true;
        }
        else return false;
    }
}
