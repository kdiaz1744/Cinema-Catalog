package com.gmail.kevin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.*;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "earnings", layout = MainView.class)
public class earnings extends VerticalLayout {

    private List<movieInfo> moviesAdding = populateList();
    private Grid<movieInfo> movieInfoGrid = new Grid<>();
    public Double premiumTickets = new Double(0);
    public Double normalTickets = new Double(0);
    public Double discountTickets = new Double(0);
    public Integer index = new Integer(0);

    public earnings(){

        add(
                new H1("Movie Earnings"),
                new H5("For both adding and subtracting earnings you need the Movie ID."),
                buildForm(),
                buildGrid()
        );

    }

    private Component buildGrid() {

        //moviesAdding = populateList();
        movieInfoGrid.setItems(moviesAdding);
        movieInfoGrid.removeAllColumns();
        movieInfoGrid.addColumn(c -> c.getName()).setHeader("Movie Name");
        movieInfoGrid.addColumn(c -> c.getId()).setHeader("Movie Id");
        movieInfoGrid.addColumn(c -> c.getAmount()).setHeader("Movie Earnings ($)");

        return movieInfoGrid;
    }

    private List<movieInfo> populateList() {
        List<movieInfo> Movies = new ArrayList<>();
        Movies.add(new movieInfo(1, "EndGame", "Final Battle of the Avengers", 5,
                2019, 1000000000, true));
        Movies.add(new movieInfo(2, "Batman", "Christian Bale takes the mantle of Batman", 4,
                2006, 365000000, false));
        Movies.add(new movieInfo(3, "Zone of Enders", "The world is taken over by robots and onlt a boy can save it", 3,
                2008, 1250000, false));
        return Movies;
    }

    private Component buildForm() {

        //Div validationStatus = new Div();
        //validationStatus.setId("validation");

        Binder<movieInfo> binder = new Binder<>(movieInfo.class);

        NumberField id = new NumberField("Movie ID");
        id.setValue(0.1);
        binder.forField(id)
                .asRequired("A Movie ID is Required")
                .bind("id");

        NumberField premium = new NumberField("Premium Tickets ($10)");
        premium.setHasControls(true);
        premium.setMaxWidth("150px");
        premium.setWidthFull();
        premium.setMin(0.0);
        premium.setValue(0.0);

        NumberField normal = new NumberField("Normal Tickets ($7)");
        normal.setHasControls(true);
        normal.setMaxWidth("150px");
        normal.setWidthFull();
        normal.setMin(0.0);
        normal.setValue(0.0);

        NumberField discount = new NumberField("Discount Tickets ($4)");
        discount.setHasControls(true);
        discount.setMaxWidth("150px");
        discount.setWidthFull();
        discount.setMin(0.0);
        discount.setValue(0.0);

        Button addButton = new Button("Add Earnings", new Icon(VaadinIcon.PLUS));




        NumberField minus = new NumberField("Earnings You Want To Subtract");
        minus.setPrefixComponent(new Span("$"));
        minus.setMaxWidth("200px");
        minus.setWidthFull();

        Button subtract = new Button("Subtract Earnings", new Icon(VaadinIcon.MINUS));



        // Configure UI components
        addButton.setThemeName("primary");
        subtract.setThemeName("primary");



        Label content = new Label(
                "Please make sure that your Movie ID exists!");
        NativeButton buttonInside = new NativeButton("Got it");
        Notification notification = new Notification(content, buttonInside);
        notification.setDuration(3000);
        buttonInside.addClickListener(event -> notification.close());
        notification.setPosition(Notification.Position.MIDDLE);




        addButton.addClickListener(click -> {

            Boolean found = false;
            Integer index = 0;
            Integer size = moviesAdding.size();

            for (int i = 0; i < size; i++){
                Integer comp = id.getValue().intValue();
                if(moviesAdding.get(i).getId() == comp){
                    found = true;
                    index = i;
                    //addEarnings(premium, normal, discount, index);
                    break;
                }
            }
            if(found){
                addEarnings(premium, normal, discount, index);
            }
            else{
                notification.open();
            }
            //addEarnings(premium, normal, discount, index);

            id.setValue(0.0);
            premium.setValue(0.0);
            normal.setValue(0.0);
            discount.setValue(0.0);
        });



        Dialog dialog = new Dialog();
        dialog.add(new Label("Are you sure you want to do this?"));

        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        final Integer[] finalIndex = {null};

        subtract.addClickListener(Click -> {

            Boolean found = false;
            index = 0;
            Integer size = moviesAdding.size();


            for (int i = 0; i < size; i++){
                Integer comp = id.getValue().intValue();
                if(moviesAdding.get(i).getId() == comp){
                    found = true;
                    index = i;
                    finalIndex[0] = i;
                    //addEarnings(premium, normal, discount, index);
                    break;
                }
            }
            if(found){
                dialog.open();
            }
            else{
                notification.open();
            }

        });


        NativeButton confirmButton = new NativeButton("Confirm", event -> {
            //messageLabel.setText("Confirmed!");
            subtractEarnings(minus, finalIndex[0]);
            dialog.close();
            id.setValue(0.0);
            minus.clear();
        });
        NativeButton cancelButton = new NativeButton("Cancel", event -> {
            //messageLabel.setText("Cancelled...");
            dialog.close();
        });
        dialog.add(confirmButton, cancelButton);


        //Wrap components in layouts
        HorizontalLayout formLayout = new HorizontalLayout(id, premium, normal, discount, addButton, minus, subtract);
        Div wrapperLayout = new Div(formLayout);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        wrapperLayout.setWidth("100%");

        return wrapperLayout;
    }

    private void addEarnings(NumberField premium, NumberField normal, NumberField discount, Integer index) {

        movieInfo newMovie = moviesAdding.get(index);
        moviesAdding.remove(moviesAdding.get(index));


        premiumTickets = premium.getValue() * 10;
        normalTickets = normal.getValue() * 7;
        discountTickets = discount.getValue() * 4;
        newMovie.setAmount(newMovie.getAmount() + premiumTickets.longValue() + normalTickets.longValue() + discountTickets.longValue());
        moviesAdding.add(index, newMovie);

        //Grid<movieInfo> gridNew = new Grid<>();
        //movieInfoGrid = gridNew;
        //movieInfoGrid.setItems(moviesAdding);

        movieInfoGrid.getDataProvider().refreshAll();
    }

    private void subtractEarnings(NumberField minus, Integer index){
        movieInfo newMovie = moviesAdding.get(index);
        moviesAdding.remove(moviesAdding.get(index));

        newMovie.setAmount(newMovie.getAmount() - minus.getValue().longValue());
        moviesAdding.add(index, newMovie);

        //Grid<movieInfo> gridNew = new Grid<>();
        //movieInfoGrid = gridNew;
        //movieInfoGrid.setItems(moviesAdding);

        movieInfoGrid.getDataProvider().refreshAll();
    }


}
