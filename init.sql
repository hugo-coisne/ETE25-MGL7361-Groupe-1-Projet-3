DROP DATABASE IF EXISTS lel;
CREATE DATABASE lel DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lel;

-- Table: Account
CREATE TABLE accounts
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(20),
    email      VARCHAR(100) UNIQUE,
    password   VARCHAR(255)
);

-- Table: Address
CREATE TABLE addresses
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    account_id  INT,
    first_name  VARCHAR(50),
    last_name   VARCHAR(50),
    phone       VARCHAR(20),
    street      VARCHAR(100),
    city        VARCHAR(50),
    postal_code VARCHAR(10),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

-- Table: Publisher
CREATE TABLE publishers
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

-- Table: Author
CREATE TABLE authors
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

-- Table: Category
CREATE TABLE categories
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

-- Table: Book
CREATE TABLE books
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(200),
    description      TEXT,
    isbn             VARCHAR(20) UNIQUE,
    publication_date DATE,
    price            DECIMAL(10, 2),
    stock_quantity   INT,
    publisher_id     INT,
    FOREIGN KEY (publisher_id) REFERENCES publishers (id)
);

-- Many-to-many: Book <-> Author
CREATE TABLE book_author
(
    book_id   INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

-- Many-to-many: Book <-> Category
CREATE TABLE book_category
(
    book_id     INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

-- Table: Cart
CREATE TABLE carts
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    account_id  INT UNIQUE,
    total_price DECIMAL(10, 2),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

-- Table: Cart_Book
CREATE TABLE cart_book
(
    cart_id  INT,
    book_id  INT,
    quantity INT,
    PRIMARY KEY (cart_id, book_id),
    FOREIGN KEY (cart_id) REFERENCES carts (id),
    FOREIGN KEY (book_id) REFERENCES books (id)
);

-- Table: Order
CREATE TABLE orders
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100) UNIQUE,
    account_id   INT,
    order_date   DATE,
    total_price  DECIMAL(10, 2),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

-- Table: Order_Book
CREATE TABLE order_contents
(
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    order_number          VARCHAR(100),
    book_isbn             VARCHAR(20),
    book_title            VARCHAR(200),
    book_description      TEXT,
    book_price            DECIMAL(10, 2),
    book_publisher        VARCHAR(100),
    book_publication_date DATE,
    book_authors          TEXT,
    quantity              INT,
    FOREIGN KEY (order_number) REFERENCES orders (order_number)
);

-- Table: Invoice
CREATE TABLE invoices
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100) UNIQUE,
    invoice_number VARCHAR(100) UNIQUE,
    invoice_date DATE,
    total_price  DECIMAL(10, 2),
    payment_method ENUM('CARD', 'PAYPAL'),
    FOREIGN KEY (order_number) REFERENCES orders (order_number)
);

-- Table: Delivery
CREATE TABLE deliveries
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    order_id      INT UNIQUE,
    address_id    INT,
    delivery_date DATE,
    status        VARCHAR(50),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id)
);

-- Initial accounts
INSERT INTO accounts (first_name, last_name, phone, email, password)
VALUES ('Alice', 'Durand', '5141234567', 'alice@example.com',
        'hashed_password_1'),
       ('Bob', 'Martin', '5149876543', 'bob@example.com', 'hashed_password_2');

-- Addresses
INSERT INTO addresses (account_id, first_name, last_name, phone, street, city,
                       postal_code)
VALUES (1, 'Alice', 'Durand', '5141234567', '123 Lilas Street', 'Montreal',
        'H3Z1X1'),
       (2, 'Bob', 'Martin', '5149876543', '456 Pine Avenue', 'Quebec',
        'G1V2T3');

-- Publishers
INSERT INTO publishers (name)
VALUES ('Gallimard'),
       ('Seuil');

-- Authors
INSERT INTO authors (name)
VALUES ('Victor Hugo'),
       ('Albert Camus'),
       ('Marguerite Duras');

-- Categories
INSERT INTO categories (name)
VALUES ('Novel'),
       ('Philosophy'),
       ('Classic');

-- Books
INSERT INTO books (title, description, isbn, publication_date, price,
                   stock_quantity, publisher_id)
VALUES ('Les Misérables', 'A novel by Victor Hugo', '9782070409188',
        '1862-01-01', 19.99, 10, 1),
       ('The Stranger', 'A novel by Albert Camus', '9782070360021',
        '1942-05-01', 14.99, 5, 2),
       ('The Lover', 'A novel by Marguerite Duras', '9782707306951',
        '1984-09-01', 12.50, 0, 2);
-- Out of stock

-- Book <-> Author
INSERT INTO book_author (book_id, author_id)
VALUES (1, 1), -- Les Misérables / Victor Hugo
       (2, 2), -- The Stranger / Albert Camus
       (3, 3);
-- The Lover / Marguerite Duras

-- Book <-> Category
INSERT INTO book_category (book_id, category_id)
VALUES (1, 1),
       (1, 3), -- Les Misérables: Novel, Classic
       (2, 1),
       (2, 2), -- The Stranger: Novel, Philosophy
       (3, 1);
-- The Lover: Novel

-- Carts
INSERT INTO carts (account_id, total_price)
VALUES (1, 34.98), -- Alice
       (2, 14.99);
-- Bob

-- Cart_Book
INSERT INTO cart_book (cart_id, book_id, quantity)
VALUES (1, 1, 1), -- Alice: 1 x Les Misérables
       (1, 2, 1), -- Alice: 1 x The Stranger
       (2, 2, 1);
-- Bob: 1 x The Stranger

-- Orders
INSERT INTO orders (account_id, order_date, total_price, order_number)
VALUES (1, '2025-06-10', 34.98, '20250623-AAAABBBB'),
       (2, '2025-06-09', 14.99, '20250623-AABBAABB');

-- Order_Book
INSERT INTO order_contents (order_number,
                            book_isbn,
                            book_title,
                            book_description,
                            book_price,
                            book_publisher,
                            book_publication_date,
                            book_authors,
                            quantity)
VALUES ('20250623-AAAABBBB',
        '9782070409188',
        'Les Misérables',
        'A novel by Victor Hugo',
        19.99,
        'Gallimard',
        '1862-01-01',
        'Victor Hugo',
        2),

       ('20250623-AABBAABB',
        '9782070360021',
        'The Stranger',
        'A novel by Albert Camus',
        14.99,
        'Seuil',
        '1942-05-01',
        'Albert Camus', 1);


-- Invoices
INSERT INTO invoices (invoice_number, order_number, invoice_date, total_price, payment_method)
VALUES ('INV-20250623-AAAABBBB-001', '20250623-AAAABBBB', '20250623', 19.99, 'CARD'),
       ('INV-20250623-AABBAABB-001', '20250623-AABBAABB', '20250623', 14.99, 'PAYPAL');
-- Deliveries
INSERT INTO deliveries (order_id, address_id, delivery_date, status)
VALUES (1, 1, '2025-06-12', 'In Transit'),
       (2, 2, '2025-06-11', 'Delivered');
