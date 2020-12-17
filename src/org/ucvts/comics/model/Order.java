package org.ucvts.comics.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private static long lastOrderId = 1L;

    private long orderId;
    private Customer customer;
    private long orderDate;
    private String status;
    private ArrayList<OrderItem> items;
    private double total;



    public Order() {
        this.orderId = Order.lastOrderId++;
        this.customer = new Customer();
        this.orderDate = getDate();
        this.status = "O";
        this.items = new ArrayList<>();
        this.total = 0;
    }



    public Order(long orderId, Customer customer, long orderDate, String status, ArrayList<OrderItem> items,
                 double total)
    {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
        this.total = total;
    }

    public Order(Customer customer, long orderDate, String status, ArrayList<OrderItem> items,
                 double total)
    {
        this.orderId = Order.lastOrderId++;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
        this.total = total;
    }



    public void addItem(OrderItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(item);
        this.updateTotal();
    }


    public void setOrderId(long id) {
        this.orderId = id;
    }

    public void setOrderDate(long date) {
        this.orderDate = date;
    }

    public void setCustomer(Customer c) {
        this.customer = c;
    }

    public void setTotal(double d) {
        this.total = d;
    }

    public void removeItem(OrderItem item) {
        if (items != null) {
            items.remove(item);
        }

        this.updateTotal();
    }

    public long getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public String getStatus() { return status.substring(0,1); }

    public void setStatus(String status) {
        this.status = status;
    }

    public double calculateTotal() {
        double total = 0;
        for(int i = 0; i < items.size(); i++) {
            total += items.get(i).getPrice();
        }
        return total;
    }



    public ArrayList<OrderItem> getItems() {
        return items;
    }



    public double getTotal() {
        return total;
    }



    private long getDate() {
        LocalDateTime now = LocalDateTime.now();

        String year = String.format("%d", now.getYear());
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        return Long.valueOf(year + month + day);
    }

    private void updateTotal() {
        if (items == null) {
            this.total = 0;
        } else {
            double total = 0;

            for (OrderItem item : items) {
                total = total + item.getPrice();
            }

            this.total = total;
        }
    }
}