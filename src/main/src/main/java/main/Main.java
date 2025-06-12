package main;

import persistence.OrderPersistence;

public class Main {
    public static void main(String[] args) {
        OrderPersistence orderPersistence = new OrderPersistence();
        orderPersistence.getOrders();
        
    }
}
