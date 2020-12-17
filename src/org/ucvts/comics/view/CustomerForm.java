package org.ucvts.comics.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.ucvts.comics.model.Customer;

@SuppressWarnings("serial")
public class CustomerForm extends JPanel {

    private JTextField customerIdField;
    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField postalField;
    private JTextField phoneField;
    private JTextField emailField;
    private JLabel errorLabel;



    public CustomerForm() {
        this(null);
    }



    public CustomerForm(Customer customer) {
        this.init(customer);
    }



    public void updateFields(Customer customer) {
        if (customer == null) {
            clearFields();

            return;
        }

        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        fnameField.setText(customer.getFirstName());
        lnameField.setText(customer.getLastName());
        streetField.setText(customer.getStreetAddress());
        cityField.setText(customer.getCity());
        stateField.setText(customer.getState());
        postalField.setText(customer.getPostalCode());
        emailField.setText(customer.getEmail());
        phoneField.setText(Long.toString(customer.getPhone()));

    }



    public Customer getCustomerFromFields() {
        if (customerIdField.getText().trim().isEmpty()) {
            return new Customer(
                    fnameField.getText(),
                    lnameField.getText(),
                    Long.parseLong(phoneField.getText()),
                    emailField.getText(),
                    streetField.getText(),
                    cityField.getText(),
                    stateField.getText(),
                    postalField.getText()
            );
        } else {
            return new Customer(
                    Long.parseLong(customerIdField.getText()),
                    fnameField.getText(),
                    lnameField.getText(),
                    Long.parseLong(phoneField.getText()),
                    emailField.getText(),
                    streetField.getText(),
                    cityField.getText(),
                    stateField.getText(),
                    postalField.getText()
            );
        }
    }



    public void updateErrorMessage(String message) {
        errorLabel.setText(message);
    }



    private void init(Customer customer) {
        this.setLayout(null);

        initId(customer);
        initName(customer);
        initStreet(customer);
        initCity(customer);
        initState(customer);
        initZip(customer);
        initEmail(customer);
        initPhone(customer);

        initErrorMessage();
    }



    private void initId(Customer customer) {
        JLabel label = new JLabel("Customer ID");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 15, 100, 40);
        label.setLabelFor(customerIdField);

        customerIdField = new JTextField(10);
        customerIdField.setBounds(20, 45, 710, 40);;
        customerIdField.setEditable(false);

        this.add(label);
        this.add(customerIdField);
    }



    private void initName(Customer customer) {
        JLabel label = new JLabel("First Name");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 85, 100, 40);
        label.setLabelFor(fnameField);

        fnameField = new JTextField(10);
        fnameField.setBounds(20, 115, 710, 40);

        JLabel label2 = new JLabel("Last Name");
        label2.setFont(new Font("DialogInput", Font.BOLD, 14));
        label2.setBounds(25, 155, 100, 40);
        label2.setLabelFor(fnameField);

        lnameField = new JTextField(10);
        lnameField.setBounds(20, 185, 710, 40);

        this.add(label);
        this.add(fnameField);
        this.add(label2);
        this.add(lnameField);
    }



    private void initStreet(Customer customer) {
        JLabel label = new JLabel("Street Address");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 225, 200, 40);
        label.setLabelFor(streetField);

        streetField = new JTextField(10);
        streetField.setBounds(20, 255, 320, 40);

        this.add(label);
        this.add(streetField);
    }


    private void initCity(Customer customer) {
        JLabel label = new JLabel("City");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 295, 100, 40);
        label.setLabelFor(cityField);

        cityField = new JTextField(10);
        cityField.setBounds(20, 325, 320, 40);

        this.add(label);
        this.add(cityField);
    }

    private void initState(Customer customer) {
        JLabel label = new JLabel("State");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(355, 295, 100, 40);
        label.setLabelFor(stateField);

        stateField = new JTextField(10);
        stateField.setBounds(350, 325, 160, 40);

        stateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(stateField.getText().length() >= 2) {
                    e.consume();
                }
            }
        });

        this.add(label);
        this.add(stateField);
    }

    private void initZip(Customer customer) {
        JLabel label = new JLabel("Postal Code");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(525, 295, 100, 40);
        label.setLabelFor(postalField);

        postalField = new JTextField(10);
        postalField.setBounds(520, 325, 210, 40);

        postalField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < 48 || e.getKeyChar() > 57) {
                    e.consume();
                } else if(postalField.getText().length() >= 5) {
                    e.consume();
                }
            }
        });

        this.add(label);
        this.add(postalField);
    }

    private void initPhone(Customer customer) {
        JLabel label = new JLabel("Phone");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 365, 100, 40);
        label.setLabelFor(phoneField);

        phoneField = new JTextField(10);
        phoneField.setBounds(20, 395, 710, 40);

        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < 48 || e.getKeyChar() > 57) {
                    e.consume();
                } else if(phoneField.getText().length() >= 10) {
                    e.consume();
                }
            }
        });

        this.add(label);
        this.add(phoneField);
    }

    private void initEmail(Customer customer) {
        JLabel label = new JLabel("Email");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 435, 100, 40);
        label.setLabelFor(emailField);

        emailField = new JTextField(10);
        emailField.setBounds(20, 465, 710, 40);

        this.add(label);
        this.add(emailField);
    }


    private void initErrorMessage() {
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(20, 540, 710, 40);

        this.add(errorLabel);
    }
    private void clearFields() {
        customerIdField.setText("");
        fnameField.setText("");
        lnameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        postalField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}