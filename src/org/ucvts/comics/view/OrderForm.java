package org.ucvts.comics.view;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.controller.ViewManager;
import org.ucvts.comics.dao.CustomerDAO;
import org.ucvts.comics.model.Order;

@SuppressWarnings("serial")
public class OrderForm extends JPanel {

    private ViewManager manager;
    private JTextField orderIdField;
    private JTextField productIdField;
    private JTextField statusField;
    private JComboBox<String> monthDropdown;
    private JComboBox<String> dayDropdown;
    private JComboBox<String> yearDropdown;
    private JTextField customerIdField;
    private JTextField totalField;
    private JLabel errorLabel;
    private JScrollPane scroll;
    private Order order;


    public OrderForm(ViewManager manager) {
        this(manager, null);
    }



    public OrderForm(ViewManager manager, Order order) {
        this.manager = manager;
        this.init(order);
    }



    public void setOrder(Order order) {
        this.order = order;
        updateFields(this.order);
    }


    public void updateFields(Order order) {
        if (order == null) {
            clearFields();

            return;
        }

        orderIdField.setText(String.valueOf(order.getOrderId()));
        productIdField.setText(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        customerIdField.setText(String.valueOf(order.getCustomer().getCustomerId()));
        statusField.setText(order.getStatus());
        totalField.setText(String.valueOf(order.getTotal()));


        String date = String.valueOf(order.getOrderDate());
        String year = date.substring(0, 4);
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));

        monthDropdown.setSelectedIndex(month);
        dayDropdown.setSelectedIndex(day);
        yearDropdown.setSelectedItem(year);

        if(order != null) {
            refreshOrderItemList(order);
        }

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            statusField.setEditable(false);
            totalField.setEditable(false);
            monthDropdown.setEnabled(false);
            dayDropdown.setEnabled(false);
            yearDropdown.setEnabled(false);

        } else {
            statusField.setEditable(true);
            totalField.setEditable(true);
            monthDropdown.setEnabled(true);
            dayDropdown.setEnabled(true);
            yearDropdown.setEnabled(true);

        }

    }

    public void refreshOrderItemList(Order order) {
        if(scroll != null ) {
            this.remove(scroll);
        }
        manager.switchTo(MidtownComics.InventoryView);
        manager.switchTo(MidtownComics.OrderView);
        //System.out.println("!!s");
        initOrderItemsPanel(order);
    }



    public Order getOrderFromFields() {
        System.out.println(parsePrice());
        try {
            return new Order(
                    Long.parseLong(orderIdField.getText()),
                    CustomerDAO.getCustomer(Long.parseLong(customerIdField.getText())),
                    parseReleaseDate(),
                    statusField.getText(),
                    new ArrayList<>(),
                    parsePrice()
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void updateErrorMessage(String message) {
        errorLabel.setText(message);
    }



    private void init(Order order) {
        this.setLayout(null);

        initOrderId(order);
        initProductId(order);
        initCustomerId(order);
        initStatus(order);
        initTotal(order);
        initDate(order);
        if(order != null) {
            System.out.println("!!!!!");
            initOrderItemsPanel(order);
        }
        initErrorMessage();
    }



    private void initOrderId(Order order) {
        JLabel label = new JLabel("Order ID");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 15, 100, 40);
        label.setLabelFor(orderIdField);

        orderIdField = new JTextField(10);
        orderIdField.setBounds(20, 45, 710, 40);;
        orderIdField.setEditable(false);
        orderIdField.setEditable(false);

        this.add(label);
        this.add(orderIdField);
    }



    private void initProductId(Order order) {
        JLabel label = new JLabel("Customer Name");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 85, 100, 40);
        label.setLabelFor(productIdField);

        productIdField = new JTextField(10);
        productIdField.setBounds(20, 115, 345, 40);
        productIdField.setEditable(false);

        this.add(label);
        this.add(productIdField);
    }

    private void initCustomerId(Order order) {
        JLabel label = new JLabel("Customer ID");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(390, 85, 100, 40);
        label.setLabelFor(productIdField);

        customerIdField = new JTextField(10);
        customerIdField.setBounds(385, 115, 345, 40);
        customerIdField.setEditable(false);

        this.add(label);
        this.add(customerIdField);
    }



    private void initStatus(Order order) {
        JLabel label = new JLabel("Status");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 155, 100, 40);
        label.setLabelFor(statusField);

        statusField = new JTextField(10);
        statusField.setBounds(20, 185, 345, 40);

        if(order != null && order.getStatus().toLowerCase().equals("closed")) {
            statusField.setEditable(false);
        } else {
            statusField.setEditable(true);
        }

        this.add(label);
        this.add(statusField);
    }

    private void initTotal(Order order) {
        JLabel label = new JLabel("Total");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(390, 155, 100, 40);
        label.setLabelFor(totalField);

        totalField = new JTextField(10);
        totalField.setBounds(385, 185, 345, 40);

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            totalField.setEditable(false);
        } else {
            totalField.setEditable(true);
        }

        this.add(label);
        this.add(totalField);
    }



    private void initDate(Order order) {
        JLabel label = new JLabel("Order Date");
        label.setFont(new Font("DialogInput", Font.BOLD, 14));
        label.setBounds(25, 225, 100, 40);
        label.setLabelFor(monthDropdown);

        monthDropdown = new JComboBox<>(getMonths());
        monthDropdown.setBounds(20, 255, 320, 40);

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            monthDropdown.setEditable(false);
        } else {
            monthDropdown.setEditable(true);
        }

        dayDropdown = new JComboBox<>(getDays());
        dayDropdown.setBounds(350, 255, 160, 40);

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            dayDropdown.setEditable(false);
        } else {
            dayDropdown.setEditable(true);
        }

        yearDropdown = new JComboBox<>(getYears());
        yearDropdown.setBounds(520, 255, 210, 40);

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            yearDropdown.setEditable(false);
        } else {
            yearDropdown.setEditable(true);
        }

        this.add(label);
        this.add(monthDropdown);
        this.add(dayDropdown);
        this.add(yearDropdown);
    }


    private void initOrderItemsPanel(Order order) {
        JPanel body = new JPanel();
        //body.setBackground(Color.RED);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(15, 15, 15, 15));
        body.setBounds(20, 335, 710, 200);
        for (int i = 0; i < manager.getOrderItems(order).size(); i++) {
            System.out.println("!");
            OrderItemListPanel oilp = new OrderItemListPanel(manager, manager.getOrderItems(order).get(i), order);
            //oilp.setBounds(20, 335, 710, 200);
            body.add(oilp);
        }



        scroll = new JScrollPane(body);
        scroll.setBounds(20, 335, 710, 200);
        this.add(scroll);
    }



    private void initErrorMessage() {
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(20, 540, 710, 40);

        this.add(errorLabel);
    }



    private void clearFields() {
        orderIdField.setText("");
        productIdField.setText("");
        customerIdField.setText("");
        statusField.setText("");
        totalField.setText("");
        monthDropdown.setSelectedIndex(0);
        dayDropdown.setSelectedIndex(0);
        yearDropdown.setSelectedIndex(0);


    }



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



    private long parseReleaseDate() {
        int year = Integer.parseInt(yearDropdown.getSelectedItem().toString());
        int month = monthDropdown.getSelectedIndex();
        int day = dayDropdown.getSelectedIndex();

        return Long.parseLong(String.format("%d%02d%02d", year, month, day));
    }



    private double parsePrice() {
        System.out.println(totalField.getText());
        return Double.parseDouble(totalField.getText());
    }
}
