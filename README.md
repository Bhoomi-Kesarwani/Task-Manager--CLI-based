# Task Manager CLI App (Java + MySQL)
A  console-based task management system built with *Java* and *MySQL*, featuring user authentication, CRUD operations, and task prioritization.  

## Features
- User Authentication: Sign up & login.
- Task Management: Add, update, delete, and mark tasks as completed.
- Category & Priority: Assign tasks categories and priorities (High, Medium, Low).
- Search/Filter: Search tasks by category or priority.
- Persistent Storage: Data stored in MySQL via JDBC.

## ⚙ Setup & Installation

1 Clone the Repository
git clone https://github.com/Bhoomi-Kesarwani/Task-Manager--CLI-based 


2 Create MySQL Database
Open MySQL Workbench and run:
CREATE DATABASE task_manager;
USE task_manager;

3  Add MySQL Connector/Jar
Download from: MySQL Connector/Jar
Extract and place the .jar file inside the lib/ folder.

4  Configure DBConnection
In DBConnection.java, update:
String url = "jdbc:mysql://localhost:3306/task_manager";
String username = " Your MySQL username";
String password = "your_password_here";

5 Compile & Run

Windows

javac -cp ".;lib/mysql-connector-java-8.0.xx.jar" src/*.java
java -cp ".;lib/mysql-connector-java-8.0.xx.jar;src" Main

Mac/Linux

javac -cp ".:lib/mysql-connector-java-8.0.xx.jar" src/*.java
java -cp ".:lib/mysql-connector-java-8.0.xx.jar:src" Main
