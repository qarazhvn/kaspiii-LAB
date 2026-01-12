-- Инициализация тестовых данных для таблицы products
INSERT INTO products (name, price, address) VALUES ('Laptop', 999.99, '123 Main St, Almaty');
INSERT INTO products (name, price, address) VALUES ('Mouse', 25.50, '456 Oak Ave, Astana');
INSERT INTO products (name, price, address) VALUES ('Keyboard', 75.00, '789 Pine Rd, Karaganda');
INSERT INTO products (name, price, address) VALUES ('Monitor', 350.00, '321 Elm St, Shymkent');
INSERT INTO products (name, price, address) VALUES ('Headphones', 120.00, '654 Maple Dr, Pavlodar');

-- Инициализация тестовых данных для таблицы deliveries
INSERT INTO deliveries (product_id, address, status, created_at) VALUES (1, '123 Main St, Almaty', 'PENDING', CURRENT_TIMESTAMP);
INSERT INTO deliveries (product_id, address, status, created_at) VALUES (2, '456 Oak Ave, Astana', 'IN_PROGRESS', CURRENT_TIMESTAMP);
