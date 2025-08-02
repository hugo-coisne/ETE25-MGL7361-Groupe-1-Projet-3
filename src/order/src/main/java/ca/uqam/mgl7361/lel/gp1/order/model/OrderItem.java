package ca.uqam.mgl7361.lel.gp1.order.model;

public class OrderItem {

    private int id;
    private String orderNumber;
    private String bookIsbn;
    private float bookPrice;
    private int quantity;

    public OrderItem(){

    }

    public OrderItem(int id, String orderNumber, String bookIsbn, float bookPrice, int quantity){
        this.setId(id);
        this.setOrderNumber(orderNumber);
        this.setBookIsbn(bookIsbn);
        this.setBookPrice(bookPrice);
        this.setQuantity(quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
