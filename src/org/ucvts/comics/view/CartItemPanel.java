package org.ucvts.comics.view;

import org.ucvts.comics.controller.ViewManager;
import org.ucvts.comics.model.Product;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class CartItemPanel extends JPanel implements ActionListener {
    private ViewManager manager;
    private Product product;
    private JComboBox<Integer> combo;

    /**
     * Creates an instance of the org.ucvts.comics.view.CartItemPanel class.
     *
     * @param product  the item represented by this panel
     * @param quantity the quantity of this product
     */

    public CartItemPanel(ViewManager manager, Product product) {
        super(new BorderLayout());

        this.manager = manager;
        this.product = product;
        this.init();
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }

    /*
     * Initializes all UI components.
     */

    private void init() {
        JPanel content = getContentPanel();
        JPanel actions = getActionPanel();

        this.add(content, BorderLayout.WEST);
        this.add(actions, BorderLayout.EAST);
        this.add(new JSeparator(), BorderLayout.SOUTH);
    }

    /*
     * Initializes content panel UI components.
     */

    private JPanel getContentPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        Integer[] available = new Integer[product.getCopies() + 1];
        for (int i = 0; i < available.length; i++) {
            available[i] = i;
        }

        JLabel title = new JLabel(product.getTitle() + " #" + product.getIssue());
        combo = new JComboBox<>(available);
        combo.setSelectedIndex(manager.getOrderItemQuantity(product));

        title.setFont(new Font("DialogInput", Font.BOLD, 18));
        combo.addActionListener(this);

        panel.add(title);
        panel.add(combo);

        return panel;
    }

    /*
     * Initializes action panel UI components.
     */

    private JPanel getActionPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel price = new JLabel("$" + manager.getSubtotal(product), SwingConstants.CENTER);
        price.setFont(new Font("DialogInput", Font.BOLD, 15));

        panel.add(price);

        return panel;
    }

    /*
     * Handles combobox selections in this view.
     *
     * @param e the event that triggered this action
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(combo)) {
            int quantity = (int) combo.getSelectedItem();

            if (quantity == 0) {
                manager.removeItemFromOrder(product);
            } else {
                manager.modifyItemQuantityInOrder(product, quantity);
            }
        }
    }
}
