-- =========================================
-- SkillGap Analyzer Database
-- =========================================

-- Create database
CREATE DATABASE IF NOT EXISTS skillgap_db;

-- Select database
USE skillgap_db;


-- =========================================
-- 1. Roles Table
-- =========================================

CREATE TABLE IF NOT EXISTS roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(100) NOT NULL UNIQUE
);


-- =========================================
-- 2. Skills Table
-- =========================================

CREATE TABLE IF NOT EXISTS skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(100) NOT NULL UNIQUE
);


-- =========================================
-- 3. Role-Skills Relationship Table
-- =========================================

CREATE TABLE IF NOT EXISTS role_skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT NOT NULL,
    skill_id INT NOT NULL,
    importance INT NOT NULL,

    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (skill_id) REFERENCES skills(id)
);


-- =========================================
-- Insert Roles
-- =========================================

INSERT INTO roles (role_name) VALUES
('Java Developer'),
('Java Full Stack Developer'),
('Frontend Developer');


-- =========================================
-- Insert Skills
-- =========================================

INSERT INTO skills (skill_name) VALUES
('Java'),
('SQL'),
('HTML'),
('CSS'),
('JavaScript'),
('Spring Boot'),
('React'),
('Git'),
('DSA'),
('REST API'),
('MySQL'),
('OOP'),
('JDBC'),
('Bootstrap'),
('TypeScript');


-- =========================================
-- Connect Skills to Roles
-- =========================================

INSERT INTO role_skills (role_id, skill_id, importance) VALUES

-- Java Developer
(1, 1, 5),
(1, 2, 4),
(1, 8, 3),
(1, 9, 5),
(1, 12, 5),
(1, 13, 4),
(1, 6, 4),
(1, 10, 4),

-- Java Full Stack Developer
(2, 1, 5),
(2, 2, 5),
(2, 3, 4),
(2, 4, 4),
(2, 5, 5),
(2, 6, 5),
(2, 7, 4),
(2, 8, 3),
(2, 9, 5),
(2, 10, 5),
(2, 11, 4),
(2, 12, 5),
(2, 13, 4),

-- Frontend Developer
(3, 3, 5),
(3, 4, 5),
(3, 5, 5),
(3, 7, 4),
(3, 8, 3),
(3, 14, 3),
(3, 15, 3);