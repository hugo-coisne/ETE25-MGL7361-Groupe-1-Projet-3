-- ========================================
-- DROP existing databases
-- ========================================
DROP DATABASE IF EXISTS user_db;

DROP DATABASE IF EXISTS shop_db;

DROP DATABASE IF EXISTS order_db;

DROP DATABASE IF EXISTS checkout_db;

DROP DATABASE IF EXISTS delivery_db;

-- ========================================
-- USER SERVICE
-- ========================================
CREATE DATABASE user_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE user_db;

CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

INSERT INTO
    accounts (
        first_name,
        last_name,
        phone,
        email,
        password
    )
VALUES (
        'Alice',
        'Durand',
        '5141234567',
        'alice@example.com',
        'hashed_password_1'
    ),
    (
        'Bob',
        'Martin',
        '5149876543',
        'bob@example.com',
        'hashed_password_2'
    );

-- ========================================
-- SHOP SERVICE
-- ========================================
CREATE DATABASE shop_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shop_db;

CREATE TABLE publishers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

INSERT INTO publishers (name) VALUES ('Gallimard'), ('Seuil');

CREATE TABLE authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

INSERT INTO
    authors (name)
VALUES ('Victor Hugo'),
    ('Albert Camus'),
    ('Marguerite Duras');

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

INSERT INTO
    categories (name)
VALUES ('Novel'),
    ('Philosophy'),
    ('Classic');

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    description TEXT,
    isbn VARCHAR(20) UNIQUE,
    publication_date DATE,
    price DECIMAL(10, 2),
    stock_quantity INT,
    publisher_id INT
);

INSERT INTO
    books (
        title,
        description,
        isbn,
        publication_date,
        price,
        stock_quantity,
        publisher_id
    )
VALUES (
        'Les Misérables',
        'A novel by Victor Hugo',
        '9782070409188',
        '1862-01-01',
        19.99,
        10,
        1
    ),
    (
        'The Stranger',
        'A novel by Albert Camus',
        '9782070360021',
        '1942-05-01',
        14.99,
        5,
        2
    ),
    (
        'The Lover',
        'A novel by Marguerite Duras',
        '9782707306951',
        '1984-09-01',
        12.50,
        0,
        2
    );

CREATE TABLE book_author (
    book_id INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id)
);

INSERT INTO
    book_author (book_id, author_id)
VALUES (1, 1),
    (2, 2),
    (3, 3);

CREATE TABLE book_category (
    book_id INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id)
);

INSERT INTO
    book_category (book_id, category_id)
VALUES (1, 1),
    (1, 3),
    (2, 1),
    (2, 2),
    (3, 1);

CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT UNIQUE,
    total_price DECIMAL(10, 2)
);

INSERT INTO
    carts (account_id, total_price)
VALUES (1, 34.98),
    (2, 14.99);

CREATE TABLE cart_book (
    cart_id INT,
    book_id INT,
    quantity INT,
    PRIMARY KEY (cart_id, book_id)
);

INSERT INTO
    cart_book (cart_id, book_id, quantity)
VALUES (1, 1, 1),
    (1, 2, 1),
    (2, 2, 1);

-- ========================================
-- ORDER SERVICE
-- ========================================
CREATE DATABASE order_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE order_db;

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100) UNIQUE,
    account_id INT,
    order_date DATE,
    total_price DECIMAL(10, 2)
);

INSERT INTO
    orders (
        account_id,
        order_date,
        total_price,
        order_number
    )
VALUES (
        1,
        '2025-06-10',
        34.98,
        '20250623-AAAABBBB'
    ),
    (
        2,
        '2025-06-09',
        14.99,
        '20250623-AABBAABB'
    );

CREATE TABLE order_contents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100),
    book_isbn VARCHAR(20),
    book_title VARCHAR(200),
    book_description TEXT,
    book_price DECIMAL(10, 2),
    book_publisher VARCHAR(100),
    book_publication_date DATE,
    book_authors TEXT,
    quantity INT
);

INSERT INTO
    order_contents (
        order_number,
        book_isbn,
        book_title,
        book_description,
        book_price,
        book_publisher,
        book_publication_date,
        book_authors,
        quantity
    )
VALUES (
        '20250623-AAAABBBB',
        '9782070409188',
        'Les Misérables',
        'A novel by Victor Hugo',
        19.99,
        'Gallimard',
        '1862-01-01',
        'Victor Hugo',
        2
    ),
    (
        '20250623-AABBAABB',
        '9782070360021',
        'The Stranger',
        'A novel by Albert Camus',
        14.99,
        'Seuil',
        '1942-05-01',
        'Albert Camus',
        1
    );

-- ========================================
-- CHECKOUT SERVICE
-- ========================================
CREATE DATABASE checkout_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE checkout_db;

CREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100) UNIQUE,
    invoice_number VARCHAR(100) UNIQUE,
    invoice_date DATE,
    total_price DECIMAL(10, 2),
    checkout_method ENUM('CARD', 'PAYPAL')
);

INSERT INTO
    invoices (
        invoice_number,
        order_number,
        invoice_date,
        total_price,
        checkout_method
    )
VALUES (
        'INV-20250623-AAAABBBB-001',
        '20250623-AAAABBBB',
        '2025-06-23',
        19.99,
        'CARD'
    ),
    (
        'INV-20250623-AABBAABB-001',
        '20250623-AABBAABB',
        '2025-06-23',
        14.99,
        'PAYPAL'
    );

-- ========================================
-- DELIVERY SERVICE
-- ========================================
CREATE DATABASE delivery_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE delivery_db;

CREATE TABLE addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    street VARCHAR(100),
    city VARCHAR(50),
    postal_code VARCHAR(10)
);

INSERT INTO
    addresses (
        account_id,
        first_name,
        last_name,
        phone,
        street,
        city,
        postal_code
    )
VALUES (
        1,
        'Alice',
        'Durand',
        '5141234567',
        '123 Lilas Street',
        'Montreal',
        'H3Z1X1'
    ),
    (
        2,
        'Bob',
        'Martin',
        '5149876543',
        '456 Pine Avenue',
        'Quebec',
        'G1V2T3'
    );

CREATE TABLE deliveries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT UNIQUE,
    address_id INT,
    delivery_date DATE,
    status VARCHAR(50)
);

-- ========================================
-- FINALIZE
-- ========================================
CREATE USER 'user_svc' @'%' IDENTIFIED BY 'user_svc_pwd';

GRANT ALL PRIVILEGES ON user_db.* TO 'user_svc' @'%';

CREATE USER 'shop_svc' @'%' IDENTIFIED BY 'shop_svc_pwd';

GRANT ALL PRIVILEGES ON shop_db.* TO 'shop_svc' @'%';

CREATE USER 'order_svc' @'%' IDENTIFIED BY 'order_svc_pwd';

GRANT ALL PRIVILEGES ON order_db.* TO 'order_svc' @'%';

CREATE USER 'checkout_svc' @'%' IDENTIFIED BY 'checkout_svc_pwd';

GRANT ALL PRIVILEGES ON checkout_db.* TO 'checkout_svc' @'%';

CREATE USER 'delivery_svc' @'%' IDENTIFIED BY 'delivery_svc_pwd';

GRANT ALL PRIVILEGES ON delivery_db.* TO 'delivery_svc' @'%';

FLUSH PRIVILEGES;