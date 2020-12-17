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
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.controller.ViewManager;
import org.ucvts.comics.model.NumberSpinner;
import org.ucvts.comics.model.Order;
import org.ucvts.comics.model.OrderItem;

@SuppressWarnings("serial")
public class OrderItemListPanel extends JPanel implements ActionListener {

    private ViewManager manager;
    private Order order;
    private OrderItem orderItem;
    private NumberSpinner quant;



    public OrderItemListPanel(ViewManager manager, OrderItem orderitem, Order order) {
        super(new BorderLayout());

        this.order = order;
        this.manager = manager;
        this.orderItem = orderitem;
        this.init(orderItem);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }



    private void init(OrderItem oi) {
        if(oi != null) {
            JPanel content = getContentPanel(oi);
            JPanel actions = getActionPanel(oi);
            this.add(content, BorderLayout.CENTER);
            this.add(actions, BorderLayout.EAST);
            this.add(new JSeparator(), BorderLayout.SOUTH);
        }
    }



    private JPanel getContentPanel(OrderItem oi) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel title = new JLabel(oi.getProduct().getTitle() + " #" + oi.getProduct().getIssue());
        JLabel author = new JLabel("Quantity " + oi.getQuantity() + " • Price " + oi.getPrice());

        title.setFont(new Font("DialogInput", Font.BOLD, 18));
        author.setFont(new Font("DialogInput", Font.ITALIC, 12));



        panel.add(title);
        panel.add(author);

        return panel;
    }



    private JPanel getActionPanel(OrderItem oi) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(new EmptyBorder(20,10,20,10));

        quant = new NumberSpinner(oi.getQuantity(), 0, oi.getProduct().getCopies() + oi.getQuantity(), 1);
        //System.out.println(oi.getProduct().getProductId());
        quant.putClientProperty("id", Long.toString(oi.getItemId()));

        JSpinner quantSpinner = new JSpinner(quant);

        JButton buy = new JButton("Update");
        buy.putClientProperty("id", oi.getItemId());
        buy.putClientProperty("type", "UPDATE");
        buy.addActionListener(this);

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            quant.setMaximum((Integer) quant.getValue());
            quant.setMinimum((Integer) quant.getValue());
            buy.setEnabled(false);

        } else {
            buy.setEnabled(true);
            quant.setMaximum(oi.getProduct().getCopies() + oi.getQuantity());
            quant.setMinimum(0);

        }

        panel.add(quantSpinner, BorderLayout.NORTH);
        panel.add(buy, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        Long id = (Long) source.getClientProperty("id");
        String type = (String) source.getClientProperty("type");

        for (OrderItem oi : manager.getOrderItems(order)) {
            if (id.longValue() == oi.getItemId() && type.equals("UPDATE")) {
                if((Integer) quant.getValue() == 0) {
                    manager.removeItemFromOrder(oi);
                    manager.switchTo(MidtownComics.InventoryView);
                    manager.switchTo(MidtownComics.OrderView);
                }
                else {
                    manager.modifyItemQuantityInOrder(oi, (Integer) quant.getValue());
                    manager.switchTo(MidtownComics.OrderView);
                }
            }

        }

    }
}
