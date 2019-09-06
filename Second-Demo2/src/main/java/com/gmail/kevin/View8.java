package com.gmail.kevin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.Route;

import java.util.*;

@Route(value = "view8", layout = MainView.class)
public class View8 extends VerticalLayout {

    private List<movieInfo> moviesAdding = new LinkedList<>();
    private Grid<movieInfo> grid = new Grid<>();
    public Integer premiumTickets = new Integer(0);
    public Integer normalTickets = new Integer(0);
    public Integer discountTickets = new Integer(0);

    public View8(){

        Grid<movieInfo> grid = new Grid<>();
        List<movieInfo> movieList = getItems();
        grid.setItems(movieList);
        Grid.Column<movieInfo> nameColumn = grid.addColumn(movieInfo::getName)
                .setHeader("Movie Name");
        Grid.Column<movieInfo> earningsColumn = grid.addColumn(movieInfo::getAmount)
                .setHeader("Movie Earnings");

        Binder<movieInfo> binder = new Binder<>(movieInfo.class);
        Editor<movieInfo> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        //Div validationStatus = new Div();
        //validationStatus.setId("validation");

        NumberField premium = new NumberField();
        premium.setHasControls(true);
        binder.forField(premium)
                .asRequired()
                .bind("amount")
        ;
        earningsColumn.setEditorComponent(premium);

        NumberField normal = new NumberField();
        normal.setHasControls(true);
        binder.forField(normal)
                .asRequired()
                .bind("amount")
        ;

        NumberField discount = new NumberField();
        discount.setHasControls(true);
        binder.forField(discount)
                .asRequired()
                .bind("amount")
        ;



        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        Grid.Column<movieInfo> editorColumn = grid.addComponentColumn(movie -> {
            Button edit = new Button("Add Earnings");
            edit.addClassName("add");
            edit.addClickListener(e -> {
                editor.editItem(movie);
                premium.focus();
            });
            edit.setEnabled(!editor.isOpen());
            editButtons.add(edit);
            return edit;
        });

        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));

        Button save = new Button("Save", e -> editor.save());
        save.addClassName("save");

        Button cancel = new Button("Cancel", e -> editor.cancel());
        cancel.addClassName("cancel");

// Add a keypress listener that listens for an escape key up event.
// Note! some browsers return key as Escape and some as Esc
        grid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        Div buttons = new Div(save, cancel);
        editorColumn.setEditorComponent(buttons);

        //Wrap components in layouts
        HorizontalLayout formLayout = new HorizontalLayout( grid);
        Div wrapperLayout = new Div(formLayout);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        wrapperLayout.setWidth("100%");

        add(wrapperLayout);

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
