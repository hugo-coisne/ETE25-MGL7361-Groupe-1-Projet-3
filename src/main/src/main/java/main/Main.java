package main;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;
import account.presentation.CartAPI;
import account.presentation.CartAPIImpl;
import common.DBConnection;
import delivery.dto.AddressDTO;
import delivery.dto.DeliveryDTO;
import delivery.presentation.DeliveryAPIImpl;
import order.dto.OrderDTO;
import order.presentation.OrderAPIImpl;
import shop.dto.BookDTO;
import shop.dto.BookProperty;
import shop.presentation.BookAPIImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;

public class Main {
        public static void account() {
                AccountAPI accountAPI = new AccountAPIImpl();
                String firstName = "John";
                String lastName = "Doe";
                String phone = "1234567890";
                String email = "john.doe@mail.com";
                String password = "P@ssword123";
                String wrongPassword = "Password123";

                // Try to sign in with an unregistered account
                accountAPI.signin(email, password);
                System.out.println("");

                // Create a new account
                accountAPI.signup(firstName, lastName, phone, email, password);
                System.out.println("");

                // Sign in with the newly created account but with a wrong password
                accountAPI.signin(email, wrongPassword);
                System.out.println("");

                // Sign in with the correct password
                AccountDTO account = accountAPI.signin(email, password);
                System.out.println("");

                // Change password
                String newPassword = "NewP@ssword123";
                accountAPI.changePasswordFor(account, newPassword);
                System.out.println("");

                // Try to sign in with the new password
                AccountDTO account2 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change phone number
                String newPhone = "0987654321";
                accountAPI.changePhoneFor(account2, newPhone);
                System.out.println("");

                // Try to sign in with the new password with new phone number
                AccountDTO account3 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change first name
                String newFirstName = "Jane";
                accountAPI.changeFirstNameFor(account3, newFirstName);
                System.out.println("");

                // Try to sign in with the new password with new first name
                AccountDTO account4 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change last name
                String newLastName = "Smith";
                accountAPI.changeLastNameFor(account4, newLastName);
                System.out.println("");

                // Try to sign in with the new password with new last name
                AccountDTO account5 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change email
                String newEmail = "jane.smith@mail.com";
                accountAPI.changeEmailFor(account5, newEmail);
                System.out.println("");

                // Try to sign in with the new password with new email
                AccountDTO account6 = accountAPI.signin(newEmail, newPassword);
                System.out.println("");

                // Delete the account
                accountAPI.delete(account6);
                System.out.println("");

                // Try to sign in again to confirm deletion
                accountAPI.signin(newEmail, newPassword);
                System.out.println("");
        }

        public static void cart() {
                AccountAPI accountAPI = new AccountAPIImpl();
                String firstName = "John";
                String lastName = "Doe";
                String phone = "1234567890";
                String email = "john.doe@mail.com";
                String password = "P@ssword123";

                // Create a new account
                accountAPI.signup(firstName, lastName, phone, email, password);
                System.out.println("");

                // Sign in with the correct password
                AccountDTO account = accountAPI.signin(email, password);
                System.out.println("");

                // Get a CartAPI instance for the signed-in account, creating the cart if it
                // doesn't exist
                CartAPI myCartAPI = CartAPIImpl.getCartAPI(account);

                System.out.println("getting cart for account: " + account.getEmail() + "\n");
                CartDTO cart = myCartAPI.getCart();
                System.out.println("Cart : " + cart);

        }

        public static void shop() throws Exception {
                BookAPIImpl bookAPI = new BookAPIImpl();

                BookDTO bookDTO = new BookDTO("My Book", "1234567890", 19.99);
                // bookAPI.createBook(bookDTO);
                List<BookDTO> books = bookAPI.getBooksBy(
                                Map.of(
                                                BookProperty.TITLE, "%Les Misérables%",
                                                BookProperty.DESCRIPTION, "%Hugo%",
                                                BookProperty.ISBN, "%88",
                                                BookProperty.PUBLICATION_DATE, "%1862%",
                                                BookProperty.PRICE, "19.99",
                                                BookProperty.STOCK_QUANTITY, "10",
                                                BookProperty.PUBLISHER, "Gallimard",
                                                BookProperty.CATEGORY, "NOVEL",
                                                BookProperty.AUTHOR, "%Victor%"));
                System.out.println("Books found: " + books.size());
        }

        public static void order() throws Exception {
                OrderAPIImpl orderAPI = new OrderAPIImpl();
                AccountDTO accountDTO = new AccountDTO(
                                "John",
                                "Doe",
                                "1234567890",
                                "my@email.com");
                accountDTO.setId(1); // Assuming the account with ID 1 exists
                CartDTO cartDTO = new CartDTO(1);
                cartDTO.addBookIsbn(
                                Map.of(
                                                "9782070409188t", 2, // Les Misérables
                                                "9782070360021", 1 // The Stranger
                                ));
                orderAPI.createOrder(accountDTO, cartDTO);
        }

        public static void delivery() throws Exception {
                DeliveryAPIImpl deliveryAPI = new DeliveryAPIImpl();
                OrderAPIImpl orderAPI = new OrderAPIImpl();

                OrderDTO order = orderAPI.findOrderByOrderNumber("20250623-AAAABBBB");
                AddressDTO address = new AddressDTO();
                address.setId(1);

                // Étape 8 : Le système se charge de la livraison de la commande aux dates de livraison prévues
                // pour chaque livre;
                DeliveryDTO delivery = deliveryAPI.createDelivery(
                        address,
                        LocalDate.now().plusDays(3),
                        "In Transit",
                        order
                );

                // Affiche les détails de la livraison
                System.out.println("Created Delivery: ");
                System.out.println("Status: " + delivery.getDeliveryStatus());
                System.out.println("Delivery Date: " + delivery.getDeliveryDate());
                System.out.println("Order: " + delivery.getOrder().getOrderNumber());

                // Étape 9 : Voir la liste des commandes en attente de livraison ainsi que l'historique des
                // commandes livrées
                List<DeliveryDTO> pendingDeliveries = deliveryAPI.getAllOrdersInTransit();
                System.out.println("Pending Deliveries (" + pendingDeliveries.size() + ") :");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        System.out.println("Order: " + pendingDelivery.getOrder().getOrderNumber() +
                                ", Status: " + pendingDelivery.getDeliveryStatus());
                }

                List<DeliveryDTO> deliveredDeliveries = deliveryAPI.getAllOrdersDelivered();
                System.out.println("Delivered Deliveries (" + deliveredDeliveries.size() + ") :");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        System.out.println("Order: " + deliveredDelivery.getOrder().getOrderNumber() +
                                ", Status: " + deliveredDelivery.getDeliveryStatus());
                }

                // Étape 10 : Une fois un livre est livré, sa date de livraison dans la commande est mise à jour et
                // son status passe de "En attente de livraison" à "Livré"
                deliveryAPI.updateStatusToDelivered(delivery);
                System.out.println("Updated Status: " + delivery.getDeliveryStatus());

                pendingDeliveries = deliveryAPI.getAllOrdersInTransit();
                System.out.println("Pending Deliveries (" + pendingDeliveries.size() + ") :");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        System.out.println("Order: " + pendingDelivery.getOrder().getOrderNumber() +
                                ", Status: " + pendingDelivery.getDeliveryStatus());
                }

                deliveredDeliveries = deliveryAPI.getAllOrdersDelivered();
                System.out.println("Delivered Deliveries (" + deliveredDeliveries.size() + ") :");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        System.out.println("Order: " + deliveredDelivery.getOrder().getOrderNumber() +
                                ", Status: " + deliveredDelivery.getDeliveryStatus());
                }
        }


        public static void main(String[] args) {
                GlobalSafeExecutor.run(() -> {
                        // Main.account();
                        // Main.cart();
                        // Main.shop();
                        // Main.order();
                        Main.delivery();
                });
        }
}
