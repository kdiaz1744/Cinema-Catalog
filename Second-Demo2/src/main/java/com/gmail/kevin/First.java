package com.gmail.kevin;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "feed", layout = MainView.class)
public class First extends VerticalLayout {

    public First(){

        Label end1 = new Label("Programmer Information: \n ");
        Label end2 = new Label("Name: Kevin Diaz \n");
        Label end3 = new Label("E-mail: kdiaz1744@hotmail.com \n " +
                "");

        add(
                new H2("If you have any Feedback, please use the form below, \n"),
                buildForm(),
                new H3("\nOr let the programmer know directly, Thank You."),
                end1, end2, end3
        );

    }

    private Component buildForm() {

        FormLayout layoutWithBinder = new FormLayout();
        Binder<feedback> binder = new Binder<>();

// The object that will be edited
        feedback given = new feedback();

// Create the fields
        TextField firstName = new TextField();
        firstName.setValueChangeMode(ValueChangeMode.EAGER);

        TextField lastName = new TextField();
        lastName.setValueChangeMode(ValueChangeMode.EAGER);

        TextField phone = new TextField();
        phone.setValueChangeMode(ValueChangeMode.EAGER);

        TextField email = new TextField();
        email.setValueChangeMode(ValueChangeMode.EAGER);

        TextField Message = new TextField();
        Message.setValueChangeMode(ValueChangeMode.EAGER);
        Message.setMaxHeight("100px");
        Message.setMaxWidth("1000px");
        Message.setHeightFull();
        Message.setWidthFull();

        Label infoLabel = new Label();
        NativeButton save = new NativeButton("Send");
        NativeButton reset = new NativeButton("Clear");

        layoutWithBinder.addFormItem(firstName, "First name");
        layoutWithBinder.addFormItem(lastName, "Last name");
        layoutWithBinder.addFormItem(email, "E-mail");
        FormLayout.FormItem phoneItem = layoutWithBinder.addFormItem(phone, "Phone");
        layoutWithBinder.addFormItem(Message, "Feedback");

        // Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");

        SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
                .getValue().trim().isEmpty()
                || !email.getValue().trim().isEmpty();

// E-mail and phone have specific validators
        Binder.Binding<feedback, String> emailBinding = binder.forField(email)
                .withValidator(phoneOrEmailPredicate,
                        "Both phone and email cannot be empty")
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(feedback::getEmail, feedback::setEmail);

        Binder.Binding<feedback, String> phoneBinding = binder.forField(phone)
                .withValidator(phoneOrEmailPredicate,
                        "Both phone and email cannot be empty")
                .bind(feedback::getNumber, feedback::setNumber);

// Trigger cross-field validation when the other field is changed
        email.addValueChangeListener(event -> phoneBinding.validate());
        phone.addValueChangeListener(event -> emailBinding.validate());

// First name and last name are required fields
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);
        Message.setRequiredIndicatorVisible(true);

        binder.forField(firstName)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(feedback::getFirstName, feedback::setFirstName);
        binder.forField(lastName)
                .withValidator(new StringLengthValidator(
                        "Please add the last name", 1, null))
                .bind(feedback::getLastName, feedback::setLastName);
        binder.forField(Message)
                .withValidator(new StringLengthValidator(
        "Please add a message", 1, null))
                .bind(feedback::getMessage, feedback::setMessage);


        Label content = new Label(
                "Your feedback has been sent! Thank You!");
        NativeButton buttonInside = new NativeButton("Got it");
        Notification notification = new Notification(content, buttonInside);
        notification.setDuration(3000);
        buttonInside.addClickListener(event -> notification.close());
        notification.setPosition(Notification.Position.MIDDLE);

// Click listeners for the buttons
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(given)) {
                infoLabel.setText("Saved bean values: " + given);
                notification.open();
                binder.readBean(null);
                infoLabel.setText("");
            } else {
                BinderValidationStatus<feedback> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                infoLabel.setText("There are errors: " + errorText);
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            infoLabel.setText("");
        });

        //Wrap components in layouts
        HorizontalLayout formLayout = new HorizontalLayout(layoutWithBinder);
        Div wrapperLayout = new Div(actions, formLayout);
        formLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        formLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        wrapperLayout.setWidth("100%");

        return wrapperLayout;
    }

}