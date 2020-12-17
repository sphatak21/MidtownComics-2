package org.ucvts.comics.controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ucvts.comics.MidtownComics;
import org.ucvts.comics.dao.CustomerDAO;
import org.ucvts.comics.dao.DAO;        // this is a new import statement
import org.ucvts.comics.dao.OrderDAO;
import org.ucvts.comics.dao.OrderItemDAO;
import org.ucvts.comics.dao.ProductDAO; // this is a new import statement
import org.ucvts.comics.model.Customer;
import org.ucvts.comics.model.Order;
import org.ucvts.comics.model.OrderItem;
import org.ucvts.comics.model.Product;
import org.ucvts.comics.view.CartView;
import org.ucvts.comics.view.CustomerListView;
import org.ucvts.comics.view.CustomerView;
import org.ucvts.comics.view.InventoryView;
import org.ucvts.comics.view.OrderListView;
import org.ucvts.comics.view.PaymentView;
import org.ucvts.comics.view.ProductView;
import org.ucvts.comics.view.OrderView;

public class ViewManager {

    private static ViewManager manager;

    private Container views;
    private Order order;

    /*
     * A private constructor, implementing the singleton pattern.
     *
     * The singleton pattern guarantees that only a single instance
     * of the ViewManager class will every be instantiated.
     *
     * @param views
     */

    private ViewManager(Container views) {
        this.views = views;

        // rudimentary error handling. a more mature application
        // would handle database exceptions more gracefully, but
        // we'll just print the stack trace for now.

        try {
            DAO.buildDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshViews() {
        refreshInventoryList();
        refreshOrderList();
        refreshCustomerList();
        refreshCart();
    }

    /**
     * Returns an instance of the ViewManager class, creating one if necessary.
     *
     * @param views the parent container
     * @return an instance of the ViewManager class
     */

    public static ViewManager getInstance(Container views) {
        if (manager == null) {
            manager = new ViewManager(views);
        }

        return manager;
    }

    /**
     * Switches to the specified named view.
     *
     * @param view the view to switch to
     */

    public void switchTo(String view) {
        ((CardLayout) views.getLayout()).show(views, view);
    }

    /**
     * Attaches a product to the product view.
     *
     * @param product the product to attach
     */

    public void attachProduct(Product product) {
        ((ProductView) views.getComponent(MidtownComics.ProductViewIndex)).setProduct(product);
    }

    /**
     * Detaches a product from the product view.
     */

    public void detachProduct() {
        ((ProductView) views.getComponent(MidtownComics.ProductViewIndex)).setProduct(null);
    }


    //attaches and detaches a customer from the customer view

    public void attachCustomer(Customer customer) {
        ((CustomerView) views.getComponent(MidtownComics.CustomerViewIndex)).setCustomer(customer);
    }

    public void detachCustomer() {
        ((CustomerView) views.getComponent(MidtownComics.CustomerViewIndex)).setCustomer(null);
    }

    public void attachOrder(Order order) {
        ((OrderView) views.getComponent(MidtownComics.OrderViewIndex)).setOrder(order);
    }

    public void detachOrder() {
        ((OrderView) views.getComponent(MidtownComics.OrderViewIndex)).setOrder(null);
    }

    /**
     * Adds a new Product to inventory.
     *
     * @param product the new product
     */

    public void addProductToInventory(Product product) {
        try {
            ProductDAO.insertProduct(product);  // this is new, add product to the database

            detachProduct();
            refreshInventoryList();
            switchTo(MidtownComics.InventoryView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomerToList(Customer customer) {
        try {
            CustomerDAO.insertCustomer(customer);  // this is new, add product to the database

            detachCustomer();
            refreshCustomerList();
            switchTo(MidtownComics.CustomerListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //this will only happen when submit button pressed
    public void addOrderToList(Order order) {
    	try {
           OrderDAO.insertOrder(order);  // this is new, add product to the database

            detachOrder();
            refreshOrderList();
           switchTo(MidtownComics.OrderListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
  }

    /**
     * Modifies an existing Product in inventory.
     *
     * @param product the modified product
     */

    public void modifyProductInInventory(Product product) {
        try {
            ProductDAO.updateProduct(product);  // this is new, updates product in the database

            detachProduct();
            refreshInventoryList();
            switchTo(MidtownComics.InventoryView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyCustomerInList(Customer customer) {
        try {
            CustomerDAO.updateCustomer(customer);  // this is new, updates product in the database

            detachCustomer();
            refreshCustomerList();
            switchTo(MidtownComics.CustomerListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyOrderInList(Order order) {
        try {

            OrderDAO.updateOrder(order);  // this is new, updates product in the database

            detachOrder();
            refreshOrderList();
            switchTo(MidtownComics.OrderListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes an existing Product from inventory.
     *
     * @param product the product to be removed
     */

    public void removeProductFromInventory(Product product) {
        try {
            ProductDAO.deleteProduct(product);  // this is new, deletes product from the database

            detachProduct();
            refreshInventoryList();
            switchTo(MidtownComics.InventoryView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCustomerFromList(Customer customer) {
        try {

            ArrayList<Order> orderList = (ArrayList<Order>) OrderDAO.getOrders();
            for(int i = 0; i < orderList.size(); i++) {
                if(orderList.get(i).getCustomer().getCustomerId() == customer.getCustomerId()) {
                    removeOrderFromList(orderList.get(i));
                }
            }
            CustomerDAO.deleteCustomer(customer);

            detachCustomer();
            refreshCustomerList();
            switchTo(MidtownComics.CustomerListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeOrderFromList(Order order) {
        try {
            for(int i = 0; i < order.getItems().size(); i++) {
                OrderItemDAO.deleteOrderItem(order.getItems().get(i));
            }
            OrderItemDAO.deleteOrderItems(order.getOrderId());
            OrderDAO.deleteOrder(order);

            detachOrder();
            refreshOrderList();
            switchTo(MidtownComics.OrderListView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an OrderItem to an Order, creating the Order first if necessary.
     *
     * @param item the item to be added
     */

    public void addItemToOrder(OrderItem item) {
        if (order == null) {
            order = new Order();
        }

        order.addItem(item);
        refreshCart();
        updateOrderTotal();
        manager.switchTo(MidtownComics.CartView);
    }

    /**
     * Modify the quantity of an OrderItem in an Order.
     *
     * @param product  the product to be modified
     * @param quantity the new quantity
     */

    public void modifyItemQuantityInOrder(Product product, int quantity) {
        int index = findItemInOrder(product);
        order.getItems().get(index).setQuantity(quantity);

        refreshCart();
        updateOrderTotal();
        switchTo(MidtownComics.InventoryView);
        switchTo(MidtownComics.CartView);
    }


    public void modifyItemQuantityInOrder(OrderItem oi, int quant) {



        try {
            int prevquant = oi.getQuantity();
            oi.setQuantity(quant);
            OrderItemDAO.updateOrderItem(oi);
            Product p = ProductDAO.getProduct(oi.getProduct().getProductId());
            p.setCopies(p.getCopies() + (prevquant - quant));
            ProductDAO.updateProduct(p);
            refreshOrderItemList();
            refreshInventoryList();
            switchTo(MidtownComics.OrderListView);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Remove an OrderItem from an existing Order.
     *
     * @param product the product to be removed
     */

    public void removeItemFromOrder(Product product) { // for orders not submitted yet
        int index = findItemInOrder(product);
        order.getItems().remove(index);

        refreshCart();
        updateOrderTotal();
        switchTo(MidtownComics.InventoryView);

        if (order.getItems().size() > 0) {
            switchTo(MidtownComics.CartView);
        }
    }

    public void removeItemFromOrder(OrderItem oi) {
        try {
            OrderItemDAO.deleteOrderItem(oi);
            Product p = ProductDAO.getProduct(oi.getProduct().getProductId());
            p.setCopies(p.getCopies() + oi.getQuantity());
            ProductDAO.updateProduct(p);
            refreshOrderItemList();
            refreshInventoryList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the OrderItem quantity by Product.
     *
     * @param product the product whose quantity we need
     * @return the order item quantity
     */

    public int getOrderItemQuantity(Product product) {
        int index = findItemInOrder(product);

        return index != -1 ? order.getItems().get(index).getQuantity() : 0;
    }

    /**
     * Returns the subtotal for this Product.
     *
     * @param product the product whose subtotal we need
     * @return the subtotal
     */

    public double getSubtotal(Product product) {
        int index = findItemInOrder(product);

        return index != -1 ? order.getItems().get(index).getPrice() : 0;
    }

    /**
     * Returns whether or not the Product already exists in the Order.
     *
     * @param product the product
     * @return whether or not it exists in the order
     */

    public boolean productExistsInOrder(Product product) {
        if (order == null) {
            return false;
        }

        return findItemInOrder(product) != -1;
    }

    /**
     * Submits an order. Aside from updating the inventory, this
     * is all for show. We'll change that later.
     *
     * @throws SQLException
     */

    public void submitOrder(Long cid) throws SQLException {
        order.setCustomer(CustomerDAO.getCustomer(cid));
        order.setTotal(order.calculateTotal());
        Long orderid = OrderDAO.insertOrderReturnId(order);
        for (int i = 0; i < order.getItems().size(); i++) {
            OrderItem item = order.getItems().get(i);
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            int copies = product.getCopies();

            product.setCopies(copies - quantity);
            modifyProductInInventory(product);
            OrderItemDAO.insertOrderItem(item, orderid);

            refreshOrderList();
            switchTo(MidtownComics.InventoryView);


        }



        order = null;
        clearOrder();
    }

    /**
     * Retrieves the inventory.
     *
     * @return the inventory
     */

    public List<Product> getInventory() {
        try {
            return ProductDAO.getProducts();    // this is new, retrieves all products from the database
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    //retrieves all customers

    public List<Customer> getCustomers() {
        try {
            return CustomerDAO.getCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Order> getOrders() {
        try {
            return OrderDAO.getOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OrderItem> getOrderItems(Order order) {
        try {
            return OrderItemDAO.getOrderItems(order.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the current order.
     *
     * @return the current order
     */

    public Order getOrder() {
        return order;
    }

    /*
     * Refreshes the inventory list in the InventoryView.
     */

    private void refreshInventoryList() {
        ((InventoryView) views.getComponent(MidtownComics.InventoryViewIndex)).refreshInventoryList();
    }

    private void refreshCustomerList() {
        ((CustomerListView) views.getComponent(MidtownComics.CustomerListViewIndex)).refreshCustomerList();
    }
    private void refreshOrderList() {
        ((OrderListView) views.getComponent(MidtownComics.OrderListViewIndex)).refreshOrderList();
    }
    private void refreshOrderItemList() {
        ((OrderView) views.getComponent(MidtownComics.OrderViewIndex)).refreshOrderItemList();
    }

    /*
     * Refreshes the cart in the CartView.
     */

    private void refreshCart() {
        ((CartView) views.getComponent(MidtownComics.CartViewIndex)).refreshCart();
    }

    /*
     * Updates the order total in the OrderView.
     */

    private void updateOrderTotal() {
        ((PaymentView) views.getComponent(MidtownComics.PaymentViewIndex)).updateOrderTotal(order.calculateTotal());
    }

    /*
     * Clears the current order in the OrverView.
     */

    private void clearOrder() {
        ((PaymentView) views.getComponent(MidtownComics.PaymentViewIndex)).clearOrder();
    }

    /*
     * Finds an OrderItem in an Order.
     *
     * @param product the product we're looking for
     * @return the index of the item in the order
     */

    private int findItemInOrder(Product product) {
        for (int i = 0; i < order.getItems().size(); i++) {
            if (order.getItems().get(i).getProduct().getProductId() == product.getProductId()) {
                return i;
            }
        }

        return -1;
    }
}
