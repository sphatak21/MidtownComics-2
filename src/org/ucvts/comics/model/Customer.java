package org.ucvts.comics.model;

public class Customer {

    private static long lastCustomerId = 1L;

    private long customerId;
    private String firstName;
    private String lastName;
    private long phone;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;



    public Customer() {
        this.customerId = Customer.lastCustomerId++;
    }



    public Customer(long customerId, String firstName, String lastName, long phone, String email, String streetAddress,
                    String city, String state, String postalCode)
    {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public Customer(String firstName, String lastName, long phone, String email, String streetAddress,
                    String city, String state, String postalCode)
    {
        this.customerId = Customer.lastCustomerId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }



    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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



    public long getPhone() {
        return phone;
    }


    public String getFormattedPhone() {
        String s = Long.toString(phone);
        return "(" + s.substring(0,3) + ")" + " " + s.substring(3, 6) + "-" + s.substring(6,10);
    }


    public String getFormattedAddress() {
        return streetAddress + " " + city + ", " + state + " " + postalCode;
    }


    public void setPhone(long phone) {
        this.phone = phone;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public String getStreetAddress() {
        return streetAddress;
    }



    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }



    public String getCity() {
        return city;
    }



    public void setCity(String city) {
        this.city = city;
    }



    public String getState() {
        return state;
    }



    public void setState(String state) {
        this.state = state;
    }



    public String getPostalCode() {
        return postalCode;
    }



    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}