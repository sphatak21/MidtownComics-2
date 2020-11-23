import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class PaymentForm extends JPanel{
    private JTextField nameField;
    private JTextField creditCardField;
    private JComboBox<String> monthDropdown;
    private JComboBox<String> dayDropdown;
    private JComboBox<String> yearDropdown;
    private JTextField securityCodeField;
    private JTextField streetAddressField;
    private JTextField cityField;
    private JComboBox<String> stateDropdown;
    private JTextField postalCodeField;

    /**
     * Creates a default instance of the PaymentForm class.
     */

    public PaymentForm() {
        super();

        this.init();
    }

    /**
     * Clears all fields.
     */

    public void clearFields() {
        nameField.setText("");
        creditCardField.setText("");
        monthDropdown.setSelectedIndex(0);
        dayDropdown.setSelectedIndex(0);
        yearDropdown.setSelectedIndex(0);
        securityCodeField.setText("");
        streetAddressField.setText("");
        cityField.setText("");
        stateDropdown.setSelectedIndex(0);
        postalCodeField.setText("");
    }

    /*
     * Initializes all UI components.
     */

    private void init() {
        this.setLayout(null);

        initNameField();
        initCreditCardField();
        initExpirationDate();
        initSecurityCode();
        initStreetAddress();
        initCityStatePostalCode();
    }

    /*
     * Initializes name UI field.
     */

    private void initNameField() {
        JLabel label = new JLabel("Name on Credit Card");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 15, 200, 40);
        label.setLabelFor(nameField);

        nameField = new JTextField(10);
        nameField.setBounds(20, 45, 710, 40);;

        this.add(label);
        this.add(nameField);
    }

    /*
     * Initializes credit card UI field.
     */

    private void initCreditCardField() {
        JLabel label = new JLabel("Credit Card No.");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 85, 200, 40);
        label.setLabelFor(creditCardField);

        creditCardField = new JTextField(10);
        creditCardField.setBounds(20, 115, 710, 40);
        creditCardField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < 48 || e.getKeyChar() > 57) {
                    e.consume();
                }
            }
        });

        this.add(label);
        this.add(creditCardField);
    }

    /*
     * Initializes expiration date UI dropdown menus.
     */

    private void initExpirationDate() {
        JLabel label = new JLabel("Expiration Date");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 155, 200, 40);
        label.setLabelFor(monthDropdown);

        monthDropdown = new JComboBox<>(getMonths());
        monthDropdown.setBounds(20, 185, 320, 40);

        dayDropdown = new JComboBox<>(getDays());
        dayDropdown.setBounds(350, 185, 160, 40);

        yearDropdown = new JComboBox<>(getYears());
        yearDropdown.setBounds(520, 185, 210, 40);

        this.add(label);
        this.add(monthDropdown);
        this.add(dayDropdown);
        this.add(yearDropdown);
    }

    /*
     * Initializes security code UI field.
     */

    private void initSecurityCode() {
        JLabel label = new JLabel("CVV Code");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 225, 100, 40);
        label.setLabelFor(securityCodeField);

        securityCodeField = new JTextField(10);
        securityCodeField.setBounds(20, 255, 710, 40);
        securityCodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < 48 || e.getKeyChar() > 57) {
                    e.consume();
                }
            }
        });

        this.add(label);
        this.add(securityCodeField);
    }

    /*
     * Initializes street address UI field.
     */

    private void initStreetAddress() {
        JLabel label = new JLabel("Street Address");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 365, 200, 40);
        label.setLabelFor(securityCodeField);

        streetAddressField = new JTextField(10);
        streetAddressField.setBounds(20, 395, 710, 40);

        this.add(label);
        this.add(streetAddressField);
    }

    /*
     * Initializes cstate UI dropdown menu, as well as the city and postal code UI fields.
     */

    private void initCityStatePostalCode() {
        cityField = new JTextField(10);
        cityField.setBounds(20, 445, 320, 40);

        stateDropdown = new JComboBox<>(getStates());
        stateDropdown.setBounds(350, 445, 160, 40);

        postalCodeField = new JTextField(10);
        postalCodeField.setBounds(520, 445, 210, 40);
        postalCodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (postalCodeField.getText().length() >= 5) {
                    e.consume();
                } else if (e.getKeyChar() < 48 || e.getKeyChar() > 57) {
                    e.consume();
                }
            }
        });

        this.add(cityField);
        this.add(stateDropdown);
        this.add(postalCodeField);
    }

    /*
     * Gets the values for the months dropdown menu.
     */

    private String[] getMonths() {
        return new String[]{
                "--Month--",
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
    }

    /*
     * Gets the values for the days dropdown menu.
     */

    private String[] getDays() {
        String[] days = new String[32];

        for (int i = 0; i <= 31; i++) {
            if (i == 0) {
                days[i] = "--Day--";
            } else {
                days[i] = String.valueOf(i);
            }
        }

        return days;
    }

    /*
     * Gets the values for the years dropdown menu.
     */

    private String[] getYears() {
        String[] years = new String[73];

        for (int i = 1949; i <= 2021; i++) {
            if (i == 1949) {
                years[0] = "--Year--";
            } else {
                years[i - 1949] = String.valueOf(i);
            }
        }

        return years;
    }

    /*
     * Gets the values for the states dropdown menu.
     */

    private String[] getStates() {
        return new String[] {
                "--State--",
                "AL",
                "AK",
                "AS",
                "AZ",
                "AR",
                "CA",
                "CO",
                "CT",
                "DE",
                "DC",
                "FM",
                "FL",
                "GA",
                "GU",
                "HI",
                "ID",
                "IL",
                "IN",
                "IA",
                "KS",
                "KY",
                "LA",
                "ME",
                "MH",
                "MD",
                "MA",
                "MI",
                "MN",
                "MS",
                "MO",
                "MT",
                "NE",
                "NV",
                "NH",
                "NJ",
                "NM",
                "NY",
                "NC",
                "ND",
                "MP",
                "OH",
                "OK",
                "OR",
                "PW",
                "PA",
                "PR",
                "RI",
                "SC",
                "SD",
                "TN",
                "TX",
                "UT",
                "VT",
                "VI",
                "VA",
                "WA",
                "WV",
                "WI",
                "WY"
        };
    }
}
