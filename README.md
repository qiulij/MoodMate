# MoodMate: 
A Knowledge-Based Emotion Tracker for Self-Regulation and Early Detection of Mental Health Risks. The system includes a user-friendly Java GUI, a powerful inference engine powered by Jess rule-based system, and a MySQL database connected using JDBC for persistent storage and retrieval of data.

## Table of Contents
- [MoodMate:](#moodmate)
  - [Table of Contents](#table-of-contents)
  - [Project Structure](#project-structure)
  - [Installation](#installation)
  - [Database Setup](#database-setup)


## Project Structure
├── src/                     # Source code
|   ├── module.java           # packages usage
│   ├── gui/                 # GUI components
│   ├── logic/               # Jess rules and templates
│   ├── database/            # Database connection and queries
│   └── utilities/           # Helper functions and utilities
├── assets/                  # Configuration files and templates
├── README.md                # This file


## Installation
1. Clone the repository: 
   `git clone https://github.com/qiulij/MoodMate.git`
   `cd MoodMate`
2. Set up Jess: 
    Download and configure Jess. Add the 'jess.jar file' to your project's classpath.
3. Set up MySQL: 
    Install MySQL Server. Set up the database using the provided SQL scripts (see Database Setup).


## Database Setup
1. Run the SQL Scripts in MySQL
   -- Creating tables
   -- Creating the Authentication table
   CREATE TABLE IF NOT EXISTS Authentication (
                                            user_id INT AUTO_INCREMENT PRIMARY KEY,
                                            username VARCHAR(50) UNIQUE NOT NULL,
                                            password VARCHAR(255) NOT NULL,
                                            email VARCHAR(255) NOT NULL);

    -- Creating the User_Info table
    CREATE TABLE IF NOT EXISTS User_Info (
                                            user_id INT PRIMARY KEY,
                                            name VARCHAR(50),
                                            gender TINYINT,
                                            age INT,
                                            mbti VARCHAR(4),
                                            hobbies TEXT,
                                            self_image_score DECIMAL(5,2));

    -- Creating the Daily_Record table
    CREATE TABLE IF NOT EXISTS Daily_Record (
                                            user_id INT NOT NULL,
                                            record_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
                                            happy_score DECIMAL(5,2),
                                            sad_score DECIMAL(5,2),
                                            angry_score DECIMAL(5,2),
                                            confused_score DECIMAL(5,2),
                                            scared_score DECIMAL(5,2),
                                            self_image_score DECIMAL(5,2),
                                            sleep_score DECIMAL(5,2),
                                            physical_activity_score DECIMAL(5,2),
                                            food_score DECIMAL(5,2),
                                            weather_condition VARCHAR(50),
                                            weather_temperature DECIMAL(5,2),
                                            triggers_of_day TEXT,
                                            PRIMARY KEY (user_id, record_date));