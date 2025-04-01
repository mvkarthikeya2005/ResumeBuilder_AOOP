-- Resume Builder Database Setup Script

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS resume;

-- Use the database
USE resume;

-- Create the signup table (for user accounts)
CREATE TABLE IF NOT EXISTS signup (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    phone BIGINT NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert some sample users (password is 'password123' for all)
INSERT INTO signup (fullname, username, email, phone, password) VALUES
('John Doe', 'johndoe', 'john@example.com', 1234567890, 'password123'),
('Jane Smith', 'janesmith', 'jane@example.com', 9876543210, 'password123'),
('Bob Johnson', 'bjohnson', 'bob@example.com', 5551234567, 'password123'),
('Alice Williams', 'awilliams', 'alice@example.com', 7778889999, 'password123'),
('Charlie Brown', 'cbrown', 'charlie@example.com', 1112223333, 'password123');

-- Create a table to store resumes (if needed for future development)
CREATE TABLE IF NOT EXISTS resumes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES signup(id) ON DELETE CASCADE
);

-- Note: To run this script, you can use:
-- mysql -u root -p < database_setup.sql
-- Or run it from your MySQL client
