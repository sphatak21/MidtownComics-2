package org.ucvts.comics.model;

import java.util.HashMap;

import javax.swing.SpinnerNumberModel;

@SuppressWarnings({"serial", "unused"})
public class NumberSpinner extends SpinnerNumberModel {

    private HashMap<String, String> clientProperties;


    public NumberSpinner(int start, int min, int max, int step) {

        super(start, min, max, step);

        clientProperties = new HashMap<String, String>();

    }

    public void putClientProperty(String property, String value) {
        clientProperties.put(property, value);
    }

    public String getClientProperty(String property) {
        return clientProperties.get(property);
    }

    public void clearClientProperty(String property) {
        clientProperties.remove(property);
    }

    public void clearClientProperties() {
        clientProperties.clear();
    }



}
