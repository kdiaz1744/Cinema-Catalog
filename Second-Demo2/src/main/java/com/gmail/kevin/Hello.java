package com.gmail.kevin;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "Home", layout = MainView.class)
public class Hello extends VerticalLayout {

    public Hello(){

        add(
                new H1("Hello, Welcome to the Movie Catalog!"),
                new H3("For any Movie related purposes, use the toolbar to navigate, \n"),
                new H3("When you are done, use the Exit tab to leave, Thank You! \n")
        );

    }

}
