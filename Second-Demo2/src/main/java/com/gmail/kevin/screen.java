package com.gmail.kevin;

import com.gmail.kevin.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Route(value = "Home2", layout = MainView.class)
public class screen extends VerticalLayout {

    private List<movieInfo> moviesAdding = getItems();
    private Grid<movieInfo> movieInfoGrid = new Grid<>();

    public screen() {

        //Wrap components in layouts
        HorizontalLayout formLayout = new HorizontalLayout(movieInfoGrid);
        Div wrapperLayout = new Div(formLayout);
        //formLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        formLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        wrapperLayout.setWidth("100%");

        add(
                new H1("Now Showing"),
                buildGrid()
        );




    }

    private Component buildGrid() {
        //moviesAdding = populateList();
        movieInfoGrid.setItems(moviesAdding);
        movieInfoGrid.setMaxWidth("800px");
        movieInfoGrid.setWidth("100%");
        movieInfoGrid.removeAllColumns();
        movieInfoGrid.addColumn(c -> c.getName()).setHeader("Movie Name");
        movieInfoGrid.addColumn(c -> c.getRating()).setHeader("Movie Rating");

        return movieInfoGrid;
    }


    private List<movieInfo> getItems() {
        List<movieInfo> Movies = new ArrayList<>();
        Movies.add(new movieInfo(1, "EndGame", "Final Battle of the Avengers", 5,
                2019, 1000000000, true));
        Movies.add(new movieInfo(2, "Batman", "Christian Bale takes the mantle of Batman", 4,
                2006, 365000000, false));
        Movies.add(new movieInfo(3, "Zone of Enders", "The world is taken over by robots and onlt a boy can save it", 3,
                2008, 1250000, false));
        return Movies;
    }

}