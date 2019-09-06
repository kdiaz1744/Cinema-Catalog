package com.gmail.kevin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "View7", layout = MainView.class)
public class View7 extends VerticalLayout {
    private Grid<movieInfo> movieInfoGrid = new Grid<>(movieInfo.class);
    private List<movieInfo> moviesAdding = new LinkedList<>();


    public View7() {

        //movieInfo datas = new movieInfo();
        //moviesAdding = datas.DATABASE;

        add(
                new H1("Add Movie"),
                buildForm(),
                movieInfoGrid
        );

    }

    private Component buildForm(){

        // Create UI components
        TextField nameField = new TextField("Name");
        TextField synopsisField = new TextField("Synopsis");
        TextField yearField = new TextField("Year");
        TextField ratingField = new TextField("Ratings");

        //ComboBox<String> ratingsSelect = new ComboBox<>("Ratings",RNumbers.keySet());
        Button addButton = new Button("Add");
        Div errorsLayout = new Div();

        // Configure UI components
        addButton.setThemeName("primary");

        Binder<movieInfo> binder = new Binder<>(movieInfo.class);
        binder.forField(nameField)
                .asRequired("Name is required")
                .bind("name");
        binder.forField(ratingField)
                .asRequired()
                .withConverter(new StringToIntegerConverter("Must be an acceptable number"))
                .withValidator(new IntegerRangeValidator("Must be at least 1, and max 5", 1, 5))
                .bind("rating");
        binder.forField(synopsisField)
                .asRequired("Synopsis is required")
                .bind("synopsis");
        binder.forField(yearField)
                .asRequired()
                .withConverter(new StringToIntegerConverter("Quantity must be a number"))
                .withValidator(new IntegerRangeValidator("Must be an acceptable year", 1900, 2020))
                .bind("year");

        binder.addStatusChangeListener(status -> {
            addButton.setEnabled(!status.hasValidationErrors());
        });

        binder.readBean(new movieInfo());

        addButton.addClickListener(click -> {
            try {
                errorsLayout.setText("");
                movieInfo mInfo = new movieInfo();
                binder.writeBean((mInfo));
                addMovie(mInfo);
                binder.readBean(new movieInfo());

            } catch (ValidationException e){
                errorsLayout.add(new Html(e.getValidationErrors().stream()
                        .map(res -> "<p>" + res.getErrorMessage() + "</p>")
                        .collect(Collectors.joining("\n"))));
            }
        });


        //Wrap components in layouts
        HorizontalLayout formLayout = new HorizontalLayout(nameField, synopsisField, ratingField, yearField, addButton);
        Div wrapperLayout = new Div(formLayout, errorsLayout);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        wrapperLayout.setWidth("100%");

        return wrapperLayout;
    }

    private void addMovie(movieInfo savedMovie) {
        moviesAdding.add(savedMovie);
        movieInfoGrid.setItems(moviesAdding);
    }


}