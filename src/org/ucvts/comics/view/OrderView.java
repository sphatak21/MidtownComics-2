package org.ucvts.comics.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.controller.ViewManager;
import org.ucvts.comics.model.Order;


@SuppressWarnings("serial")
public class OrderView extends JPanel implements ActionListener {

    private ViewManager manager;
    private Order order;
    private OrderForm orderForm;
    private JButton save;
    private JButton remove;
    private JButton cancel;



    public OrderView(ViewManager manager) {
        super(new BorderLayout());

        this.manager = manager;
        this.orderForm = new OrderForm(manager);
        this.init();
    }



    public void setOrder(Order order) {
        this.order = order;

        remove.setEnabled(true);
        orderForm.updateFields(order);

        if(order != null && order.getStatus().toLowerCase().equals("closed") ) {
            remove.setEnabled(false);
            save.setEnabled(false);

        } else {
            remove.setEnabled(true);
            save.setEnabled(true);

        }
    }



    private void init() {
        initHeader();
        initProductForm();
        initFooter();
    }



    private void initHeader() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Midtown Comics");
        label.setFont(new Font("DialogInput", Font.BOLD, 21));
        label.setBorder(new EmptyBorder(15, 15, 10, 0));

        panel.add(label, BorderLayout.WEST);
        this.add(panel, BorderLayout.NORTH);
    }



    private void initProductForm() {
        this.add(new JScrollPane(orderForm), BorderLayout.CENTER);
    }

    public void refreshOrderItemList() {
        if(orderForm != null && order != null) {
            orderForm.refreshOrderItemList(order);
        }
    }



    private void initFooter() {
        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.setBorder(new EmptyBorder(10, 15, 15, 15));

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        remove = new JButton("Remove");
        remove.setEnabled(false);
        remove.addActionListener(this);

        save = new JButton("Save");
        save.addActionListener(this);

        panel.add(cancel);
        panel.add(remove);
        panel.add(save);
        this.add(panel, BorderLayout.SOUTH);


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.equals(save)) {
            if (order == null) {
                //System.out.println("This should never print (Order == null) in OrderView");
                //manager.addOrderToList(orderForm.getOrderFromFields());
                manager.switchTo(MidtownComics.OrderListView);
            } else {
                manager.modifyOrderInList(orderForm.getOrderFromFields());
                manager.switchTo(MidtownComics.OrderListView);
            }
        } else if (source.equals(remove)) {
            manager.removeOrderFromList(order);
        } else if (source.equals(cancel)) {
            manager.detachOrder();
            manager.switchTo(MidtownComics.OrderListView);
        }
    }
}