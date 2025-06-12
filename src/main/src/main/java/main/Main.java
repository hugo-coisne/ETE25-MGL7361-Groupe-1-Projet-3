package main;

import shop.presentation.BookAPIImpl;

public class Main {
    public static void main(String[] args) {
        BookAPIImpl bookAPIImpl = new BookAPIImpl();
        bookAPIImpl.createBook("Les Misérables", "un grand livre un peu chiant à lire", "12");
    }
}
