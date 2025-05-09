-- users table
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL PRIMARY KEY,
    password_hash CHAR(64) NOT NULL,
    bio TEXT,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- orders table
CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount NUMERIC(18,2) NOT NULL,
    shipping_address TEXT NOT NULL,
    billing_address TEXT NOT NULL,
    status VARCHAR(50),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
-- products table
CREATE TABLE IF NOT EXISTS products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(18,2) NOT NULL,
    category VARCHAR(255)
);

-- order_details table
CREATE TABLE IF NOT EXISTS order_details (
    order_detail_id SERIAL PRIMARY KEY,
    total_price DECIMAL(10, 2),
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(18,2) NOT NULL,
    discount NUMERIC(5,2) DEFAULT 0,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- activity_log table
CREATE TABLE IF NOT EXISTS activity_log (
    log_id SERIAL PRIMARY KEY,
    user_id INT,
    action TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ==== INSERT DATA IF NOT EXISTS ====

-- users
INSERT INTO users (username, email, password_hash, bio)
SELECT 'user1', 'user1@example.com', '1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef', 'This is a test user.'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'user1@example.com');

INSERT INTO users (username, email, password_hash, bio)
SELECT 'user2', 'user2@example.com', 'abcdef12345678901234567890abcdef1234567890abcdef1234567890abcdef', 'Another test user.'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'user2@example.com');

-- products
INSERT INTO products (name, description, price, category)
SELECT 'Product A', 'A very bad description that repeats.', 19.99, 'Electronics'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Product A');

INSERT INTO products (name, description, price, category)
SELECT 'Product B', 'Another poorly optimized product.', 49.99, 'Appliances'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Product B');

-- orders
INSERT INTO orders (user_id, total_amount, shipping_address, billing_address, status)
SELECT user_id, 59.99, '123 Fake St', '123 Fake St', 'Pending'
FROM users WHERE username = 'user1'
  AND NOT EXISTS (
      SELECT 1 FROM orders WHERE user_id = users.user_id AND total_amount = 59.99
  );

INSERT INTO orders (user_id, total_amount, shipping_address, billing_address, status)
SELECT user_id, 89.99, '456 Fake Ave', '456 Fake Ave', 'Shipped'
FROM users WHERE username = 'user2'
  AND NOT EXISTS (
      SELECT 1 FROM orders WHERE user_id = users.user_id AND total_amount = 89.99
  );

-- order_details
INSERT INTO order_details (order_id, product_id, quantity, unit_price, discount)
SELECT o.order_id, p.product_id, 1, p.price, 0
FROM orders o
JOIN users u ON o.user_id = u.user_id
JOIN products p ON p.name = 'Product A'
WHERE u.username = 'user1'
  AND NOT EXISTS (
      SELECT 1 FROM order_details WHERE order_id = o.order_id AND product_id = p.product_id
  );

INSERT INTO order_details (order_id, product_id, quantity, unit_price, discount)
SELECT o.order_id, p.product_id, 2, p.price, 5
FROM orders o
JOIN users u ON o.user_id = u.user_id
JOIN products p ON p.name = 'Product B'
WHERE u.username = 'user2'
  AND NOT EXISTS (
      SELECT 1 FROM order_details WHERE order_id = o.order_id AND product_id = p.product_id
  );

-- activity_log
INSERT INTO activity_log (user_id, action, details)
SELECT user_id, 'Login', 'User logged in successfully'
FROM users WHERE username = 'user1'
  AND NOT EXISTS (
      SELECT 1 FROM activity_log WHERE user_id = users.user_id AND action = 'Login'
  );

INSERT INTO activity_log (user_id, action, details)
SELECT user_id, 'Placed Order', 'User placed an order for 1 item'
FROM users WHERE username = 'user1'
  AND NOT EXISTS (
      SELECT 1 FROM activity_log WHERE user_id = users.user_id AND action = 'Placed Order'
  );

INSERT INTO activity_log (user_id, action, details)
SELECT user_id, 'Login', 'User logged in successfully'
FROM users WHERE username = 'user2'
  AND NOT EXISTS (
      SELECT 1 FROM activity_log WHERE user_id = users.user_id AND action = 'Login'
  );

INSERT INTO activity_log (user_id, action, details)
SELECT user_id, 'Placed Order', 'User placed an order for 2 items'
FROM users WHERE username = 'user2'
  AND NOT EXISTS (
      SELECT 1 FROM activity_log WHERE user_id = users.user_id AND action = 'Placed Order'
  );
SELECT * FROM users WHERE email LIKE '%example.com%' LIMIT 0 OFFSET 2;
SELECT SUM(total_amount) FROM orders WHERE order_date >= '2024-01-01';
--localhost:8080/users/search?emailPart=user&page=0&size=10
--localhost:8080/orders/income?from=2024-01-01
--localhost:8080/orders/by-email?email=user1@example.com
--localhost:8080/users/findByEmail?email=user1@example.com