
package org.ucvts.comics.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.controller.ViewManager;

    @SuppressWarnings("serial")
    public class OrderListView extends JPanel implements ActionListener {

        private ViewManager manager;
        private JScrollPane scroll;
        private JButton search;
        private JTextField searchField;
        private JButton back;



        public OrderListView(ViewManager manager) {
            super(new BorderLayout());

            this.manager = manager;
            this.init();
        }



        public void refreshOrderList() {
            this.remove(scroll);
            manager.switchTo(MidtownComics.InventoryView);
            manager.switchTo(MidtownComics.OrderListView);

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
                for (int i = 0; i < manager.getOrders().size(); i++) {
                    if(Long.toString(manager.getOrders().get(i).getOrderId()).equals(searchField.getText())) {
                        body.add(new OrderListPanel(manager, manager.getOrders().get(i)));
                    }
                }
            }
            else  {
                for (int i = 0; i < manager.getOrders().size(); i++) {
                    body.add(new OrderListPanel(manager, manager.getOrders().get(i)));
                }
            }



            scroll = new JScrollPane(body);
            this.add(scroll, BorderLayout.CENTER);
        }



        private void initFooter() {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(new EmptyBorder(10, 15, 15, 15));


            back = new JButton("Back");
            back.putClientProperty("id", -1L);
            back.addActionListener(this);

            panel.add(back, BorderLayout.WEST);
            this.add(panel, BorderLayout.SOUTH);
        }



        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();

            if (source.equals(back)) {
                manager.switchTo(MidtownComics.InventoryView);
            } else if(source.equals(search)) {
                refreshOrderList();
            }
        }
    }

