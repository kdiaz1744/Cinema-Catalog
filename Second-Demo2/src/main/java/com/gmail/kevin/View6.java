package com.gmail.kevin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Route(value = "View6", layout = MainView.class)
public class View6 extends VerticalLayout {

    private List<movieInfo> moviesAdding = new LinkedList<>();
    private Grid<movieInfo> grid = new Grid<>();

    public View6() {
        setSizeFull();
        ListDataProvider<movieInfo> dataProvider = createDataProvider();
        Crud<movieInfo> crud = new Crud<>(movieInfo.class, createGrid(), createMovieEditor());
        crud.setMaxWidth("1300px");
        crud.setWidth("100%");
        crud.setDataProvider(dataProvider);
        setHorizontalComponentAlignment(Alignment.CENTER, crud);

        CrudI18n customI18n = CrudI18n.createDefault();
        customI18n.setEditItem("Update Movie");
        customI18n.setNewItem("New Movie");
        crud.setI18n(customI18n);

        crud.addSaveListener(saveEvent -> {
            movieInfo toSave = saveEvent.getItem();
            // Save the item in the database
            if (!dataProvider.getItems().contains(toSave)) {
                dataProvider.getItems().add(toSave);
            }
            //addMovie(toSave);

        });

        crud.addDeleteListener(deleteEvent -> {
            movieInfo toDelete = deleteEvent.getItem();
            // Delete the item in the database
            dataProvider.getItems().remove(deleteEvent.getItem());
            //deleteMovie(toDelete);
        });

        add(
                new H2("Movie Catalog"),
                new H5("If you want to Edit or Delete any movies, hit the button at the far right corner. " +
                        "To add movies, simply press the New Movie button at the bottom"),
                crud
        );
    }

    private ListDataProvider<movieInfo> createDataProvider() {
        List<movieInfo> data = new ArrayList<>();

        if(moviesAdding.isEmpty()){
            movieInfo movie = new movieInfo(1, "EndGame", "Final Battle of the Avengers", 5,
                    2019, 1000000000, true);
            data.add(new movieInfo(1, "EndGame", "Final Battle of the Avengers", 5,
                    2019, 1000000000, true));
            addMovie(movie);
            movie = new movieInfo(2, "Batman", "Christian Bale takes the mantle of Batman", 4,
                    2006, 365000000, false);
            data.add(new movieInfo(2, "Batman", "Christian Bale takes the mantle of Batman", 4,
                    2006, 365000000, false));
            addMovie(movie);
            movie = new movieInfo(3, "Zone of Enders", "The world is taken over by robots and onlt a boy can save it", 3,
                    2008, 1250000, false);
            data.add(new movieInfo(3, "Zone of Enders", "The world is taken over by robots and onlt a boy can save it", 3,
                    2008, 1250000, false));
            addMovie(movie);
        }
        else {
            for(int i = 0; i < moviesAdding.size(); i++){
                data.add(i, moviesAdding.get(i));
            }
        }


        //data.add(new Company("Masters 25", "Xi Zhang", "xizhang78@live.com", "", "Kaifeng", "", "Henan", "China", "(561) 187-4547", "(214) 481-7271"));
        return new ListDataProvider<>(data);
    }

    private Grid<movieInfo> createGrid() {
;
        grid.addColumn(c -> c.getName()).setHeader("Movie name")
                .setWidth("160px");
        grid.addColumn(c -> c.getYear()).setHeader("Movie's Year");
        grid.addColumn(c -> c.getRating()).setHeader("Movie Rating");
        Crud.addEditColumn(grid);
        return grid;
    }

    private CrudEditor<movieInfo> createMovieEditor() {

        TextField nameField = new TextField("Movie Name");
        nameField.setRequiredIndicatorVisible(true);
        setColspan(nameField, 4);

        TextField ratingField = new TextField("Movie Rating");
        ratingField.setRequiredIndicatorVisible(true);
        setColspan(ratingField, 2);

        TextField yearField = new TextField("Movie's Year");
        yearField.setRequiredIndicatorVisible(true);
        setColspan(yearField, 2);

        TextField synopsisField = new TextField("Movie Synopsis");
        setColspan(synopsisField, 4);
        synopsisField.setRequiredIndicatorVisible(true);

        Checkbox available = new Checkbox("Currently On?");


        FormLayout form = new FormLayout(nameField, ratingField, yearField,
                synopsisField, available);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

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
        binder.forField(available)
                .bind("screening");

        return new BinderCrudEditor<>(binder, form);
    }

    private void setColspan(Component component, int colspan) {
        component.getElement().setAttribute("colspan", Integer.toString(colspan));
    }

    private void addMovie(movieInfo savedMovie) {
        //if(moviesAdding.contains(savedMovie.getId())){}
        movieInfo newMovie = savedMovie;
        moviesAdding.removeIf(entity -> entity.getId().equals(savedMovie.getId()));
        moviesAdding.add(newMovie);
        grid.setItems(moviesAdding);
    }

    private void deleteMovie(movieInfo movie){
        moviesAdding.removeIf(entity -> entity.getId().equals(movie.getId()));
    }
}