package org.ucvts.comics.model;

public class OrderItem {

    private static long lastItemId = 1L;

    private long itemId;
    private Product product;
    private int quantity;



    public OrderItem(Product product) {
        this.itemId = OrderItem.lastItemId++;
        this.product = product;
        this.quantity = 1;
    }



    public OrderItem(long itemId, Product product, int quantity) {
        this.itemId = itemId;
        this.product = product;
        this.quantity = quantity;
    }



    public double getPrice() {
        return quantity * product.getUnitPrice();
    }



    public long getItemId() {
        return itemId;
    }



    public Product getProduct() {
        return product;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
