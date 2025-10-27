🏨 Hotel Management System

A complete Java-based Hotel Management Application using MVC Architecture and MySQL Database.

📘 Overview

The Hotel Management System is a desktop-based Java application that helps automate hotel operations such as room management, customer registration, booking, payments, and report generation.
It is designed using the Model-View-Controller (MVC) architecture, ensuring clear separation between business logic, user interface, and data access. The project features a clean Swing-based GUI and MySQL backend for data persistence.

🌟 Features

✅ Admin Login System
•	Secure login validation with credentials stored in MySQL.

✅ Room Management
•	Add, update, or delete rooms with details like room number, type, price, and status.

✅ Customer Management
•	Add and manage customers with validations for phone and email format.

✅ Booking Management
•	Book rooms for registered customers with check-in/check-out dates.
•	Update or cancel bookings.

✅ Payment & Billing System
•	Generate and manage payments linked to bookings and customers.
•	Calculate total amounts and maintain payment status.

✅ Reports & Dashboard
•	View real-time hotel summary: total rooms, available rooms, total bookings, and revenue.
•	Date and time display for current overview.


🧩 Project Architecture (MVC Structure)

opps/

│

├── model/          # Handles database and business logic

│   ├── DatabaseConnection.java

│   ├── BookingModel.java

│   ├── CustomerModel.java

│   ├── RoomModel.java

│   ├── PaymentModel.java

│   ├── EmployeeModel.java

│   └── ReportModel.java

│

├── controller/     # Connects Model and View

│   ├── BookingController.java

│   ├── CustomerController.java

│   ├── RoomController.java

│   ├── PaymentController.java

│   ├── ReportController.java

│   └── LoginController.java

│

└── view/           # GUI Layer (Swing-based interface)
  
    ├── LoginView.java
    
    ├── AdminMainFrame.java
    
    ├── HomePanel.java
    
    ├── RoomsPanel.java
    
    ├── CustomersPanel.java
    
    ├── BookingsPanel.java
   
    ├── PaymentsPanel.java
  
    ├── ReportsPanel.java
   
    ├── UserPanel.java
   
    └── UserBox.java


🗄️ Database Setup

🔹 Database Name

hotel_db

🔹 SQL Script

Run this in your MySQL terminal or phpMyAdmin:

CREATE DATABASE IF NOT EXISTS hotel_db;

USE hotel_db;


-- Admin Table

CREATE TABLE admin (

  id INT AUTO_INCREMENT PRIMARY KEY,
  
  username VARCHAR(50) NOT NULL UNIQUE,
  
  password VARCHAR(50) NOT NULL

);


INSERT INTO admin (username, password) VALUES ('admin', 'admin123');


-- Customers Table

CREATE TABLE customers (

  id INT AUTO_INCREMENT PRIMARY KEY,
  
  name VARCHAR(100),
  
  email VARCHAR(100),
  
  phone VARCHAR(15)

);


-- Rooms Table

CREATE TABLE rooms (

  id INT AUTO_INCREMENT PRIMARY KEY,
  
  room_no VARCHAR(10) UNIQUE,
  
  room_type VARCHAR(50),
  
  price DOUBLE,
  
  status VARCHAR(20)

);


INSERT INTO rooms (room_no, room_type, price, status)

VALUES ('101', 'Deluxe', 2500, 'Available'),

       ('102', 'Suite', 4000, 'Booked'),
       
       ('103', 'Standard', 1800, 'Available');



-- Bookings Table

CREATE TABLE bookings (

  id INT AUTO_INCREMENT PRIMARY KEY,
  
  customer_id INT,
  
  room_id INT,
  
  checkin_date DATE,
  
  
  checkout_date DATE,
  
  status VARCHAR(20),
  
  FOREIGN KEY (customer_id) REFERENCES customers(id),
  
  FOREIGN KEY (room_id) REFERENCES rooms(id)

);


-- Payments Table

CREATE TABLE payments (

  id INT AUTO_INCREMENT PRIMARY KEY,
  
  booking_id INT,
  
  customer_id INT,
  
  amount DOUBLE,
  
  payment_date DATE,
  
  status VARCHAR(20),
  
  FOREIGN KEY (booking_id) REFERENCES bookings(id),
  
  FOREIGN KEY (customer_id) REFERENCES customers(id)

);


-- Reports Table

CREATE TABLE reports (

  id INT AUTO_INCREMENT PRIMARY KEY,
  
  report_date DATE,
  
  total_bookings INT,
  
  total_revenue DOUBLE

);


🔧 Configuration

1.	Open DatabaseConnection.java

Update your MySQL credentials:

2.	private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";

3.	private static final String USER = "root";     // Change if different

4.	private static final String PASSWORD = "";      // Your MySQL password

5.	Ensure you have the MySQL Connector JAR added to your project libraries.


🚀 How to Run

1.	Open the project in an IDE like Eclipse or IntelliJ IDEA.

2.	Import the MySQL connector JAR file.

3.	Create the database using the SQL script provided above.

4.	Update database credentials in DatabaseConnection.java.

5.	Run the project by executing:

6.	LoginView.java



🔑 Login Credentials

Role	Username	Password

Admin	admin	admin123



🧠 Input Validations

✔ Customer Email: Must contain @gmail.com

✔ Phone Number: Must have exactly 10 digits

✔ Dates: Check-out date must be after check-in date

✔ Room Availability: Automatically updates after booking



🛠️ Technologies Used

Category	Tools / Technologies

Language	Java (JDK 8 or higher)

GUI	Java Swing

Database	MySQL

Architecture	MVC (Model-View-Controller)

Libraries	JDBC, MySQL Connector JAR



📈 Future Enhancements

🔹 Generate printable PDF invoices

🔹 Add user roles (Admin / Receptionist)

🔹 Add room images and search filters

🔹 Email notifications for bookings

🔹 Export reports to Excel or PDF

