# SkillGap Analyzer

A role-based skill gap analysis web application that helps students identify the skills they need for a selected software job role.

## Problem Statement

Students often learn different technical skills but may not know which skills are required for a particular job role.

SkillGap Analyzer compares a user's current skills with the skills required for a selected role and identifies the missing skills.

## Features

- Select a target job role
- Select currently known skills
- Fetch role-specific required skills from MySQL
- Compare current skills with required skills
- Calculate skill coverage percentage
- Identify missing skills
- Assign skill priorities
- Generate a recommended learning order

## Job Roles Supported

- Java Developer
- Java Full Stack Developer
- Frontend Developer
- Backend Developer
- Software Developer
- Data Analyst
- Python Developer
- Web Developer
- Database Developer
- QA / Software Tester

## Technology Stack

### Frontend
- HTML5
- CSS3

### Backend
- Core Java
- Java HttpServer

### Database
- MySQL

### Database Connectivity
- JDBC
- MySQL Connector/J

### Tools
- VS Code
- MySQL Workbench
- Git
- GitHub

## Architecture

User
  ↓
HTML / CSS Interface
  ↓
Java HttpServer
  ↓
Skill Gap Calculator
  ↓
JDBC
  ↓
MySQL Database
  ↓
Role Skills + Importance
  ↓
Skill Gap Analysis
  ↓
Coverage + Missing Skills + Learning Order

## How It Works

1. The user selects a target job role.
2. The user selects their current skills.
3. Java retrieves the required skills for that role from MySQL.
4. The application compares the required skills with the user's current skills.
5. Matching skills are identified.
6. Missing skills are identified.
7. Skill coverage percentage is calculated.
8. Missing skills are prioritized based on their defined importance.
9. A recommended learning order is displayed.

## Database Design

The application uses three main tables:

- roles - stores available job roles
- skills - stores available technical skills
- role_skills - connects roles with required skills and their importance

roles
  │
  └──── role_skills ──── skills
             │
             └── importance

## Example

For the Data Analyst role, if a user selects:

- SQL
- Python
- Excel

the application compares these skills with the required skills stored in MySQL and displays the skill coverage, missing skills, and recommended learning order.

## Security

Database credentials are not stored directly in the source code.

The application reads the database password using the DB_PASSWORD environment variable.

## Future Enhancements

- User login and profiles
- Learning resource recommendations
- Skill progress tracking
- Resume-based skill extraction
- More job roles and technologies
- Admin interface for managing roles and skills

## Author

Basireddy Indrani

B.Tech Computer Science and Engineering