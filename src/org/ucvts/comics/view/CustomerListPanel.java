package org.ucvts.comics.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.controller.ViewManager;
import org.ucvts.comics.model.Customer;

@SuppressWarnings("serial")
public class CustomerListPanel extends JPanel implements ActionListener {

    private ViewManager manager;



    public CustomerListPanel(ViewManager manager, Customer customer) {
        super(new BorderLayout());

        this.manager = manager;
        this.init(customer);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }



    private void init(Customer c) {
        JPanel content = getContentPanel(c);
        JPanel actions = getActionPanel(c);

        this.add(content, BorderLayout.CENTER);
        this.add(actions, BorderLayout.EAST);
        this.add(new JSeparator(), BorderLayout.SOUTH);
    }



    private JPanel getContentPanel(Customer c) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel title = new JLabel(c.getFirstName() + " " + c.getLastName());
        JLabel author = new JLabel("Address " + c.getFormattedAddress());
        JLabel copies = new JLabel("Customer ID " + c.getCustomerId() + " • Email " + c.getEmail() + " • Telephone " + c.getPhone());

        title.setFont(new Font("DialogInput", Font.BOLD, 18));
        author.setFont(new Font("DialogInput", Font.ITALIC, 12));
        copies.setFont(new Font("DialogInput", Font.ITALIC, 12));

        panel.add(title);
        panel.add(author);
        panel.add(copies);

        return panel;
    }



    private JPanel getActionPanel(Customer c) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(20,10,20,10));

        JButton buy = new JButton("Delete");
        buy.putClientProperty("id", c.getCustomerId());
        buy.putClientProperty("type", "DELETE");
        buy.addActionListener(this);

        JButton edit = new JButton("Edit");
        edit.putClientProperty("id", c.getCustomerId());
        edit.putClientProperty("type", "EDIT");
        edit.addActionListener(this);

        panel.add(buy, BorderLayout.NORTH);
        panel.add(edit, BorderLayout.SOUTH);

        return panel;
    }



//    private String convertDate(long date) {
//        String dateStr = String.valueOf(date);
//
//        int year = Integer.valueOf(dateStr.substring(0, 4));
//        int month = Integer.valueOf(dateStr.substring(4, 6));
//        int day = Integer.valueOf(dateStr.substring(6));
//
//        return getMonth(month) + " " + day + ", " + year;
//    }
//
//
//
//    private String getMonth(int month) {
//        switch (month) {
//            case 1: return "January";
//            case 2: return "February";
//            case 3: return "March";
//            case 4: return "April";
//            case 5: return "May";
//            case 6: return "June";
//            case 7: return "July";
//            case 8: return "August";
//            case 9: return "September";
//            case 10: return "October";
//            case 11: return "November";
//            case 12: return "December";
//            default: return "";
//        }
//    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        Long id = (Long) source.getClientProperty("id");
        String type = (String) source.getClientProperty("type");

        for (Customer c : manager.getCustomers()) {
            if (id.longValue() == c.getCustomerId() && type.equals("DELETE")) {
                manager.removeCustomerFromList(c);
            } else if (id.longValue() == c.getCustomerId() && type.equals("EDIT")) {
                manager.attachCustomer(c);
                manager.switchTo(MidtownComics.CustomerView);
            }
        }

    }
}
