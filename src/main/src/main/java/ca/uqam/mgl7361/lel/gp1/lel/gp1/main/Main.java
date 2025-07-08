package ca.uqam.mgl7361.lel.gp1.lel.gp1.main;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.CartDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.AccountAPI;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.AccountAPIImpl;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.CartAPI;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.CartAPIImpl;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.presentation.DeliveryAPIImpl;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.order.presentation.OrderAPIImpl;
import ca.uqam.mgl7361.lel.gp1.payment.dto.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.payment.dto.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.payment.presentation.InvoiceAPIImpl;
import ca.uqam.mgl7361.lel.gp1.shop.dto.*;
import ca.uqam.mgl7361.lel.gp1.shop.presentation.BookAPI;
import ca.uqam.mgl7361.lel.gp1.shop.presentation.BookAPIImpl;
import ca.uqam.mgl7361.lel.gp1.shop.presentation.BookAttributeAPI;
import ca.uqam.mgl7361.lel.gp1.shop.presentation.BookAttributeAPIImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        BookAPI bookAPI = new BookAPIImpl();
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

        // Get a CartAPI instance for the signed-in account
        CartAPI cartAPI = new CartAPIImpl();

        // Retrieve the cart for the account, creating the cart if it
        // doesn't exist

        System.out.println("successful getCart test case");
        cartAPI.getCart(account);
        List<BookDTO> books = new ArrayList<BookDTO>(); // TODO : PLEASE HANDLE EXCEPTIONS CORRECTLY : IN API
        // IMPLEMENTATIONS !
        try {
            books = bookAPI.getBooksBy(
                    Map.of(
                            BookProperty.TITLE, "%"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        BookDTO bookToRemoveLater = books.get(0); // Get the first book to remove later

        System.out.println("successful add book to cart test cases");
        books.forEach(book -> {
            cartAPI.add(book, account);
        });

        // Retrieve the cart again to see the added book
        cartAPI.getCart(account);

        // Add a book to the cart
        cartAPI.add(bookToRemoveLater, account);
        cartAPI.getCart(account);

        // Remove a book from the cart

        System.out.println("successful remove book from cart test case");
        cartAPI.remove(bookToRemoveLater, account);

        // Retrieve the cart again to see the added book
        cartAPI.getCart(account);

        // clear the cart twice to test the clearCart method error handling
        System.out.println("successful clear cart test case");
        cartAPI.clearCart(account);
        System.out.println("error second clear non-existent cart test case");
        cartAPI.clearCart(account);

        // try to delete a book that is not in the cart
        System.out.println("error remove book not in cart test case");
        cartAPI.remove(bookToRemoveLater, account);

        // Retrieve the cart again to see that it is empty
        System.out.println("successful getCart after clear test case");
        cartAPI.getCart(account);

        // Add multiple books to the cart

        accountAPI.delete(account);

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
                Date.valueOf(LocalDate.now().plusDays(3)),
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

    public static void disableLogging() {
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();

        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler); // Retire tous les handlers
        }

        rootLogger.setLevel(Level.OFF); // Désactive les logs
    }


    private static void scenarioStep(String step) {
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_BLUE + step + ANSI_RESET);

    }

    private static void scenarioDetailsStep(String step) {
        String ANSI_ORANGE = "\u001B[33m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_ORANGE + step + ANSI_RESET);

    }

    public static void scenario() throws Exception { // scenario described in the provided specifications
        Main.disableLogging();

        // signup
        // create an account with first name, last name, phone, email and password
        String firstName = "Alice";
        String lastName = "Smith";
        String phone = "1234567890";
        String email = "alice.smith@mail.com";
        String password = "P@ssword123";

        Main.scenarioStep("1. L'utilisateur devrait créer un compte et se connecter pour pouvoir utiliser l'application");
        AccountAPI accountAPI = new AccountAPIImpl();
        Main.scenarioStep("1.1 Création d'un compte");
        accountAPI.signup(firstName, lastName, phone, email, password);

        Main.scenarioStep("1.2 Tentative de création du même compte");
        accountAPI.signup(firstName, lastName, phone, email, password);

        Main.scenarioStep("1.3 Tentative de création d'un autre compte");
        firstName = "Paul";
        lastName = "Smith";
        phone = "1234567891";
        email = "paul.smith@mail.com";
        password = "P@ssword1234";
        accountAPI.signup(firstName, lastName, phone, email, password);

        Main.scenarioStep("1.4 Connexion à au deuxième compte crée");
        AccountDTO account = accountAPI.signin(email, password);
        Main.scenarioDetailsStep("Connecté avec le compte - " + account);
        /* Should show that signin was a success */

        // get the cart for the account
        // if the cart does not exist, it should be created
        CartAPI cartAPI = new CartAPIImpl();
        cartAPI.getCart(account);
        /* Should show that the cart was retrieved successfully */

        Main.scenarioStep("2. Sélectionner un ou plusieurs livres");
        Main.scenarioStep("2.1 Obtenir les catégories existantes");

        // browse books using : author, title, isbn, category (one example for each
        // minimum)
        BookAttributeAPI bookAttributeAPI = new BookAttributeAPIImpl();
        BookAPI bookAPI = new BookAPIImpl();

        // Authors
        List<AuthorDTO> authors = bookAttributeAPI.getAuthors();
        Main.scenarioDetailsStep("Auteurs disponibles :");
        authors.forEach(author -> Main.scenarioDetailsStep(author.getName()));

        // Categories
        List<CategoryDTO> categories = bookAttributeAPI.getCategories();
        Main.scenarioDetailsStep("Catégories disponibles :");
        categories.forEach(category -> Main.scenarioDetailsStep(category.getName()));

        // Editeurs
        List<PublisherDTO> publishers = bookAttributeAPI.getPublishers();
        Main.scenarioDetailsStep("Publishers disponibles :");
        publishers.forEach(publisher -> Main.scenarioDetailsStep(publisher.getName()));

        // Livres
        Main.scenarioStep("2.2.1 Rechercher tous les livres");
        List<BookDTO> booksByTitle = new ArrayList<BookDTO>();
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.TITLE, "%"));
        booksByTitle.forEach(book -> Main.scenarioDetailsStep(book.getTitle()));

        Main.scenarioStep("2.2.2 Rechercher les livres à partir d'un titre ('Les Misérables')");
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.TITLE, "Les Misérables"));
        booksByTitle.forEach(book -> Main.scenarioDetailsStep(book.getTitle()));

        Main.scenarioStep("2.2.3 Rechercher les livres à partir d'un titre partiel ('The')");
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.TITLE, "The"));
        booksByTitle.forEach(book -> Main.scenarioDetailsStep(book.getTitle()));

        Main.scenarioStep("2.2.4 Rechercher les livres à partir d'un auteur ('Camus')");
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.AUTHOR, "Camus"));
        booksByTitle.forEach(book -> Main.scenarioDetailsStep(book.getTitle()));

        Main.scenarioStep("2.2.5 Rechercher les livres à partir d'un editeur ('Gal')");
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.PUBLISHER, "Gal"));
        booksByTitle.forEach(
                book -> Main.scenarioDetailsStep(book.getTitle() + " - Editor : " + book.getPublisher().getName())
        );

        Main.scenarioStep("2.2.6 Rechercher les livres à partir d'un mélange de caractéristiques" +
                " - titre(partiel) : 'Lover', " +
                "ISBN(partiel): '06951', " +
                "Editeur: 'Seuil', " +
                "Auteur: 'Duras'");
        // Exemple de mélange d'une partie des caractéristiques
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.TITLE, "Lover",
                BookProperty.ISBN, "06951",
                BookProperty.PUBLISHER, "Seuil",
                BookProperty.AUTHOR, "Duras"
        ));
        booksByTitle.forEach(
                book -> Main.scenarioDetailsStep(book.getTitle() +
                        "\n - ISBN : " + book.getIsbn() +
                        "\n - Editor : " + book.getPublisher().getName() +
                        "\n - Author : " + book.getAuthors().getFirst().getName()
                )
        );

        Main.scenarioStep("3. Mettre les livres sélectionnés dans le panier");
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        // Livre pas en stock
        Main.scenarioDetailsStep("Le livre The Lover n'est pas en stock donc l'ajout au panier n'est pas possible");
        BookDTO book = booksByTitle.getFirst();
        cartAPI.add(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        // Livre devrait être en stock (first run ie il n'y a pas trop de livre dans le panier et ça ne dépasse pas le stock)
        Main.scenarioDetailsStep("Le livre Les Misérables est en stock et dois bien s'ajouter au panier (s'il n'y en a pas trop dans le panier)");
        book = bookAPI.getBooksBy(Map.of(BookProperty.TITLE, "Les Misérables")).getFirst();
        cartAPI.add(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioDetailsStep("Ajout une deuxième fois du même livre");
        book = bookAPI.getBooksBy(Map.of(BookProperty.TITLE, "Les Misérables")).getFirst();
        cartAPI.add(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioDetailsStep("Ajout d'un autre livre");
        book = bookAPI.getBooksBy(Map.of(BookProperty.TITLE, "Stranger")).getFirst();
        cartAPI.add(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioStep("4. Supprimer, du panier, un ou des livres qu'on ne désire plus acheter;");
        Main.scenarioStep("4.1 Suppression du dernier livre ajouté ('The Stranger')");
        cartAPI.remove(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioStep("4.2 Suppression de l'un des livres ('Les Misérables')");
        CartDTO cart = cartAPI.getCart(account);
        BookDTO lesMiserables = bookAPI.getBooksBy(Map.of(BookProperty.TITLE, "Les Misérables")).getFirst();
        cartAPI.remove(lesMiserables, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioStep("5. Refaire les étapes 1 à 4 autant de fois qu'on veut;");

        Main.scenarioStep("5.1.1 Tentative de création d'un autre compte");
        firstName = "John";
        lastName = "Doe";
        phone = "1234567892";
        email = "JohnDoe@mail.com";
        password = "P@ssword1234";
        accountAPI.signup(firstName, lastName, phone, email, password);

        Main.scenarioStep("5.1.2 Connexion à au deuxième compte crée");
        account = accountAPI.signin(email, password);
        Main.scenarioDetailsStep("Connecté avec le compte - " + account);

        Main.scenarioStep("5.2 Recherche de livre (titre: 'The')");
        booksByTitle = bookAPI.getBooksBy(Map.of(
                BookProperty.TITLE, "The"));
        booksByTitle.forEach(bookFor -> Main.scenarioDetailsStep(bookFor.getTitle()));

        Main.scenarioStep("5.3 Mettre les livres sélectionnés dans le panier");
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioDetailsStep("Ajout d'un livre 'The Stranger'");
        book = bookAPI.getBooksBy(Map.of(BookProperty.TITLE, "Stranger")).getFirst();
        cartAPI.add(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioStep("5.4 Supprimer, du panier, un ou des livres qu'on ne désire plus acheter;");
        Main.scenarioDetailsStep("Suppression du dernier livre ajouté ('The Stranger')");
        cartAPI.remove(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioDetailsStep("5.3 Ajout d'un deuxième livre 'The Stranger'");
        book = bookAPI.getBooksBy(Map.of(BookProperty.TITLE, "Stranger")).getFirst();
        cartAPI.add(book, account);
        Main.scenarioDetailsStep("Contenu du panier : " + cartAPI.getCart(account));

        Main.scenarioStep("À partir du panier, passer au paiement et régler la facture; les livres du panier\n" +
                "constituent alors une nouvelle commande où chaque livre de la commande a un status \"En\n" +
                "attente de livraison\"");

        InvoiceAPIImpl invoiceAPI = new InvoiceAPIImpl();
        Main.scenarioStep("6.1 Simulation de paiement réussi (par carte), génération de la facture et de la commande");
        InvoiceDTO invoice = invoiceAPI.createInvoice(account, PaymentMethod.CARD);
        Main.scenarioDetailsStep("Facture créée : " + invoice);

        DeliveryAPIImpl deliveryAPI = new DeliveryAPIImpl();
        OrderAPIImpl orderAPI = new OrderAPIImpl();

        OrderDTO order = orderAPI.findOrderByOrderNumber(invoice.getOrderNumber());
        AddressDTO address = new AddressDTO();
        address.setId(1);

        Main.scenarioStep("8 : Le système se charge de la livraison de la commande aux dates de livraison prévues");
        DeliveryDTO delivery = deliveryAPI.createDelivery(
                address,
                Date.valueOf(LocalDate.now().plusDays(3)),
                "In Transit",
                order
        );

        Main.scenarioDetailsStep("Created Delivery: ");
        Main.scenarioDetailsStep("Status: " + delivery.getDeliveryStatus());
        Main.scenarioDetailsStep("Delivery Date: " + delivery.getDeliveryDate());
        Main.scenarioDetailsStep("Order: " + delivery.getOrder().getOrderNumber());

        Main.scenarioStep("9 : Voir la liste des commandes en attente de livraison ainsi que l'historique des commandes livrées");
        List<DeliveryDTO> pendingDeliveries = deliveryAPI.getAllOrdersInTransit();
        Main.scenarioDetailsStep("Pending Deliveries (" + pendingDeliveries.size() + ") :");
        for (DeliveryDTO pendingDelivery : pendingDeliveries) {
            Main.scenarioDetailsStep("Order: " + pendingDelivery.getOrder().getOrderNumber() +
                    ", Status: " + pendingDelivery.getDeliveryStatus());
        }

        List<DeliveryDTO> deliveredDeliveries = deliveryAPI.getAllOrdersDelivered();
        Main.scenarioDetailsStep("Delivered Deliveries (" + deliveredDeliveries.size() + ") :");
        for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
            Main.scenarioDetailsStep("Order: " + deliveredDelivery.getOrder().getOrderNumber() +
                    ", Status: " + deliveredDelivery.getDeliveryStatus());
        }

        Main.scenarioStep("10 : Une fois un livre est livré, sa date de livraison dans la commande est mise à jour et le statut passe de \"En attente de livraison\" à \"Livré\"");
        deliveryAPI.updateStatusToDelivered(delivery);
        Main.scenarioDetailsStep("Updated Status: " + delivery.getDeliveryStatus());

        pendingDeliveries = deliveryAPI.getAllOrdersInTransit();
        Main.scenarioDetailsStep("Pending Deliveries (" + pendingDeliveries.size() + ") :");
        for (DeliveryDTO pendingDelivery : pendingDeliveries) {
            Main.scenarioDetailsStep("Order: " + pendingDelivery.getOrder().getOrderNumber() +
                    ", Status: " + pendingDelivery.getDeliveryStatus());
        }

        deliveredDeliveries = deliveryAPI.getAllOrdersDelivered();
        Main.scenarioDetailsStep("Delivered Deliveries (" + deliveredDeliveries.size() + ") :");
        for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
            Main.scenarioDetailsStep("Order: " + deliveredDelivery.getOrder().getOrderNumber() +
                    ", Status: " + deliveredDelivery.getDeliveryStatus());
        }
    }

    public static void main(String[] args) {
        GlobalSafeExecutor.run(() -> {
            // Main.account();
            // Main.cart();
            // Main.shop();
            // Main.order();
            // Main.delivery();
            Main.scenario();
        });
    }
}
