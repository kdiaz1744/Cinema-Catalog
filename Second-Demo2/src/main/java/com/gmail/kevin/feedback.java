package com.gmail.kevin;

public class feedback implements Cloneable {

    private String firstName = "";
    private String lastName = "";
    private String message = "";
    private String email = "";
    private String number = "";


    public feedback(){

    }

    public feedback(String firstName, String lastName, String message, String email, String number){

        this.firstName = firstName;
        this.lastName = lastName;
        this.message = message;
        this.email = email;
        this.number = number;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public feedback clone() {
        try {
            return (feedback) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
