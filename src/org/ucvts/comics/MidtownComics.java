package org.ucvts.comics;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.ucvts.comics.controller.ViewManager;
import org.ucvts.comics.view.CartView;
import org.ucvts.comics.view.InventoryView;
import org.ucvts.comics.view.ProductView;
import org.ucvts.comics.view.PaymentView;
import org.ucvts.comics.view.CustomerView;
import org.ucvts.comics.view.OrderView;
import org.ucvts.comics.view.CustomerListView;
import org.ucvts.comics.view.OrderListView;

@SuppressWarnings("serial")
public class MidtownComics extends JFrame {

    public static final int InventoryViewIndex = 0;
    public static final int ProductViewIndex = 1;
    public static final int CartViewIndex = 2;
    public static final int PaymentViewIndex = 3;
    public static final int OrderViewIndex = 4;
    public static final int CustomerViewIndex = 5;
    public static final int OrderListViewIndex = 6;
    public static final int CustomerListViewIndex = 7;

    public static final String InventoryView = "InventoryView";
    public static final String ProductView = "ProductView";
    public static final String CartView = "CartView";
    public static final String PaymentView = "PaymentView";
    public static final String OrderView = "OrderView";
    public static final String CustomerView = "CustomerView";
    public static final String OrderListView = "OrderListView";
    public static final String CustomerListView = "CustomerListView";




    public void init() {
        JPanel views = new JPanel(new CardLayout());
        ViewManager manager = ViewManager.getInstance(views);

        // add child views to the parent container

        views.add(new InventoryView(manager), InventoryView);
        views.add(new ProductView(manager), ProductView);
        views.add(new CartView(manager), CartView);
        views.add(new PaymentView(manager), PaymentView);
        views.add(new OrderView(manager), OrderView);
        views.add(new CustomerView(manager), CustomerView);
        views.add(new OrderListView(manager), OrderListView);
        views.add(new CustomerListView(manager), CustomerListView);


        // configure application frame

        this.getContentPane().add(views);
        this.setBounds(0, 0, 750, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        manager.refreshViews();
        manager.switchTo(InventoryView);
    }

    ////////// MAIN METHOD /////////////////////////////////////////////////////////

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MidtownComics().init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}