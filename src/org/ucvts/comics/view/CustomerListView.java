package org.ucvts.comics.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.controller.ViewManager;

@SuppressWarnings("serial")
public class CustomerListView extends JPanel implements ActionListener {

    private ViewManager manager;
    private JScrollPane scroll;
    private JButton addProduct;
    private JButton search;
    private JTextField searchField;
    private JButton back;



    public CustomerListView(ViewManager manager) {
        super(new BorderLayout());

        this.manager = manager;
        this.init();
    }



    public void refreshCustomerList() {
        this.remove(scroll);
        manager.switchTo(MidtownComics.InventoryView);
        manager.switchTo(MidtownComics.CustomerListView);

        initInventoryList();
    }



    private void init() {
        initHeader();
        initInventoryList();
        initFooter();
    }



    private void initHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Midtown Comics");
        label.setFont(new Font("DialogInput", Font.BOLD, 21));
        label.setBorder(new EmptyBorder(15, 15, 10, 0));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 400, 15, 15));



        searchField = new JTextField();
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() < 48 || e.getKeyChar() > 57) {
                    e.consume();
                }
            }
        });


        search = new JButton("Search By ID");
        search.putClientProperty("id", -1L);
        search.addActionListener(this);



        buttonPanel.add(searchField, BorderLayout.NORTH);
        buttonPanel.add(search, BorderLayout.SOUTH);
        panel.add(buttonPanel);
        this.add(panel, BorderLayout.SOUTH);



        panel.add(label, BorderLayout.WEST);
        this.add(panel, BorderLayout.NORTH);
    }



    private void initInventoryList() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(15, 15, 15, 15));

        if(searchField.getText().length() != 0) {
            for (int i = 0; i < manager.getCustomers().size(); i++) {
                if(Long.toString(manager.getCustomers().get(i).getCustomerId()).equals(searchField.getText())) {
                    body.add(new CustomerListPanel(manager, manager.getCustomers().get(i)));
                }
            }
        }
        else  {
            for (int i = 0; i < manager.getCustomers().size(); i++) {
                body.add(new CustomerListPanel(manager, manager.getCustomers().get(i)));
            }
        }



        scroll = new JScrollPane(body);
        this.add(scroll, BorderLayout.CENTER);
    }



    private void initFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));

        addProduct = new JButton("Add Customer");
        addProduct.putClientProperty("id", -1L);
        addProduct.addActionListener(this);

        back = new JButton("Back");
        back.putClientProperty("id", -1L);
        back.addActionListener(this);

        panel.add(back, BorderLayout.WEST);
        panel.add(addProduct, BorderLayout.EAST);
        this.add(panel, BorderLayout.SOUTH);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(addProduct)) {
            searchField.setText("");
            manager.switchTo(MidtownComics.CustomerView);
        } else if (source.equals(back)) {
            searchField.setText("");
            manager.switchTo(MidtownComics.InventoryView);
        } else if(source.equals(search)) {
            refreshCustomerList();
        }
    }
}

