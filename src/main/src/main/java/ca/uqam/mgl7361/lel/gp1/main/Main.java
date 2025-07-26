package ca.uqam.mgl7361.lel.gp1.main;

import ca.uqam.mgl7361.lel.gp1.common.clients.*;
import ca.uqam.mgl7361.lel.gp1.common.clients.CartAPIClient.CartBookRequest;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient.OrderRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.CreateDeliveryRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.*;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.*;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
        public static void account() {

                AccountAPIClient accountClient = Clients.accountClient;
                String firstName = "John";
                String lastName = "Doe";
                String phone = "1234567890";
                String email = "john.doe@mail.com";
                String password = "P@ssword123";

                Map<String, String> credentials = Map.of("email", email, "password", password);

                // Try to sign in with an unregistered account
                // accountClient.signin(credentials);
                // System.out.println("");

                AccountDTO account = new AccountDTO(firstName, lastName, phone, email, password);

                // Create a new account
                accountClient.signup(account);
                System.out.println("Signed up : " + account);

                // Sign in with the newly created account but with a wrong password
                // accountClient.signin(credentials);
                // System.out.println("Signed in with wrong password");

                // Sign in with the correct password
                accountClient.signin(credentials);
                System.out.println("Signed in");

                // // Change password
                // String newPassword = "NewP@ssword123";
                // accountClient.changePasswordFor(account, newPassword);
                // System.out.println("");

                // // Try to sign in with the new password
                // AccountDTO account2 = accountClient.signin(email, newPassword);
                // System.out.println("");

                // // Change phone number
                // String newPhone = "0987654321";
                // accountClient.changePhoneFor(account2, newPhone);
                // System.out.println("");

                // // Try to sign in with the new password with new phone number
                // AccountDTO account3 = accountClient.signin(email, newPassword);
                // System.out.println("");

                // // Change first name
                // String newFirstName = "Jane";
                // accountClient.changeFirstNameFor(account3, newFirstName);
                // System.out.println("");

                // // Try to sign in with the new password with new first name
                // AccountDTO account4 = accountClient.signin(email, newPassword);
                // System.out.println("");

                // // Change last name
                // String newLastName = "Smith";
                // accountClient.changeLastNameFor(account4, newLastName);
                // System.out.println("");

                // // Try to sign in with the new password with new last name
                // AccountDTO account5 = accountClient.signin(email, newPassword);
                // System.out.println("");

                // // Change email
                // String newEmail = "jane.smith@mail.com";
                // accountClient.changeEmailFor(account5, newEmail);
                // System.out.println("");

                // // Try to sign in with the new password with new email
                // AccountDTO account6 = accountClient.signin(newEmail, newPassword);
                // System.out.println("");

                // // Delete the account
                // accountClient.delete(account6);
                // System.out.println("");

                // // Try to sign in again to confirm deletion
                // accountClient.signin(newEmail, newPassword);
                // System.out.println("");
        }

        public static void cart() {
                AccountAPIClient accountClient = Clients.accountClient;
                String firstName = "John";
                String lastName = "Doe";
                String phone = "1234567890";
                String email = "john.doe@mail.com";
                String password = "P@ssword123";

                AccountDTO account = new AccountDTO(firstName, lastName, phone, email, password);
                Map<String, String> credentials = Map.of("email", email, "password", password);

                // Create a new account
                accountClient.signup(account);
                System.out.println("");
                // Sign in with the correct password
                accountClient.signin(credentials);
                System.out.println("");

                // Get a CartAPI instance for the signed-in account
                CartAPIClient cartAPIClient = Clients.cartClient;

                // Retrieve the cart for the account, creating the cart if it
                // doesn't exist

                System.out.println("successful getCart test case");
                cartAPIClient.getCart(account);

                BookAPIClient bookAPIClient = Clients.bookClient;

                List<BookDTO> books = new ArrayList<BookDTO>();
                books = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "%Les Misérables%"));

                BookDTO bookToRemoveLater = books.get(0); // Get the first book to remove later

                System.out.println("successful add book to cart test cases");
                books.forEach(book -> {
                        cartAPIClient.addBookToCart(new CartBookRequest(account, book));
                });

                // Retrieve the cart again to see the added book
                System.out.println("Getting cart");
                cartAPIClient.getCart(account);

                // Add a book to the cart
                System.out.println("adding book to cart that will be removed later");
                cartAPIClient.addBookToCart(new CartBookRequest(account, bookToRemoveLater));
                System.out.println("getting cart");
                cartAPIClient.getCart(account);

                // Remove a book from the cart

                System.out.println("successful remove book from cart test case");
                cartAPIClient.removeBookFromCart(new CartBookRequest(account, bookToRemoveLater));

                // Retrieve the cart again to see the added book
                cartAPIClient.getCart(account);

                // clear the cart twice to test the clearCart method error handling
                System.out.println("successful clear cart test case");
                cartAPIClient.clearCart(account);
                System.out.println("error second clear non-existent cart test case");
                cartAPIClient.clearCart(account);

                // These things are not required for the scenario to work, leaving them for now

                // try to delete a book that is not in the cart
                // System.out.println("error remove book not in cart test case");
                // cartAPIClient.removeBookFromCart(new CartBookRequest(account,
                // bookToRemoveLater));

                // // Retrieve the cart again to see that it is empty
                // System.out.println("successful getCart after clear test case");
                // cartAPIClient.getCart(account);

                // Add multiple books to the cart

                // accountClient.delete(account);

        }

        public static void shop() {
                BookAPIClient bookAPIClient = Clients.bookClient;

                BookDTO bookDTO = new BookDTO("My Book", "1234567890", 19.99);
                bookAPIClient.createBook(bookDTO);
                List<BookDTO> books = bookAPIClient.getBooksBy(
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

        public static void order() {
                OrderAPIClient orderAPIClient = Clients.orderClient;
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
                System.out.println("creating order");
                OrderDTO orderDTO = orderAPIClient.createOrder(new OrderRequest(accountDTO, cartDTO));
                System.out.println("order " + orderDTO);
        }

        public static void delivery() {
                DeliveryAPIClient deliveryAPIClient = Clients.deliveryClient;
                OrderAPIClient orderAPIClient = Clients.orderClient;

                System.out.println("Getting order by id");
                OrderDTO order = orderAPIClient.getOrderById("20250623-AAAABBBB");
                System.out.println(order);
                AddressDTO address = new AddressDTO(1, "55 Rue du Faubourg Saint-Honoré", "Paris", "75008");

                // Étape 8 : Le système se charge de la livraison de la commande aux dates de
                // livraison prévues pour chaque livre;
                System.out.println("creating delivery");
                DeliveryDTO delivery = deliveryAPIClient.createDelivery(
                                new CreateDeliveryRequest(
                                                address,
                                                Date.valueOf(LocalDate.now().plusDays(3)),
                                                "In Transit",
                                                order));

                // Affiche les détails de la livraison
                System.out.println("Created Delivery: ");
                System.out.println("Status: " + delivery.getStatus());
                System.out.println("Delivery Date: " + delivery.getDate());
                System.out.println("Address : " + delivery.getAddress());
                System.out.println("Order: " + delivery.getOrder().getOrderNumber());

                // Étape 9 : Voir la liste des commandes en attente de livraison ainsi que
                // l'historique des commandes livrées
                System.out.println("Getting all orders in transit");
                List<DeliveryDTO> pendingDeliveries = deliveryAPIClient.getAllOrdersInTransit();
                System.out.println("Pending Deliveries (" + pendingDeliveries.size() + "):");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        System.out.println("Order: " + pendingDelivery.getOrder().getOrderNumber() +
                                        ", Status: " + pendingDelivery.getStatus());
                }

                List<DeliveryDTO> deliveredDeliveries = deliveryAPIClient.getAllDeliveredOrders();
                System.out.println("Delivered Deliveries (" + deliveredDeliveries.size() + "):");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        System.out.println("Order: " + deliveredDelivery.getOrder().getOrderNumber()
                                        +
                                        ", Status: " + deliveredDelivery.getStatus());
                }

                // Étape 10 : Une fois un livre est livré, sa date de livraison dans la
                // commande est mise à jour et
                // son status passe de "En attente de livraison" à "Livré"
                System.out.println("updating status for " + delivery);
                deliveryAPIClient.updateStatusToDelivered(delivery);
                System.out.println("Updated Status: " + delivery.getStatus());

                pendingDeliveries = deliveryAPIClient.getAllOrdersInTransit();
                System.out.println("Pending Deliveries (" + pendingDeliveries.size() + "):");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        System.out.println("Order: " + pendingDelivery.getOrder().getOrderNumber() +
                                        ", Status: " + pendingDelivery.getStatus());
                }

                deliveredDeliveries = deliveryAPIClient.getAllDeliveredOrders();
                System.out.println("Delivered Deliveries (" + deliveredDeliveries.size() + "):");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        System.out.println("Order: " + deliveredDelivery.getOrder().getOrderNumber()
                                        +
                                        ", Status: " + deliveredDelivery.getStatus());
                }
        }

        // public static void disableLogging() {
        // Logger rootLogger = Logger.getLogger("");
        // Handler[] handlers = rootLogger.getHandlers();
        //
        // for (Handler handler : handlers) {
        // rootLogger.removeHandler(handler); // Retire tous les handlers
        // }
        //
        // rootLogger.setLevel(Level.OFF); // Désactive les logs
        // }

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

        private static void showCartFor(AccountDTO accountDTO) {
                CartAPIClient cartAPIClient = Clients.cartClient;
                List<CartItemDTO> cartItemDTOs = cartAPIClient.getCart(accountDTO).getCartItemDtos();
                Main.scenarioDetailsStep("Contenu du panier (Items : " + cartItemDTOs.size() + ")"
                                + (cartItemDTOs.size() > 0 ? " :" : ""));
                if (cartItemDTOs.size() > 0) {
                        cartItemDTOs.forEach(cartItemDto -> System.out.println(" - " + cartItemDto.book()));
                }
        }

        private static void show(List<BookDTO> bookDtos) {
                bookDtos.forEach(book -> System.out.println(" - " + book.toString()));
        }

        private static void showAllBooks() {
                BookAPIClient bookAPIClient = Clients.bookClient;
                Main.scenarioDetailsStep("Tous les livres de la librairie :");
                List<BookDTO> bookDtos = new ArrayList<BookDTO>();
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.TITLE, "%"));
                show(bookDtos);
        }

        public static void scenario() { // scenario described in the provided specifications

                // signup
                // create an account with first name, last name, phone, email and password
                String firstName = "Alice";
                String lastName = "Smith";
                String phone = "1234567890";
                String email = "alice.smith@mail.com";
                String password = "P@ssword123";

                Main.scenarioStep(
                                "1. L'utilisateur devrait créer un compte et se connecter pour pouvoir utiliser l'application");
                AccountAPIClient accountClient = Clients.accountClient;
                Main.scenarioStep("1.1 Création d'un compte");
                AccountDTO account = new AccountDTO(firstName, lastName, phone, email, password);
                System.out.println(account);
                accountClient.signup(account);

                // Main.scenarioStep("1.2 Tentative de création du même compte");
                // accountClient.signup(account);

                // Main.scenarioStep("1.2 Tentative de création d'un autre compte");
                // firstName = "Paul";
                // lastName = "Smith";
                // phone = "1234567891";
                // email = "paul.smith@mail.com";
                // password = "P@ssword1234";
                // accountClient.signup(account);

                Main.scenarioStep("1.2 Connexion au compte crée");
                Map<String, String> credentials = Map.of("email", account.getEmail(), "password",
                                account.getPassword());
                account = accountClient.signin(credentials);
                Main.scenarioDetailsStep("Connecté avec le compte - " + account);
                /* Should show that signin was a success */

                // get the cart for the account
                // if the cart does not exist, it should be created
                CartAPIClient cartAPIClient = Clients.cartClient;
                showCartFor(account);
                /* Should show that the cart was retrieved successfully */

                Main.scenarioStep("2. Sélectionner un ou plusieurs livres");
                Main.scenarioStep("2.1 Obtenir les catégories existantes");

                // browse books using : author, title, isbn, category (one example for each
                // minimum)
                BookAttributeAPIClient bookAttributeAPI = Clients.bookAttributeClient;
                BookAPIClient bookAPIClient = Clients.bookClient;

                // Authors
                List<AuthorDTO> authors = bookAttributeAPI.getAuthors();
                Main.scenarioDetailsStep("Auteurs disponibles :");
                authors.forEach(author -> System.out.println(author.getName()));

                // Categories
                List<CategoryDTO> categories = bookAttributeAPI.getCategories();
                Main.scenarioDetailsStep("Catégories disponibles :");
                categories.forEach(category -> System.out.println(category.getName()));

                // Editeurs
                List<PublisherDTO> publishers = bookAttributeAPI.getPublishers();
                Main.scenarioDetailsStep("Publishers disponibles :");
                publishers.forEach(publisher -> System.out.println(publisher.getName()));

                // Livres
                Main.scenarioStep("2.2.1 Rechercher tous les livres");
                showAllBooks();

                List<BookDTO> bookDtos = new ArrayList<>();
                Main.scenarioStep("2.2.2 Rechercher les livres à partir d'un titre ('Les Misérables')");
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.TITLE, "Les Misérables"));
                bookDtos.forEach(book -> System.out.println(" - " + book.getTitle()));

                Main.scenarioStep("2.2.3 Rechercher les livres à partir d'un titre partiel ('The')");
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.TITLE, "The"));
                bookDtos.forEach(book -> System.out.println(" - " + book.getTitle()));

                Main.scenarioStep("2.2.4 Rechercher les livres à partir d'un auteur ('Camus')");
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.AUTHOR, "Camus"));
                bookDtos.forEach(
                                book -> System.out.println(" - " +
                                                book.getTitle() + " écrit par " + book.getAuthors().getFirst()));

                Main.scenarioStep("2.2.5 Rechercher les livres à partir d'un editeur ('Gal')");
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.PUBLISHER, "Gal"));
                bookDtos.forEach(book -> System.out.println(book.getTitle() + " edité par " + book.getPublisher()));

                Main.scenarioStep("2.2.6 Rechercher les livres à partir d'un mélange de caractéristiques"
                                + " - titre(partiel) : 'Lover', " + "ISBN(partiel): '06951', " + "Editeur: 'Seuil', "
                                + "Auteur: 'Duras'");
                // Exemple de mélange d'une partie des caractéristiques
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.TITLE, "Lover",
                                BookProperty.ISBN, "06951",
                                BookProperty.PUBLISHER, "Seuil",
                                BookProperty.AUTHOR, "Duras"));
                show(bookDtos);

                Main.scenarioStep("3. Mettre les livres sélectionnés dans le panier");
                showCartFor(account);

                // Livre pas en stock
                // Main.scenarioDetailsStep(
                // "Le livre The Lover n'est pas en stock donc l'ajout au panier n'est pas
                // possible");
                BookDTO book = bookDtos.getFirst();
                // cartAPIClient.addBookToCart(new CartBookRequest(account, book));
                // Main.scenarioDetailsStep("Contenu du panier : " +
                // cartAPIClient.getCart(account).getCartItemDtos());

                // Livre devrait être en stock (first run ie il n'y a pas trop de livre dans le
                // panier et ça ne dépasse pas le stock)
                Main.scenarioDetailsStep(
                                "Le livre Les Misérables est en stock et dois bien s'ajouter au panier (s'il n'y en a pas trop dans le panier)");
                book = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "Les Misérables")).getFirst();
                cartAPIClient.addBookToCart(new CartBookRequest(account, book));
                showCartFor(account);

                Main.scenarioDetailsStep("Ajout une deuxième fois du même livre");
                book = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "Les Misérables")).getFirst();
                cartAPIClient.addBookToCart(new CartBookRequest(account, book));
                showCartFor(account);

                Main.scenarioDetailsStep("Ajout d'un autre livre");
                book = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "Stranger")).getFirst();
                cartAPIClient.addBookToCart(new CartBookRequest(account, book));
                showCartFor(account);

                Main.scenarioStep("4. Supprimer, du panier, un ou des livres qu'on ne désire plus acheter;");
                Main.scenarioStep("4.1 Suppression du dernier livre ajouté ('The Stranger')");
                cartAPIClient.removeBookFromCart(new CartBookRequest(account, book));
                showCartFor(account);

                Main.scenarioStep("4.2 Suppression de l'un des livres ('Les Misérables')");

                CartDTO cart = cartAPIClient.getCart(account);
                cart.getCartItemDtos().forEach(bookDTO -> System.out.println(" - " + bookDTO));
                BookDTO lesMiserables = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "Les Misérables"))
                                .getFirst();
                cartAPIClient.removeBookFromCart(new CartBookRequest(account, lesMiserables));
                showCartFor(account);

                Main.scenarioStep("5. Refaire les étapes 1 à 4 autant de fois qu'on veut;");

                Main.scenarioStep("5.1.1 Tentative de création d'un autre compte");
                firstName = "John";
                lastName = "Doe";
                phone = "1234567892";
                email = "JohnDoe@mail.com";
                password = "P@ssword1234";
                AccountDTO accountDto = new AccountDTO(firstName, lastName, phone, email, password);
                System.out.println(accountDto);
                accountClient.signup(accountDto);

                Main.scenarioStep("5.1.2 Connexion au deuxième compte créé");
                accountDto = accountClient.signin(credentials);
                Main.scenarioDetailsStep("Connecté avec le compte - " + accountDto);

                Main.scenarioStep("5.2 Recherche de livre (titre: 'The')");
                bookDtos = bookAPIClient.getBooksBy(Map.of(
                                BookProperty.TITLE, "The"));
                bookDtos.forEach(bookFor -> Main.scenarioDetailsStep(bookFor.getTitle()));

                Main.scenarioStep("5.3 Mettre les livres sélectionnés dans le panier");
                showCartFor(accountDto);
                Main.scenarioDetailsStep("Ajout d'un livre 'The Stranger'");
                book = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "Stranger")).getFirst();
                cartAPIClient.addBookToCart(new CartBookRequest(accountDto, book));
                showCartFor(accountDto);

                Main.scenarioStep("5.4 Supprimer, du panier, un ou des livres qu'on ne désire plus acheter;");
                Main.scenarioDetailsStep("Suppression du dernier livre ajouté ('The Stranger')");
                cartAPIClient.removeBookFromCart(new CartBookRequest(accountDto, book));
                showCartFor(accountDto);

                Main.scenarioDetailsStep("5.3 Ajout d'un deuxième livre 'The Stranger'");
                book = bookAPIClient.getBooksBy(Map.of(BookProperty.TITLE, "Stranger")).getFirst();
                cartAPIClient.addBookToCart(new CartBookRequest(accountDto, book));
                showCartFor(accountDto);

                Main.scenarioStep("À partir du panier, passer au paiement et régler la facture");
                Main.scenarioStep("les livres du panier constituent alors une nouvelle commande");
                Main.scenarioStep("où chaque livre de la commande a un status \\\"En attente de livraison\\\"");
                showCartFor(accountDto);
                showAllBooks();

                Main.scenarioStep("6.1 Simulation de paiement");
                CheckoutAPIClient checkoutApiClient = Clients.checkoutClient;
                AddressDTO address = new AddressDTO(accountDto.getFirstName(), accountDto.getLastName(), accountDto.getPhone(),"80 Rue du Faubourg Saint-Honoré", "Paris", "75008");
                Main.scenarioDetailsStep("Admettons les informations suivantes :");
                Main.scenarioDetailsStep(" - On souhaite que la commande soit livrée à " + address);
                Main.scenarioDetailsStep(" - On souhaite effectuer le paiement pour le compte " + account);
                Main.scenarioDetailsStep(" - Enfin, on souhaite (simuler) un paiement par carte de débit/crédit");
                CheckoutRequest checkoutRequest = new CheckoutRequest(accountDto, PaymentMethod.CARD, address);
                Main.scenarioStep("On envoie la requête de paiement suivante : ");
                System.out.println(checkoutRequest.toString());

                InvoiceDTO resultingInvoice = checkoutApiClient.checkout(checkoutRequest);
                Main.scenarioDetailsStep("La requête a été acceptée, voici la facture créée : ");
                System.out.println(resultingInvoice);
                Main.scenarioStep(
                                "8 : Le système procède automatiquement à l'expédition de la commande le lendemain du payment.");
                Main.scenarioDetailsStep(
                                "En attendant, on peut tout de même consulter l'état de livraison des commandes du compte.");

                // OrderAPIClient orderAPI = Clients.orderClient;
                DeliveryAPIClient deliveryAPIClient = Clients.deliveryClient;

                List<DeliveryDTO> stati = deliveryAPIClient.getOrderStatiFor(accountDto);

                System.out.println(stati);

                Main.scenarioDetailsStep(
                                "On remarque notamment l'état de livraison de la commande à "
                                                + stati.getFirst().getStatus()
                                                + ". (Normalement, il est à \"En attente de livraison\"");
                Main.scenarioDetailsStep("On peut aussi consulter l'état de toutes les commandes, notamment :");
                Main.scenarioStep(
                                "Voir la liste des commandes en transit (en cours de livraison) :");
                List<DeliveryDTO> pendingDeliveries = deliveryAPIClient.getAllOrdersInTransit();
                Main.scenarioDetailsStep("Pending Deliveries (" + pendingDeliveries.size() + ") :");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        Main.scenarioDetailsStep("Order: " + pendingDelivery.getOrder().getOrderNumber() + ", Status: "
                                        + pendingDelivery.getStatus());
                }
                Main.scenarioStep(
                                "Voir l'historique des commandes livrées : ");
                List<DeliveryDTO> deliveredDeliveries = deliveryAPIClient.getAllDeliveredOrders();
                Main.scenarioDetailsStep("Delivered Deliveries (" +
                                deliveredDeliveries.size() + ") :");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        Main.scenarioDetailsStep("Order: " +
                                        deliveredDelivery.getOrder().getOrderNumber() +
                                        ", Status: " + deliveredDelivery.getStatus());
                }

                Main.scenarioStep("On simule le passage d'une journée");
                deliveryAPIClient.pass(new Time(60 * 60 * 24)); // une journée en secondes = 60 sec/min * 60 min/h *
                                                                // 24h/j

                Main.scenarioStep("9 : Après une journée, on revérifie l'état de livraison de la commande");

                stati = deliveryAPIClient.getOrderStatiFor(accountDto); // stati étant le pluriel de status
                System.out.println(stati);

                Main.scenarioDetailsStep(
                                "On remarque désormais l'état de livraison de la commande à "
                                                + stati.getFirst().getStatus()
                                                + ". (Normalement, il est à \"En transit\"");

                Main.scenarioDetailsStep(
                                "La commande et les livres associés étant en transit, ils ne se trouvent plus en stock.");
                Main.scenarioDetailsStep("Ainsi, on peut constater que : ");
                Main.scenarioDetailsStep(" - le panier est vidé : ");
                showCartFor(accountDto);
                Main.scenarioDetailsStep(" - le stock des livres associés à la commande a diminué : ");
                showAllBooks();

                Main.scenarioDetailsStep(
                                "On peut aussi consulter de nouveau l'état de toutes les commandes, notamment :");
                Main.scenarioStep(
                                "Voir que la liste des commandes en transit (en cours de livraison) a ... :");
                // TODO : voir ce qu'il se passe
                pendingDeliveries = deliveryAPIClient.getAllOrdersInTransit();
                Main.scenarioDetailsStep("Pending Deliveries (" + pendingDeliveries.size() + ") :");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        Main.scenarioDetailsStep("Order: " + pendingDelivery.getOrder().getOrderNumber() + ", Status: "
                                        + pendingDelivery.getStatus());
                }
                Main.scenarioStep(
                                "Voir l'historique des commandes livrées (n'a pas encore changé) : ");
                // TODO : vérifier
                deliveredDeliveries = deliveryAPIClient.getAllDeliveredOrders();
                Main.scenarioDetailsStep("Delivered Deliveries (" +
                                deliveredDeliveries.size() + ") :");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        Main.scenarioDetailsStep("Order: " +
                                        deliveredDelivery.getOrder().getOrderNumber() +
                                        ", Status: " + deliveredDelivery.getStatus());
                }

                Main.scenarioStep("On simule cette fois-ci le passage de trois journées");
                deliveryAPIClient.pass(new Time(60 * 60 * 24 * 3));

                Main.scenarioStep("Après trois jours, on revérifie l'état de livraison de la commande");

                stati = deliveryAPIClient.getOrderStatiFor(accountDto); // stati étant le pluriel de status
                System.out.println(stati);

                Main.scenarioDetailsStep(
                                "On remarque désormais l'état de livraison de la commande à "
                                                + stati.getFirst().getStatus()
                                                + ". (Normalement, il est à \"Livrée\"");

                Main.scenarioDetailsStep(
                                "On consulte une dernière fois l'état de toutes les commandes, notamment :");
                Main.scenarioStep(
                                "Voir que la liste des commandes en transit (en cours de livraison) a ... :");
                // TODO : voir ce qu'il se passe
                pendingDeliveries = deliveryAPIClient.getAllOrdersInTransit();
                Main.scenarioDetailsStep("Pending Deliveries (" + pendingDeliveries.size() + ") :");
                for (DeliveryDTO pendingDelivery : pendingDeliveries) {
                        Main.scenarioDetailsStep("Order: " + pendingDelivery.getOrder().getOrderNumber() + ", Status: "
                                        + pendingDelivery.getStatus());
                }
                Main.scenarioStep(
                                "Et surtout constater que l'historique des commandes livrées compte désormais la commande qui a été passée : ");
                // TODO : vérifier
                deliveredDeliveries = deliveryAPIClient.getAllDeliveredOrders();
                Main.scenarioDetailsStep("Delivered Deliveries (" +
                                deliveredDeliveries.size() + ") :");
                for (DeliveryDTO deliveredDelivery : deliveredDeliveries) {
                        Main.scenarioDetailsStep("Order: " +
                                        deliveredDelivery.getOrder().getOrderNumber() +
                                        ", Status: " + deliveredDelivery.getStatus());
                }
        }

        public static void main(String[] args) {
                // GlobalSafeExecutor.run(() -> {
                // Main.account();
                // Main.cart();
                // Main.shop();
                // Main.order();
                // Main.delivery();
                Main.scenario();
                // });
        }
}
