DROP DATABASE IF EXISTS lel;
CREATE DATABASE lel DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lel;

-- Table: Account
CREATE TABLE Account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

-- Table: Address
CREATE TABLE Address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    street VARCHAR(100),
    city VARCHAR(50),
    postal_code VARCHAR(10),
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Table: Publisher
CREATE TABLE Publisher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

-- Table: Author
CREATE TABLE Author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

-- Table: Category
CREATE TABLE Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

-- Table: Book
CREATE TABLE Book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    description TEXT,
    isbn VARCHAR(20) UNIQUE,
    publication_date DATE,
    price DECIMAL(10,2),
    stock_quantity INT,
    publisher_id INT,
    FOREIGN KEY (publisher_id) REFERENCES Publisher(id)
);

-- Many-to-many: Book <-> Author
CREATE TABLE Book_Author (
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

-- Many-to-many: Book <-> Category
CREATE TABLE Book_Category (
    book_id INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES Book(id),
    FOREIGN KEY (category_id) REFERENCES Category(id)
);

-- Table: Cart
CREATE TABLE Cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT UNIQUE,
    total_price DECIMAL(10,2),
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Table: Cart_Book
CREATE TABLE Cart_Book (
    cart_id INT,
    book_id INT,
    quantity INT,
    PRIMARY KEY (cart_id, book_id),
    FOREIGN KEY (cart_id) REFERENCES Cart(id),
    FOREIGN KEY (book_id) REFERENCES Book(id)
);

-- Table: Order
CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    order_date DATE,
    total_price DECIMAL(10,2),
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- Table: Order_Book
CREATE TABLE Order_Book (
    order_id INT,
    book_id INT,
    quantity INT,
    book_status ENUM('Pending delivery', 'Delivered') DEFAULT 'Pending delivery',
    delivery_date DATE,
    PRIMARY KEY (order_id, book_id),
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (book_id) REFERENCES Book(id)
);

-- Table: Invoice
CREATE TABLE Invoice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT UNIQUE,
    invoice_date DATE,
    total_price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES Orders(id)
);

-- Table: Delivery
CREATE TABLE Delivery (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT UNIQUE,
    address_id INT,
    delivery_date DATE,
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (address_id) REFERENCES Address(id)
);

-- Initial accounts
INSERT INTO Account (first_name, last_name, phone, email, password) VALUES
('Alice', 'Durand', '5141234567', 'alice@example.com', 'hashed_password_1'),
('Bob', 'Martin', '5149876543', 'bob@example.com', 'hashed_password_2');

-- Addresses
INSERT INTO Address (account_id, first_name, last_name, phone, street, city, postal_code) VALUES
(1, 'Alice', 'Durand', '5141234567', '123 Lilas Street', 'Montreal', 'H3Z1X1'),
(2, 'Bob', 'Martin', '5149876543', '456 Pine Avenue', 'Quebec', 'G1V2T3');

-- Publishers
INSERT INTO Publisher (name) VALUES
('Gallimard'),
('Seuil');

-- Authors
INSERT INTO Author (name) VALUES
('Victor Hugo'),
('Albert Camus'),
('Marguerite Duras');

-- Categories
INSERT INTO Category (name) VALUES
('Novel'),
('Philosophy'),
('Classic');

-- Books
INSERT INTO Book (title, description, isbn, publication_date, price, stock_quantity, publisher_id) VALUES
('Les Misérables', 'A novel by Victor Hugo', '9782070409188', '1862-01-01', 19.99, 10, 1),
('The Stranger', 'A novel by Albert Camus', '9782070360021', '1942-05-01', 14.99, 5, 2),
('The Lover', 'A novel by Marguerite Duras', '9782707306951', '1984-09-01', 12.50, 0, 2); -- Out of stock

-- Book <-> Author
INSERT INTO Book_Author (book_id, author_id) VALUES
(1, 1), -- Les Misérables / Victor Hugo
(2, 2), -- The Stranger / Albert Camus
(3, 3); -- The Lover / Marguerite Duras

-- Book <-> Category
INSERT INTO Book_Category (book_id, category_id) VALUES
(1, 1), (1, 3),      -- Les Misérables: Novel, Classic
(2, 1), (2, 2),      -- The Stranger: Novel, Philosophy
(3, 1);              -- The Lover: Novel

-- Carts
INSERT INTO Cart (account_id, total_price) VALUES
(1, 34.98), -- Alice
(2, 14.99); -- Bob

-- Cart_Book
INSERT INTO Cart_Book (cart_id, book_id, quantity) VALUES
(1, 1, 1),  -- Alice: 1 x Les Misérables
(1, 2, 1),  -- Alice: 1 x The Stranger
(2, 2, 1);  -- Bob: 1 x The Stranger

-- Orders
INSERT INTO Orders (account_id, order_date, total_price) VALUES
(1, '2025-06-10', 34.98),
(2, '2025-06-09', 14.99);

-- Order_Book
INSERT INTO Order_Book (order_id, book_id, quantity, book_status, delivery_date) VALUES
(1, 1, 1, 'Delivered', '2025-06-12'),
(1, 2, 1, 'Pending delivery', NULL),
(2, 2, 1, 'Delivered', '2025-06-11');

-- Invoices
INSERT INTO Invoice (order_id, invoice_date, total_price) VALUES
(1, '2025-06-10', 34.98),
(2, '2025-06-09', 14.99);

-- Deliveries
INSERT INTO Delivery (order_id, address_id, delivery_date) VALUES
(1, 1, '2025-06-12'),
(2, 2, '2025-06-11');
